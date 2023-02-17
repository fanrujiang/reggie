package com.fanfan.mapper;

import com.fanfan.pojo.Employee;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper {
    Page<Employee> findPage(String name);
}
