package com.enums;

import lombok.Getter;

/**
 * @author Mango
 * @Date: 2021/1/17 20:08:12
 */
@Getter
public enum ResultEnum {

    /**
     * 通用
     */
    SUCCESS(20000, "成功"),
    ERROR(40000, "错误");

    private final int code;
    private final String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
