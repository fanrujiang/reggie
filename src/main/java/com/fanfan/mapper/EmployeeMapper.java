package com.fanfan.mapper;

import com.fanfan.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Mapper
public interface EmployeeMapper {


    Employee getByUsernameAndPassword(Employee employee);
}
