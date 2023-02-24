package com.fanfan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fanfan.bean.AddressBook;
import com.fanfan.bean.OrderDetail;
import com.fanfan.bean.Orders;
import com.fanfan.bean.User;
import com.fanfan.common.R;
import com.fanfan.dto.OrdersDto;
import com.fanfan.service.AddressBookService;
import com.fanfan.service.OrderDetailService;
import com.fanfan.service.OrderService;
import com.fanfan.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/order")
public class OrderController {


    private final OrderService orderService;
    private final OrderDetailService orderDetailService;
    private final UserService userService;
    private final AddressBookService addressBookService;


    public OrderController(OrderService orderService, OrderDetailService orderDetailService, UserService userService, AddressBookService addressBookService) {
        this.orderService = orderService;

        this.orderDetailService = orderDetailService;
        this.userService = userService;
        this.addressBookService = addressBookService;
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
    public R<Page> page (int page, int pageSize){
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        Page<OrdersDto> pageDto = new Page<>();

        orderService.page(pageInfo);
        // 将其除了records中的内存复制到pageDto中
        BeanUtils.copyProperties(pageInfo, pageDto, "records");

        List<Orders> records = pageInfo.getRecords();

        List<OrdersDto> collect = records.stream().map((order) -> {
            OrdersDto ordersDto = new OrdersDto();

            BeanUtils.copyProperties(order, ordersDto);
            // 根据订单id查询订单详细信息

            QueryWrapper<OrderDetail> wrapperDetail = new QueryWrapper<>();
            wrapperDetail.eq("order_id", order.getId());

            List<OrderDetail> orderDetails = orderDetailService.list(wrapperDetail);
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
}
