package com.fanfan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fanfan.bean.Setmeal;
import com.fanfan.common.PageParam;
import com.fanfan.common.R;
import com.fanfan.service.SetmealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 套餐管理器
 */
@RestController
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

}
