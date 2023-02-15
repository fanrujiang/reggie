package com.fanfan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fanfan.bean.Employee;
import com.fanfan.common.PageParam;
import com.fanfan.common.R;
import com.fanfan.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 员工
 * @author Admin
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * 员工登录
     *
     * @param request  Session
     * @param employee 员工登录信息
     * @return Employee
     */
    @PostMapping("login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //3、如果没有查询到则返回登录失败结果
        if (emp == null) {
            return R.error("登录失败");
        }

        //4、密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)) {
            return R.error("登录失败");
        }

        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }

        //6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出登录
     *
     * @param request Session
     * @return String
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 添加员工
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        //设置初始密码123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //给用户对象添加创建和修改时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        //获取当前操作用户的id
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    @GetMapping("/page")
    public R page(PageParam pageParam) {
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        //1.1 设置条件:: 如果name 有值，就追加条件，否则就不追加。
        lqw.like(pageParam.getName() != null, Employee::getName, pageParam.getName());
        //2. 构建分页对象:: 设置查询第几页，每页查询多少条
        Page<Employee> page = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        employeeService.page(page, lqw);
        return R.success(page);
    }
}
