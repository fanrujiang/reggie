package com.fanfan.controller;


import com.fanfan.bean.Dish;
import com.fanfan.bean.PageBean;
import com.fanfan.common.R;
import com.fanfan.dto.DishDto;
import com.fanfan.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("/dish")
@RestController
public class DishController {

    @Autowired
    DishService dishService;

    /**
     * 菜品信息分页查询
     *
     * @param page     当前查询页码
     * @param pageSize 每页展示记录数
     * @param name     菜品名 - 可选参数
     * @return
     */
    @GetMapping("/page")
    public R<PageBean> page(int page, int pageSize, String name) {

        PageBean pageBean = dishService.page(page, pageSize, name);

        return R.success(pageBean);
    }

    /**
     * 根据id查询菜品信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id) {

        DishDto dishDto = dishService.getById(id);

        return R.success(dishDto);
    }

    /**
     * 根据分类查询菜品集合
     * @param categoryId 分类id
     * @return 菜品集合
     */
    @GetMapping("/list")
    public R<ArrayList<Dish>> getByCategoryId(Long categoryId) {
        ArrayList<Dish> dishes = dishService.getByCategoryId(categoryId);
        return R.success(dishes);
    }

    /**
     * 修改菜品的启售停售
     * @param status 状态
     * @param ids 菜品ids
     */
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable("status") int status, String ids){
        dishService.status(status,ids);
        return R.success("修改成功");
    }

    /**
     * 修改菜品信息
     * @param dishDto 菜品dto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.update(dishDto);
        return R.success("修改成功");
    }



}
