package com.fanfan.service.impl;

import com.fanfan.bean.DishFlavor;
import com.fanfan.common.BaseContext;
import com.fanfan.mapper.DishFlavorMapper;
import com.fanfan.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

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

    /**
     * 根据菜品id删除口味
     *
     * @param dishId 菜品id
     */
    @Override
    public void deleteByDishId(Long dishId) {
        dishFlavorMapper.deleteByDishId(dishId);
    }

    /**
     * 增加口味信息
     *
     * @param flavor 菜品口味对象
     */
    @Override
    public void insert(DishFlavor flavor) {
        //创造一个ID
        Long uuid = (long) UUID.randomUUID().toString().replaceAll("-", "").hashCode();
        flavor.setId(uuid);
        flavor.setUpdateTime(LocalDateTime.now());
        flavor.setCreateTime(LocalDateTime.now());
        flavor.setUpdateUser(BaseContext.getCurrentId());
        flavor.setCreateUser(BaseContext.getCurrentId());
        dishFlavorMapper.insert(flavor);
    }
}
