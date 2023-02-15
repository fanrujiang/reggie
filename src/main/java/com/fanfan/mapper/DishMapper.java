package com.fanfan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fanfan.bean.Dish;
import com.fanfan.bean.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Admin
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
