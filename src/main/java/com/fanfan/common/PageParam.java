package com.fanfan.common;

import lombok.Data;

/**
 * 分页参数
 *
 * @author Admin
 */
@Data
public class PageParam {
    private int page;
    private int pageSize;
    private String name;
}
