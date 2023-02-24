package com.fanfan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fanfan.bean.ShoppingCart;
import com.fanfan.common.BaseContext;
import com.fanfan.common.CustomException;
import com.fanfan.common.R;
import com.fanfan.service.ShoppingCartService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    /**
     * 购物车查询
     *
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        lqw.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(lqw);
        return R.success(list);
    }

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        // 首先查询在其用户的购物表中是否存在 如果存在则在此基础上添加数量即可 如不存在则添加
        // 获取用户的id

        QueryWrapper<ShoppingCart> wrapper = new QueryWrapper<>();
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            throw new CustomException("请用户先进行登陆");
        }
        shoppingCart.setUserId(userId);

        wrapper.eq("user_id", userId);

        if (shoppingCart.getDishId() != null) { // 表示本次为菜品
            wrapper.eq("dish_id", shoppingCart.getDishId());
        } else {
            wrapper.eq("setmeal_id", shoppingCart.getSetmealId());
        }

        ShoppingCart shoppingOne = shoppingCartService.getOne(wrapper);

        if (shoppingOne == null) {
            // 表示用户第一次添加购物车
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            shoppingOne = shoppingCart;
        } else {
            // 表示用户在其购物车中已经添加
            shoppingOne.setNumber(shoppingOne.getNumber() + 1);
            shoppingCartService.updateById(shoppingOne);
        }

        return R.success(shoppingOne);
    }

}
