package com.fanfan.service;

import com.fanfan.pojo.Employee;

public interface EmployeeService {


    /**
     * 员工登录
     * @param employee
     * @return
     */
    Employee login(Employee employee);


    /**
     * 新增员工
     * @param employee
     * @return
     */
    void save(Employee employee);
}
