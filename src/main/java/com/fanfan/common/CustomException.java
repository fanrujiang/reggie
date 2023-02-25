package com.fanfan.common;

/**
 * 自定义异常处理器
 */
public class CustomException extends RuntimeException {
    public CustomException(String massage) {
        super(massage);
    }
}
