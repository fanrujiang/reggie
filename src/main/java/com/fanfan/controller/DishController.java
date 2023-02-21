package com.fanfan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fanfan.bean.Dish;
import com.fanfan.common.PageParam;
import com.fanfan.common.R;
import com.fanfan.dto.DishDto;
import com.fanfan.service.DishFlavorService;
import com.fanfan.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 菜品
 *
 * @author Admin
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    private final DishService dishService;
    private final DishFlavorService dishFlavorService;

    private DishController(DishService dishService, DishFlavorService dishFlavorService) {
        this.dishService = dishService;
        this.dishFlavorService = dishFlavorService;
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

    /**
     * 新增菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());

        dishService.add(dishDto);

        return R.success("成功");
    }

}
