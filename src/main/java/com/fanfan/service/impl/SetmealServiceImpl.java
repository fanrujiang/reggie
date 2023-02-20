package com.fanfan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanfan.bean.Setmeal;
import com.fanfan.mapper.SetmealMapper;
import com.fanfan.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
