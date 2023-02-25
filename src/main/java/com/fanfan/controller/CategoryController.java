package com.fanfan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fanfan.bean.Category;
import com.fanfan.common.CustomException;
import com.fanfan.common.PageParam;
import com.fanfan.common.R;
import com.fanfan.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     *
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

        categoryService.page(page, lqw);

        return R.success(page);
    }

    /**
     * 新增分类
     *
     * @param category 菜品
     * @return String 成功消息
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Category::getName,category.getName());
        int count = categoryService.count(lqw);
        if (count>0){
            throw new CustomException(category.getName()+"已存在");
        }
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> deleteById(Long id) {
        //categoryService.removeById(id);
        categoryService.remove(id);
        return R.success("分类信息删除成功");
    }

    /**
     * 修改分类
     *
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return R.success("修改成功");
    }

    /**
     * 菜品和套餐的分类查询
     *
     * @param category 菜品和套餐
     * @return List
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        //条件构造器
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        //设置条件
        lqw.eq(category.getType() != null, Category::getType, category.getType());
        lqw.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(lqw);
        return R.success(list);
    }

}
