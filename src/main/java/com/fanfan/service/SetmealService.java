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

    /**
     * 停售套餐
     *
     * @param ids
     * @return
     */
    void offStatus(String ids);

    /**
     * 启售套餐
     *
     * @param ids
     * @return
     */
    void onStatus(String ids);

    /**
     * 删除套餐
     * @param ids
     */
    void delete(String ids);

    void updateSetmeal(SetmealDto setmealDto);
}
