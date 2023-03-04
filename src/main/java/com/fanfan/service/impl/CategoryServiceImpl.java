package com.fanfan.service.impl;

import com.fanfan.bean.Dish;
import com.fanfan.bean.PageBean;
import com.fanfan.common.BaseContext;
import com.fanfan.common.CustomException;
import com.fanfan.mapper.CategoryMapper;
import com.fanfan.bean.Category;
import com.fanfan.mapper.DishMapper;
import com.fanfan.service.CategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final DishMapper dishMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper, DishMapper dishMapper) {
        this.categoryMapper = categoryMapper;
        this.dishMapper = dishMapper;
    }

    /**
     * 分类的分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageBean page(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        Page<Category> p = categoryMapper.findPage();

        return new PageBean((int) p.getTotal(), p.getResult());
    }

    /**
     * 根据类型查询分类集合
     *
     * @param type 类型
     * @return categories
     */
    @Override
    public ArrayList<Category> list(Integer type) {
        return categoryMapper.list(type);
    }

    /**
     * 判断分类表中名字和排序存在
     *
     * @return 存在返回true 不存在返回false
     */
    @Override
    public boolean decision(Category category) {
        String categoryName = category.getName();
        Integer sort = category.getSort();
        if (this.getByName(categoryName) != null) {
            return true;
        }
        return false;
    }

    private Category getBySort(Integer sort) {
        return categoryMapper.getOneBySort(sort);
    }


    private Category getByName(String name) {

        return categoryMapper.getOneByName(name);
    }

    /**
     * 新增分类
     *
     * @param category 分类
     */
    @Override
    public void add(Category category) {
        if (this.decision(category)) {
            throw new CustomException("名称或排序已存在");
        }
        Long currentId = BaseContext.getCurrentId();
        category.setUpdateUser(currentId);
        category.setCreateTime(LocalDateTime.now());
        categoryMapper.add(category);
    }

    /**
     * 根据id删除分类
     *
     * @param id 分类id
     */
    @Override
    public void deleteById(Long id) {
        ArrayList<Dish> byCategoryId = dishMapper.getByCategoryId(id);
        if (byCategoryId.size()>0){
            throw new CustomException("该分类下有"+byCategoryId.size()+"个菜品");
        }
        if (ObjectUtils.isEmpty(id)) {
            throw new CustomException("分类id为空");
        }
        categoryMapper.deleteById(id);
    }

    /**
     * 更新分类
     *
     * @param category 分类的对象
     */
    @Override
    public void update(Category category) {

        category.setUpdateUser(BaseContext.getCurrentId());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }
}
