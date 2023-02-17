package com.fanfan.service.impl;

import com.fanfan.mapper.EmployeeMapper;
import com.fanfan.pojo.Employee;
import com.fanfan.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;


    /**
     * 员工登录
     *
     * @param employee
     * @return
     */
    @Override
    public Employee login(Employee employee) {
        Employee emp = employeeMapper.getByUsernameAndPassword(employee);

        return emp;
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @Override
    public void save(Employee employee) {
        employeeMapper.insert(employee);
    }
}
