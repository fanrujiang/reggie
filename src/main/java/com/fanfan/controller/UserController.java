package com.fanfan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fanfan.bean.User;
import com.fanfan.common.CustomException;
import com.fanfan.common.R;
import com.fanfan.service.UserService;
import com.fanfan.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 发送手机短信验证码
     * @param phone
     * @return
     */

    @GetMapping("/code")
    public R<String> getCode(String phone , HttpSession session){

        if (ObjectUtils.isEmpty(phone)){
            return R.error("请输入手机号");
        }
        //1. 生成验证码
        String code = ValidateCodeUtils.generateValidateCode4String(4);
        //2. 给这个手机号发短信
        //SMSUtils.sendMessage("签名", "模板" , phone , code);
        //3. 把验证码保存到session作用域，以便一会登录，可以判断验证码对不对！
        session.setAttribute("code", code);
        //真实情况：
        //return R.success("短信验证码已发送！");

        //把验证码返回给前端，前端，把验证码放到输入框里面去！
        return R.success(code);
    }

    /**
     * 移动端用户登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info(map.toString());
        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //从Session中获取保存的验证码
        Object codeInSession = session.getAttribute("code");

        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if(codeInSession != null && codeInSession.equals(code)){
            //如果能够比对成功，说明登录成功

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user = userService.getOne(queryWrapper);
            if(user == null){
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }
        return R.error("登录失败");
    }

    /**
     * 移动端用户退出登录
     * @param session
     * @return
     */
    @PostMapping("/loginout")
    public R<String> loginOut(HttpSession session){
        session.removeAttribute("user");
        return R.success("退出成功");
    }
}


