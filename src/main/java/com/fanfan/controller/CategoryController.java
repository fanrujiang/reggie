package com.fanfan.controller;

import com.fanfan.bean.PageBean;
import com.fanfan.common.R;
import com.fanfan.bean.Category;
import com.fanfan.service.CategoryService;
import org.springframework.web.bind.annotation.*;

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
     *
     * @param type 类型
     * @return categories
     */
    @GetMapping("/list")
    public R<ArrayList<Category>> list(Integer type) {
        ArrayList<Category> categories = categoryService.list(type);

        return R.success(categories);
    }

    /**
     * 新增分类
     *
     * @param category 分类
     * @return String
     */
    @PostMapping
    public R<String> add(@RequestBody Category category) {
        categoryService.add(category);
        return R.success("新增成功");
    }

    /**
     * 根据id删除分类
     *
     * @param id 分类id
     * @return String
     */
    @DeleteMapping
    public R<String> add(Long id) {
        categoryService.deleteById(id);
        return R.success("删除成功");
    }

    /**
     * 更新分类
     * @param category 分类的对象
     * @return String
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        categoryService.update(category);
        return R.success("更新分类成功");
    }
}
