package com.fanfan.service.impl;

import com.fanfan.bean.SetmealDish;
import com.fanfan.mapper.SetmealDishMapper;
import com.fanfan.service.SetmealDishService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SetmealDishServiceImpl implements SetmealDishService {
    private final SetmealDishMapper setmealDishMapper;

    public SetmealDishServiceImpl(SetmealDishMapper setmealDishMapper) {
        this.setmealDishMapper = setmealDishMapper;
    }

    @Override
    public ArrayList<SetmealDish> listBySetmealId(Long id) {
        return setmealDishMapper.listBySetmealId(id);
    }
}
