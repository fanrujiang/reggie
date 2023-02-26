package com.fanfan.service.impl;

import com.fanfan.bean.PageBean;
import com.fanfan.common.BaseContext;
import com.fanfan.common.CustomException;
import com.fanfan.mapper.CategoryMapper;
import com.fanfan.pojo.Category;
import com.fanfan.service.CategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
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
        if (this.getByName(categoryName)!=null) {
            return true;
        } else return false;
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
        if (this.decision(category)){
            throw new CustomException("名称已存在");
        }
        Long currentId = BaseContext.getCurrentId();
        category.setUpdateUser(currentId);
        category.setCreateTime(LocalDateTime.now());
        categoryMapper.add(category);
    }
}
