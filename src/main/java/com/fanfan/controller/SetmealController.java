package com.fanfan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fanfan.bean.Setmeal;
import com.fanfan.common.PageParam;
import com.fanfan.common.R;
import com.fanfan.dto.SetmealDto;
import com.fanfan.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 套餐管理器
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    private final SetmealService setmealService;

    public SetmealController(SetmealService setmealService) {
        this.setmealService = setmealService;
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
}
