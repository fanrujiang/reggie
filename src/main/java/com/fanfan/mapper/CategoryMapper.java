package com.fanfan.mapper;

import com.fanfan.pojo.Category;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CategoryMapper {
    Page<Category> findPage();

    ArrayList<Category> list(Integer type);
}
