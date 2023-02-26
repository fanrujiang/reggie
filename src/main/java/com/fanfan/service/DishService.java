package com.fanfan.service;

import com.fanfan.bean.PageBean;
import com.fanfan.dto.DishDto;

public interface DishService {
    PageBean page(int page, int pageSize, String name);

    /**
     * 根据id查询菜品信息
     *
     * @param id
     * @return
     */
    DishDto getById(Long id);
}
