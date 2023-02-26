package com.fanfan.controller;


import com.fanfan.bean.PageBean;
import com.fanfan.common.R;
import com.fanfan.dto.DishDto;
import com.fanfan.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
