package com.fanfan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanfan.bean.DishFlavor;
import com.fanfan.mapper.DishFlavorMapper;
import com.fanfan.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
