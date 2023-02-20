package com.fanfan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fanfan.bean.Category;

public interface CategoryService extends IService<Category> {

    //根据id删除分类
    public void remove(Long id);
}
