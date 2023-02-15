package com.fanfan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fanfan.bean.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工表的mapper
 * @author Admin
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
