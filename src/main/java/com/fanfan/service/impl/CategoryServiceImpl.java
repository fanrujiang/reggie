package com.fanfan.service.impl;

import com.fanfan.bean.PageBean;
import com.fanfan.mapper.CategoryMapper;
import com.fanfan.pojo.Category;
import com.fanfan.service.CategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public PageBean page(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        Page<Category> p = categoryMapper.findPage();

        return new PageBean(p.getTotal(),p.getResult());
    }
}
