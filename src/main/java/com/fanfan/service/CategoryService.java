package com.fanfan.service;

import com.fanfan.bean.PageBean;
import com.fanfan.pojo.Category;

import java.util.ArrayList;

public interface CategoryService {
    /**
     * 分类的分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    PageBean page(int page, int pageSize);

    /**
     * 根据类型查询分类集合
     *
     * @param type 类型
     * @return categories
     */
    ArrayList<Category> list(Integer type);

    /**
     * 判断分类表中名字和排序存在
     *
     * @return 存在返回true 不存在返回false
     */
    boolean decision(Category category);

    /**
     * 新增分类
     *
     * @param category 分类
     */
    void add(Category category);

}
