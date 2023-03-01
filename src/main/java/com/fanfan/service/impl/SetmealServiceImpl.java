package com.fanfan.service.impl;

import com.fanfan.bean.PageBean;
import com.fanfan.bean.SetmealDish;
import com.fanfan.mapper.SetmealMapper;
import com.fanfan.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;


    @Override
    public PageBean page(int page, int pageSize, String name) {
        PageHelper.startPage(page, pageSize);
        Page<SetmealDish> setmealDishes = setmealMapper.findPage(name);
        return new PageBean((int) setmealDishes.getTotal(),setmealDishes.getResult());
    }
}
