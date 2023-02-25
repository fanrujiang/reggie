package com.fanfan.service.impl;

import com.fanfan.bean.PageBean;
import com.fanfan.mapper.DishMapper;
import com.fanfan.pojo.Dish;
import com.fanfan.pojo.Employee;
import com.fanfan.service.DishService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Override
    public PageBean page(int page, int pageSize, String name) {
        PageHelper.startPage(page,pageSize);
        Page<Dish> p =dishMapper.findPage(name);
        return new PageBean(p.getTotal(),p.getResult());
    }
}
