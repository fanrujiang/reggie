package com.fanfan.mapper;

import com.fanfan.pojo.Category;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    Page<Category> findPage();
}
