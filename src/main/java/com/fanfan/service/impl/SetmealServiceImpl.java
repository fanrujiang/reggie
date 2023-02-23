package com.fanfan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanfan.bean.Setmeal;
import com.fanfan.bean.SetmealDish;
import com.fanfan.dto.SetmealDto;
import com.fanfan.mapper.SetmealMapper;
import com.fanfan.service.SetmealDishService;
import com.fanfan.service.SetmealService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {


    private final SetmealDishService setmealDishService;

    public SetmealServiceImpl(SetmealDishService setmealDishService) {
        this.setmealDishService = setmealDishService;
    }

    /**
     * 新增套餐
     *
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {

        //保存套餐的基本信息
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());
            setmealDishService.save(setmealDish);

        }

    }

    /**
     * 停售套餐
     *
     * @param ids
     * @return
     */
    @Override
    public void offStatus(String ids) {
        String[] idss = ids.split(",");
        for (String id : idss) {
            Setmeal setmeal = this.getById(id);
            setmeal.setStatus(0);
            this.updateById(setmeal);
        }
    }

    /**
     * 启售套餐
     *
     * @param ids
     * @return
     */
    @Override
    public void onStatus(String ids) {
        String[] idss = ids.split(",");
        for (String id : idss) {
            Setmeal setmeal = this.getById(id);
            setmeal.setStatus(1);
            this.updateById(setmeal);
        }
    }
}
