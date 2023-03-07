package com.fanfan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fanfan.bean.Category;
import com.fanfan.bean.OrderDetail;
import com.fanfan.bean.Setmeal;
import com.fanfan.bean.SetmealDish;
import com.fanfan.common.PageParam;
import com.fanfan.common.R;
import com.fanfan.dto.SetmealDto;
import com.fanfan.service.CategoryService;
import com.fanfan.service.OrderDetailService;
import com.fanfan.service.SetmealDishService;
import com.fanfan.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐管理器
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
@Api(tags = "套餐相关接口")
public class SetmealController {

    private final SetmealService setmealService;
    private final SetmealDishService setmealDishService;
    private final CategoryService categoryService;
    private final OrderDetailService orderDetailService;

    public SetmealController(SetmealService setmealService, SetmealDishService setmealDishService, CategoryService categoryService, OrderDetailService orderDetailService) {
        this.setmealService = setmealService;
        this.setmealDishService = setmealDishService;
        this.categoryService = categoryService;
        this.orderDetailService = orderDetailService;
    }

    /**
     * 套餐分页查询
     *
     * @param pageParam
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "套餐分页查询接口")
    public R<Page> page(PageParam pageParam) {
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        //设置条件
        lqw.like(pageParam.getName() != null, Setmeal::getName, pageParam.getName());
        Page<Setmeal> pageInfo = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        //调用service进行查询
        setmealService.page(pageInfo, lqw);
        Page<SetmealDto> pageDto = new Page<>();
        BeanUtils.copyProperties(pageInfo, pageDto, "records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> collect = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Category category = categoryService.getById(item.getCategoryId());
            setmealDto.setCategoryName(category.getName());
            return setmealDto;
        }).collect(Collectors.toList());

        pageDto.setRecords(collect);

        return R.success(pageDto);

    }

    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    @CacheEvict(value = "setmealCache",allEntries = true) //清除setmealCache名称下,所有的缓存数据
    @ApiOperation(value = "新增套餐接口")
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
    @CacheEvict(value = "setmealCache",allEntries = true) //清除setmealCache名称下,所有的缓存数据
    @ApiOperation(value = "停售套餐接口")
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
    @CacheEvict(value = "setmealCache",allEntries = true) //清除setmealCache名称下,所有的缓存数据
    @ApiOperation(value = "启售套餐接口")
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
    @CacheEvict(value = "setmealCache",allEntries = true) //清除setmealCache名称下,所有的缓存数据
    @ApiOperation(value = "删除套餐接口")
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
    @ApiOperation(value = "根据id查询套餐接口")
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

    @GetMapping("/dish/{id}")
    public R<SetmealDto> getById2(@PathVariable("id") Long id) {
        return this.getById(id);
    }

    /**
     * 修改套餐
     *
     * @param setmealDto
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改套餐接口")
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        setmealService.updateSetmeal(setmealDto);
        return R.success("修改成功");
    }

    /**
     * 根据分类id查询套餐信息
     *
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    @Cacheable(value = "setmealCache",key = "#setmeal.categoryId + '_' + #setmeal.status")
    @ApiOperation(value = "套餐条件查询接口")
    public R<List<SetmealDto>> list(Setmeal setmeal) {
        Long categoryId = setmeal.getCategoryId();
        Category category = categoryService.getById(categoryId);
        String categoryName = category.getName();
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Setmeal::getCategoryId, categoryId);
        lqw.eq(Setmeal::getStatus,setmeal.getStatus());
        lqw.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(lqw);
        ArrayList<SetmealDto> setmealDtos = new ArrayList<>();
        for (Setmeal s : list) {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(s,setmealDto);
            setmealDto.setCategoryName(categoryName);

            //获取套餐的销售数量
            LambdaQueryWrapper<OrderDetail> olqw = new LambdaQueryWrapper<>();
            olqw.eq(OrderDetail::getSetmealId,s.getId());
            int count = orderDetailService.count(olqw);
            //设置套餐已售出的数量
            setmealDto.setSaleNum(count);
            setmealDtos.add(setmealDto);
        }

        return R.success(setmealDtos);

    }
}
