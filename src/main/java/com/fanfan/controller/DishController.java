package com.fanfan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fanfan.bean.Category;
import com.fanfan.bean.Dish;
import com.fanfan.bean.DishFlavor;
import com.fanfan.bean.OrderDetail;
import com.fanfan.common.PageParam;
import com.fanfan.common.R;
import com.fanfan.dto.DishDto;
import com.fanfan.service.CategoryService;
import com.fanfan.service.DishFlavorService;
import com.fanfan.service.DishService;
import com.fanfan.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    private CategoryService categoryService;

    private DishFlavorService dishFlavorService;

    private OrderDetailService orderDetailService;

    private RedisTemplate redisTemplate;


    private DishController(DishService dishService, CategoryService categoryService, DishFlavorService dishFlavorService, OrderDetailService orderDetailService, RedisTemplate redisTemplate) {
        this.dishService = dishService;

        this.categoryService = categoryService;
        this.dishFlavorService = dishFlavorService;
        this.orderDetailService = orderDetailService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 分页查询
     *
     * @param pageParam
     * @return
     */
    @GetMapping("/page")
    public R page(PageParam pageParam) {
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        //1.1 设置条件:: 如果name 有值，就追加条件，否则就不追加。
        lqw.like(pageParam.getName() != null, Dish::getName, pageParam.getName());
        lqw.orderByDesc(Dish::getUpdateTime);
        //2. 构建分页对象:: 设置查询第几页，每页查询多少条
        Page<Dish> pageInfo = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        dishService.page(pageInfo, lqw);
        Page<DishDto> dishDtoPage = new Page<>();
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {

            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);

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
        //清理某个分类下面的菜品缓存数据
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
        return R.success("成功");
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id) {
        DishDto byIdWithFlavor = dishService.getByIdWithFlavor(id);
        return R.success(byIdWithFlavor);
    }

    /**
     * 修改菜品
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
        Long categoryId = dishService.getById(dishDto.getId()).getCategoryId();
        if (!Objects.equals(categoryId, dishDto.getCategoryId())) {
            redisTemplate.delete("dish_" + categoryId + "_1");
        }
        dishService.updateWithFlavor(dishDto);

        return R.success("修改成功");
    }


    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable("status") int status, String ids) {
        dishService.status(status, ids);
        return R.success("修改成功");
    }

    @DeleteMapping
    public R<String> delete(String ids) {
        log.info(ids);
        dishService.delete(ids);
        //清理所有菜品的缓存数据
        Set keys = redisTemplate.keys("dish_*"); //获取所有以dish_xxx开头的key
        redisTemplate.delete(keys); //删除这些key
        return R.success("删除成功");
    }

    /**
     * 根据条件查询菜品对应数据
     *
     * @param dish
     * @return R
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {

        List<DishDto> dishDtoListt = null;
        //动态构造key
        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();//dish_1397844391040167938_1
        //先从redis中获取缓存数据
        dishDtoListt = (List<DishDto>) redisTemplate.opsForValue().get(key);
        if (dishDtoListt != null) {
            //如果存在，直接返回，无需查询数据库
            return R.success(dishDtoListt);
        }

        //条件构造器
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        lqw.eq(Dish::getStatus, 1);
        lqw.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(lqw);

        List<DishDto> dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                dishDto.setCategoryName(category.getName());
            }
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> dishLqw = new LambdaQueryWrapper<>();
            dishLqw.eq(DishFlavor::getDishId, dishId);
            List<DishFlavor> dishFlavorList = dishFlavorService.list(dishLqw);
            dishDto.setFlavors(dishFlavorList);

            //获取菜品的销量
            LambdaQueryWrapper<OrderDetail> olqw = new LambdaQueryWrapper<>();
            olqw.eq(OrderDetail::getDishId, dishId);
            int count = orderDetailService.count(olqw);
            //月售多少单
            dishDto.setSaleNum(count);
            return dishDto;

        }).collect(Collectors.toList());

        //如果不存在，需要查询数据库，将查询到的菜品数据缓存到Redis
        redisTemplate.opsForValue().set(key, dishDtoList, 60, TimeUnit.MINUTES);
        //redisUtil.set(key,dishDtoList,60);
        return R.success(dishDtoList);
    }

}
