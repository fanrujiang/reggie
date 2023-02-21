package com.fanfan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fanfan.bean.Dish;
import com.fanfan.dto.DishDto;

import java.util.List;

/**
 * @author Admin
 */

public interface DishService extends IService<Dish> {

    /**
     * 添加菜品
     *
     * @param dishDto
     * @return
     */
    void add(DishDto dishDto);

    /**
     * 根据id查询菜品信息和对应的口味信息
     *
     * @param id
     * @return
     */
    DishDto getByIdWithFlavor(Long id);

    /**
     * 修改菜品
     *
     * @param dishDto
     */
    void updateWithFlavor(DishDto dishDto);

    /**
     * 停售菜品
     *
     * @param ids
     */
    void offStatus(String ids);

    /**
     * 启售菜品
     *
     * @param ids
     */
    void onStatus(String ids);
}
