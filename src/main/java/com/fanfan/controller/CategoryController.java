package com.fanfan.controller;

import com.fanfan.bean.PageBean;
import com.fanfan.common.R;
import com.fanfan.pojo.Category;
import com.fanfan.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 分类的分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<PageBean> page(int page, int pageSize) {
        PageBean pageBean = categoryService.page(page, pageSize);

        return R.success(pageBean);
    }

    /**
     * 根据类型查询分类集合
     * @param type 类型
     * @return categories
     */
    @GetMapping("/list")
    public R<ArrayList<Category>> list(Integer type) {
        ArrayList<Category> categories = categoryService.list(type);

        return R.success(categories);
    }
}
