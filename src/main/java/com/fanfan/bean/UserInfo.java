package com.fanfan.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {

    private String username;

    private String sex;
}
