package com.fanfan.service;

import com.fanfan.bean.PageBean;

public interface CategoryService {
    PageBean page(int page, int pageSize);
}
