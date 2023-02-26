package com.fanfan.service.impl;

import com.fanfan.bean.PageBean;
import com.fanfan.common.BaseContext;
import com.fanfan.mapper.EmployeeMapper;
import com.fanfan.pojo.Employee;
import com.fanfan.service.EmployeeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @Override
    public PageBean page(int page, int pageSize, String name) {
        PageHelper.startPage(page, pageSize);
        Page<Employee> p = employeeMapper.findPage(name);
        return new PageBean((int) p.getTotal(), p.getResult());
    }

    /**
     * 根据username查询用户
     *
     * @param username
     * @return
     */
    @Override
    public Employee getByUserName(String username) {
        Employee employee = employeeMapper.getByUsername();
        return employee;
    }

    /**
     * 修改员工信息
     *
     * @param employee 员工的实体类
     */
    @Override
    public void update(Employee employee) {
        employee.setUpdateUser(BaseContext.getCurrentId());
        employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.update(employee);
    }
}
