package com.fanfan.service;

import com.fanfan.bean.PageBean;

public interface SetmealService {

    PageBean page(int page, int pageSize, String name);
}
