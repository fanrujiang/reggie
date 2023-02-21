package com.fanfan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanfan.bean.Dish;
import com.fanfan.bean.DishFlavor;
import com.fanfan.dto.DishDto;
import com.fanfan.mapper.DishMapper;
import com.fanfan.service.CategoryService;
import com.fanfan.service.DishFlavorService;
import com.fanfan.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;
    /**
     * 添加菜品
     *
     * @param dishDto
     * @return
     */
    @Override
    @Transactional
    public void add(DishDto dishDto) {
        //添加菜品
        int row = getBaseMapper().insert(dishDto);
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishDto.getId());

            dishFlavorService.save(flavor);
        }
    }
}
