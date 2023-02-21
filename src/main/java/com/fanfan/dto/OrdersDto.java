package com.fanfan.dto;

import com.fanfan.bean.OrderDetail;
import com.fanfan.bean.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
