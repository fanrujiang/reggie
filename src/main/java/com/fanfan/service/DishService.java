package com.fanfan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fanfan.bean.Dish;
import com.fanfan.dto.DishDto;

/**
 * @author Admin
 */

public interface DishService extends IService<Dish> {

    /**
     * 添加菜品
     * @param dishDto
     * @return
     */
    void add(DishDto dishDto);

}
