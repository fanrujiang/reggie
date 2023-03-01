package com.fanfan.service;

import com.fanfan.bean.SetmealDish;

import java.util.ArrayList;

public interface SetmealDishService {
    ArrayList<SetmealDish> listBySetmealId(Long id);
}
