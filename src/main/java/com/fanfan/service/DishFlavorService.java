package com.fanfan.service;

import com.fanfan.bean.DishFlavor;

import java.util.ArrayList;

public interface DishFlavorService {

    /**
     * 根据菜品id查询口味集合
     * @param dishId
     * @return
     */
    ArrayList<DishFlavor> findByDishId(Long dishId);

    /**
     * 根据菜品id删除口味
     * @param dishId 菜品id
     */
    void deleteByDishId(Long dishId);

    /**
     * 增加口味信息
     * @param flavor 菜品口味对象
     */
    void insert(DishFlavor flavor);
}
