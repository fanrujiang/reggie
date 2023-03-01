package com.fanfan.controller;

import com.fanfan.bean.PageBean;
import com.fanfan.common.R;
import com.fanfan.service.SetmealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 套餐控制器
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    private final SetmealService setmealService;

    public SetmealController(SetmealService setmealService) {
        this.setmealService = setmealService;
    }

    /**
     * 套餐分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<PageBean> page(int page, int pageSize, String name) {

        PageBean pageBean = setmealService.page(page, pageSize, name);
        return R.success(pageBean);
    }

}
