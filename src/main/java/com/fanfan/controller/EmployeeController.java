package com.fanfan.controller;

import com.fanfan.bean.PageBean;
import com.fanfan.common.R;
import com.fanfan.pojo.Employee;
import com.fanfan.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    /**
     * 员工信息分页查询
     *
     * @param page     当前查询页码
     * @param pageSize 每页展示记录数
     * @param name     员工姓名 - 可选参数
     * @return
     */
    @GetMapping("/page")
    public R<PageBean> page(int page, int pageSize, String name) {

        PageBean pageBean = employeeService.page(page, pageSize, name);

        return R.success(pageBean);
    }

    /**
     * 员工登录
     *
     * @param employee
     * @return
     */
    @RequestMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        employee.setPassword(password);

        Employee emp = employeeService.login(employee);


        if (emp == null) {
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
     * 员工退出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清理Session中保存的员工id
        request.getSession().getAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        Long uuid = (long) UUID.randomUUID().toString().replaceAll("-", "").hashCode();
        System.out.println("uuid =" + uuid);
        employee.setId(uuid);
        String password = DigestUtils.md5DigestAsHex("123456".getBytes());
        employee.setPassword(password);
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);


        employeeService.save(employee);

        return R.success("添加成功");

    }

    /**
     * 修改员工
     *
     * @param employee 员工的实体类
     * @return 成功信息;
     */
    @PutMapping
    public R<String> update(@RequestBody Employee employee) {
        System.out.println("employee = " + employee);
        employeeService.update(employee);
        return R.success("修改成功");
    }


}
