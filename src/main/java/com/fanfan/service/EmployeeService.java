package com.fanfan.service;

import com.fanfan.bean.PageBean;
import com.fanfan.bean.Employee;

public interface EmployeeService {


    /**
     * 员工登录
     *
     * @param employee
     * @return
     */
    Employee login(Employee employee);


    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    void save(Employee employee);

    PageBean page(int page, int pageSize, String name);

    /**
     * 根据username查询用户
     *
     * @param username
     * @return
     */
    Employee getByUserName(String username);

    /**
     * 修改员工信息
     *
     * @param employee 员工的实体类
     */
    void update(Employee employee);

    /**
     * 根据id查询用户
     *
     * @param id 用户id
     * @return Employee
     */
    Employee getById(Long id);
}
