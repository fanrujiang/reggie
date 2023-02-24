package com.fanfan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fanfan.bean.*;
import com.fanfan.common.BaseContext;
import com.fanfan.common.R;
import com.fanfan.dto.OrdersDto;
import com.fanfan.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/order")
public class OrderController {


    private final OrderService orderService;
    private final OrderDetailService orderDetailService;
    private final UserService userService;
    private final AddressBookService addressBookService;

    private final ShoppingCartService shoppingCartService;


    public OrderController(OrderService orderService, OrderDetailService orderDetailService, UserService userService, AddressBookService addressBookService, ShoppingCartService shoppingCartService) {
        this.orderService = orderService;

        this.orderDetailService = orderDetailService;
        this.userService = userService;
        this.addressBookService = addressBookService;
        this.shoppingCartService = shoppingCartService;
    }

    /**
     * 用户下单
     *
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        orderService.submitl(orders);
        return R.success("下单成功");
    }


    @GetMapping("userPage")
    public R<Page> page(int page, int pageSize) {
        LambdaQueryWrapper<Orders> qw = new LambdaQueryWrapper<>();
        qw.eq(Orders::getUserId, BaseContext.getCurrentId());
        qw.orderByDesc(Orders::getCheckoutTime);
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        Page<OrdersDto> pageDto = new Page<>();

        orderService.page(pageInfo, qw);
        // 将其除了records中的内存复制到pageDto中
        BeanUtils.copyProperties(pageInfo, pageDto, "records");

        List<Orders> records = pageInfo.getRecords();

        List<OrdersDto> collect = records.stream().map((order) -> {
            OrdersDto ordersDto = new OrdersDto();

            BeanUtils.copyProperties(order, ordersDto);
            // 根据订单id查询订单详细信息

            //QueryWrapper<OrderDetail> wrapperDetail = new QueryWrapper<>();
            //wrapperDetail.eq("order_id", order.getId());
            LambdaQueryWrapper<OrderDetail> lqw = new LambdaQueryWrapper<>();
            lqw.eq(OrderDetail::getOrderId, order.getId());

            List<OrderDetail> orderDetails = orderDetailService.list(lqw);
            ordersDto.setOrderDetails(orderDetails);

            // 根据userId 查询用户姓名
            Long userId = order.getUserId();
            User user = userService.getById(userId);
            ordersDto.setUserName(user.getName());
            ordersDto.setPhone(user.getPhone());

            // 获取地址信息
            Long addressBookId = order.getAddressBookId();
            AddressBook addressBook = addressBookService.getById(addressBookId);
            ordersDto.setAddress(addressBook.getDetail());
            ordersDto.setConsignee(addressBook.getConsignee());

            return ordersDto;
        }).collect(Collectors.toList());

        pageDto.setRecords(collect);
        return R.success(pageDto);

    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String number, String beginTime, String endTime) {
        //分页构造器对象
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        //构造条件查询对象
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();

        //添加查询条件  动态sql  字符串使用StringUtils.isNotEmpty这个方法来判断
        //这里使用了范围查询的动态SQL，这里是重点！！！
        queryWrapper.like(number != null, Orders::getNumber, number)
                .gt(StringUtils.isNotEmpty(beginTime), Orders::getOrderTime, beginTime)
                .lt(StringUtils.isNotEmpty(endTime), Orders::getOrderTime, endTime);

        orderService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    //抽离的一个方法，通过订单id查询订单明细，得到一个订单明细的集合
    //这里抽离出来是为了避免在stream中遍历的时候直接使用构造条件来查询导致eq叠加，从而导致后面查询的数据都是null
    public List<OrderDetail> getOrderDetailListByOrderId(Long orderId) {
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId, orderId);
        List<OrderDetail> orderDetailList = orderDetailService.list(queryWrapper);
        return orderDetailList;
    }

    /**
     * 派送订单
     *
     * @param orders
     * @return
     */
    @PutMapping
    public R<Orders> dispatch(@RequestBody Orders orders) {
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(orders.getId() != null, Orders::getId, orders.getId());
        Orders one = orderService.getOne(queryWrapper);

        one.setStatus(orders.getStatus());
        orderService.updateById(one);
        return R.success(one);
    }

    @Transactional
    @PostMapping("/again")
    public R<String> again(@RequestBody Orders orders) {
        //获取订单id
        Long id = orders.getId();
        LambdaQueryWrapper<OrderDetail> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId,id);
        //获取所有该订单中的菜品
        List<OrderDetail> orderDetailList = orderDetailService.list(queryWrapper);

        shoppingCartService.clean();
        //获取用户id
        Long userId = BaseContext.getCurrentId();
        //因为菜品详细表和购物车表内容很像，所有容易相互赋值
        List<ShoppingCart> shoppingCartList=orderDetailList.stream().map((item)->{
            ShoppingCart shoppingCart=new ShoppingCart();
            shoppingCart.setUserId(userId);
            shoppingCart.setImage(item.getImage());
            Long dishId = item.getDishId();
            Long setmealId = item.getSetmealId();
            if (dishId != null) {
                //如果是菜品那就添加菜品的查询条件
                shoppingCart.setDishId(dishId);
            } else {
                //添加到购物车的是套餐
                shoppingCart.setSetmealId(setmealId);
            }
            shoppingCart.setName(item.getName());
            shoppingCart.setDishFlavor(item.getDishFlavor());
            shoppingCart.setNumber(item.getNumber());
            shoppingCart.setAmount(item.getAmount());
            shoppingCart.setCreateTime(LocalDateTime.now());

            return shoppingCart;
        }).collect(Collectors.toList());

        //把携带数据的购物车批量插入购物车表  这个批量保存的方法要使用熟练！！！
        shoppingCartService.saveBatch(shoppingCartList);

        return R.success("操作成功");

    }


}
