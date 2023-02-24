package com.fanfan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanfan.bean.ShoppingCart;
import com.fanfan.common.BaseContext;
import com.fanfan.mapper.ShoppingCartMapper;
import com.fanfan.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Override
    public void clean() {
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        this.remove(lqw);

    }
}
