package com.fanfan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanfan.mapper.EmployeeMapper;
import com.fanfan.bean.Employee;
import com.fanfan.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author Admin
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
