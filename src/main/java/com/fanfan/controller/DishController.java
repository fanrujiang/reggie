package com.fanfan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fanfan.bean.Dish;
import com.fanfan.common.PageParam;
import com.fanfan.common.R;
import com.fanfan.dto.DishDto;
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


    private DishController(DishService dishService) {
        this.dishService = dishService;

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

        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功");
    }

    /**
     * 停售菜品
     *
     * @param ids
     * @return
     */
    @PostMapping("/status/0")   //xxxx/status/0?1236578241871268
    public R<String> updateStatus(String ids) {
        log.info(ids);
        dishService.offStatus(ids);
        return R.success("停售成功");
    }

    /**
     * 启售菜品
     *
     * @param ids
     * @return
     */
    @PostMapping("/status/1")   //xxxx/status/0?1236578241871268
    public R<String> ondateStatus(String ids) {
        log.info(ids);
        dishService.onStatus(ids);
        return R.success("启售成功");
    }

    @DeleteMapping
    public R<String> delete(String ids) {
        log.info(ids);
        dishService.delete(ids);
        return R.success("删除成功");
    }

}
