package com.fanfan.mapper;

import com.fanfan.dto.DishDto;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper {

    /**
     * 分页查询
     * @param name
     * @return
     */
    Page<DishDto> findPage(String name);

    DishDto getById(Long id);
}
