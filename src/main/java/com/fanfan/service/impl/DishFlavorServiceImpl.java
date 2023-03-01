package com.fanfan.service.impl;

import com.fanfan.mapper.DishFlavorMapper;
import com.fanfan.bean.DishFlavor;
import com.fanfan.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DishFlavorServiceImpl implements DishFlavorService {

    @Autowired
    private DishFlavorMapper dishFlavorMapper;


    /**
     * 根据菜品id查询口味集合
     *
     * @param dishId
     * @return
     */
    @Override
    public ArrayList<DishFlavor> findByDishId(Long dishId) {

        return dishFlavorMapper.findByDishId(dishId);
    }
}
