package com.fanfan.service;

import com.fanfan.bean.Dish;
import com.fanfan.bean.PageBean;
import com.fanfan.dto.DishDto;

import java.util.ArrayList;

public interface DishService {
    PageBean page(int page, int pageSize, String name);

    /**
     * 根据id查询菜品信息
     *
     * @param id
     * @return
     */
    DishDto getById(Long id);

    /**
     * 根据分类查询菜品集合
     *
     * @param categoryId 分类id
     * @return 菜品集合
     */
    ArrayList<Dish> getByCategoryId(Long categoryId);

    /**
     * 修改菜品的启售停售
     *
     * @param status 状态
     * @param ids    菜品ids
     */
    void status(int status, String ids);
}
