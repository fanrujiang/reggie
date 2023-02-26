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
     * @param type 类型
     * @return categories
     */
    ArrayList<Category> list(Integer type);
}
