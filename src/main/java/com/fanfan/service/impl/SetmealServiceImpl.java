package com.fanfan.service.impl;

import com.fanfan.bean.PageBean;
import com.fanfan.bean.SetmealDish;
import com.fanfan.dto.SetmealDto;
import com.fanfan.mapper.SetmealDishMapper;
import com.fanfan.mapper.SetmealMapper;
import com.fanfan.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SetmealServiceImpl implements SetmealService {

    private final SetmealMapper setmealMapper;
    private final SetmealDishMapper setmealDishMapper;

    public SetmealServiceImpl(SetmealMapper setmealMapper, SetmealDishMapper setmealDishMapper) {
        this.setmealMapper = setmealMapper;
        this.setmealDishMapper = setmealDishMapper;
    }


    @Override
    public PageBean page(int page, int pageSize, String name) {
        PageHelper.startPage(page, pageSize);
        Page<SetmealDish> setmealDishes = setmealMapper.findPage(name);
        return new PageBean((int) setmealDishes.getTotal(), setmealDishes.getResult());
    }

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    @Override
    public SetmealDto getById(Long id) {
        ArrayList<SetmealDish> setmealDishes = setmealDishMapper.listBySetmealId(id);
        SetmealDto setmealDto = setmealMapper.getById(id);
        setmealDto.setSetmealDishes(setmealDishes);
        return setmealDto;
    }
}
