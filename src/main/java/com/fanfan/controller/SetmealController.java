package com.fanfan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fanfan.bean.Category;
import com.fanfan.bean.Setmeal;
import com.fanfan.bean.SetmealDish;
import com.fanfan.common.PageParam;
import com.fanfan.common.R;
import com.fanfan.dto.SetmealDto;
import com.fanfan.service.CategoryService;
import com.fanfan.service.SetmealDishService;
import com.fanfan.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 套餐管理器
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    private final SetmealService setmealService;
    private final SetmealDishService setmealDishService;

    private final CategoryService categoryService;

    public SetmealController(SetmealService setmealService, SetmealDishService setmealDishService, CategoryService categoryService) {
        this.setmealService = setmealService;
        this.setmealDishService = setmealDishService;
        this.categoryService = categoryService;
    }

    /**
     * 套餐分页查询
     *
     * @param pageParam
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(PageParam pageParam) {
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        //设置条件
        lqw.like(pageParam.getName() != null, Setmeal::getName, pageParam.getName());
        Page<Setmeal> page = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        //调用service进行查询
        setmealService.page(page, lqw);
        return R.success(page);

    }

    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("套餐信息{}", setmealDto);
        setmealService.saveWithDish(setmealDto);

        return R.success("新增套餐成功");
    }


    /**
     * 停售套餐
     *
     * @param ids
     * @return
     */
    @PostMapping("/status/0")
    public R<String> offStatus(String ids) {
        log.info(ids);
        setmealService.offStatus(ids);
        return R.success("停售成功");
    }

    /**
     * 启售套餐
     *
     * @param ids
     * @return
     */
    @PostMapping("/status/1")
    public R<String> onStatus(String ids) {
        log.info(ids);
        setmealService.onStatus(ids);
        return R.success("启售成功");
    }

    /**
     * 删除套餐
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(String ids) {
        log.info(ids);
        setmealService.delete(ids);
        return R.success("删除成功");
    }

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public R<SetmealDto> getById(@PathVariable("id") Long id) {
        //根据id查询当前套餐
        Setmeal setmeal = setmealService.getById(id);
        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        //设置条件
        lqw.eq(SetmealDish::getSetmealId, id);
        //查询此套餐的所有菜品
        List<SetmealDish> setmealDishes = setmealDishService.list(lqw);
        //查询此套餐所属的分类
        Category category = categoryService.getById(setmeal.getCategoryId());
        //new 一个SetmealDto的对象
        SetmealDto setmealDto = new SetmealDto();
        //把setmeal的值对应复制给setmealDto
        BeanUtils.copyProperties(setmeal, setmealDto);
        //把此套餐的所有菜品装入Dto中
        setmealDto.setSetmealDishes(setmealDishes);
        //设置此套餐 菜品分类的名字
        setmealDto.setCategoryName(category.getName());
        return R.success(setmealDto);
    }

    /**
     * 修改套餐
     *
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        setmealService.updateSetmeal(setmealDto);
        return R.success("修改成功");
    }
}
