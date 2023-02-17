package com.fanfan.service;

import com.fanfan.bean.PageBean;

public interface DishService {
    PageBean page(int page, int pageSize, String name);
}
