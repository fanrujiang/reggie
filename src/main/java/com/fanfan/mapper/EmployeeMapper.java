package com.fanfan.mapper;

import com.fanfan.bean.Employee;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper {

    /**
     * 员工登录
     *
     * @param employee
     * @return
     */
    Employee getByUsernameAndPassword(Employee employee);

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    void insert(Employee employee);

    Page<Employee> findPage(String name);

    Employee getByUsername();

    void update(Employee employee);

    Employee getById(Long id);
}
