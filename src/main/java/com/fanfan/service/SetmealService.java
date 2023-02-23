package com.fanfan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fanfan.bean.Setmeal;
import com.fanfan.dto.SetmealDto;

public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐
     *
     * @param setmealDto
     */
    void saveWithDish(SetmealDto setmealDto);
}
