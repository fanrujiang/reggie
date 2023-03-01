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
}
