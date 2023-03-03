package com.fanfan.mapper;

import com.fanfan.bean.Dish;
import com.fanfan.dto.DishDto;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface DishMapper {

    /**
     * 分页查询
     * @param name
     * @return
     */
    Page<DishDto> findPage(String name);

    DishDto getById(Long id);

    ArrayList<Dish> getByCategoryId(Long categoryId);

    void update(Dish dish);
}
