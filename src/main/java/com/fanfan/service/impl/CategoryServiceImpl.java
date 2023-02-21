package com.fanfan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanfan.bean.Category;
import com.fanfan.bean.Dish;
import com.fanfan.common.CustomException;
import com.fanfan.mapper.CategoryMapper;
import com.fanfan.service.CategoryService;
import com.fanfan.service.DishService;
import com.fanfan.service.SetmealService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    private final DishService dishService;
    private final SetmealService setmealService;

    public CategoryServiceImpl(DishService dishService, SetmealService setmealService) {
        this.dishService = dishService;
        this.setmealService = setmealService;
    }

    /**
     * 根据id删除分类，删除之前需要进行判断
     *
     * @param id
     */
    @Override
    @Transactional
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        //如果已经关联就抛出异常
        if (count1 > 0) {
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
        super.removeById(id);
    }
}
