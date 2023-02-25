package com.fanfan.dto;

import com.fanfan.bean.Dish;
import com.fanfan.bean.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;

    //销售量
    private Integer saleNum;
}
