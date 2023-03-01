package com.fanfan.mapper;

import com.fanfan.bean.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface SetmealDishMapper {
    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    ArrayList<SetmealDish> listBySetmealId(Long id);
}
