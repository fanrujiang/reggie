package com.fanfan.controller;

import com.fanfan.bean.User;
import com.fanfan.common.R;
import com.fanfan.service.UserService;
import com.fanfan.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public R sendMsg(String phone,HttpSession session){
        //获取手机号
        if(StringUtils.isNotEmpty(phone)){
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);
            //需要将生成的验证码保存到Session
            session.setAttribute(phone,code);
            return R.success(code);
        }
        return R.error("短信发送失败");
    }
}


