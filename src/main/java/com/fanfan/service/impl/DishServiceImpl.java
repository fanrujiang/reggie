package com.fanfan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanfan.bean.Dish;
import com.fanfan.mapper.DishMapper;
import com.fanfan.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
