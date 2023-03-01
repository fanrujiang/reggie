package com.fanfan.service;

import com.fanfan.bean.PageBean;
import com.fanfan.dto.SetmealDto;

public interface SetmealService {

    PageBean page(int page, int pageSize, String name);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    SetmealDto getById(Long id);
}
