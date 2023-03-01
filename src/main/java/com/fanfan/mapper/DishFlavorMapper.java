package com.fanfan.mapper;

import com.fanfan.bean.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface DishFlavorMapper {
    /**
     * 根据菜品id查询口味集合
     * @param dishId
     * @return
     */
    ArrayList<DishFlavor> findByDishId(Long dishId);
}
