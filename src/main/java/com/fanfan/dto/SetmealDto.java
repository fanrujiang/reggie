package com.fanfan.dto;


import com.fanfan.bean.Setmeal;
import com.fanfan.bean.SetmealDish;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SetmealDto extends Setmeal implements Serializable {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
    //销售量
    private Integer saleNum;
}
