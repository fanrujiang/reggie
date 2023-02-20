package com.fanfan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fanfan.bean.Category;
import com.fanfan.common.PageParam;
import com.fanfan.common.R;
import com.fanfan.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分类管理控制器
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    /**
     * 分类管理的分页查询
     * @param pageParam
     * @return
     */
    @GetMapping("/page")
    public R page(PageParam pageParam) {
        //构建分页对象 置查询第几页，每页查询多少条
        Page<Category> page = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        //条件构造器
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        //设置条件，根据sort 进行排序
        lqw.orderByAsc(Category::getSort);

        categoryService.page(page,lqw);

        return R.success(page);
    }
}
