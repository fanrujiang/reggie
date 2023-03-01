package com.fanfan.mapper;

import com.fanfan.bean.SetmealDish;
import com.fanfan.dto.SetmealDto;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetmealMapper {
    Page<SetmealDish> findPage(String name);
}
