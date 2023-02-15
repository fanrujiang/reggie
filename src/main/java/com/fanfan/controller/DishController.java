package com.fanfan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fanfan.bean.Dish;
import com.fanfan.bean.Employee;
import com.fanfan.common.PageParam;
import com.fanfan.common.R;
import com.fanfan.service.DishService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜品
 *
 * @author Admin
 */
@RestController
@RequestMapping("/dish")
public class DishController {
    final  DishService dishService;

    private  DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/page")
    public R page(PageParam pageParam) {
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        //1.1 设置条件:: 如果name 有值，就追加条件，否则就不追加。
        lqw.like(pageParam.getName() != null, Dish::getName, pageParam.getName());
        //2. 构建分页对象:: 设置查询第几页，每页查询多少条
        Page<Dish> page = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        dishService.page(page, lqw);
        return R.success(page);

    }
}
