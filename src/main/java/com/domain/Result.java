package com.domain;

import com.enums.ResultEnum;
import lombok.Data;

/**
 * @author Mango
 * @Date: 2021/1/17 20:08:12
 */
@Data
public class Result<T> {

    private int code;

    private String message;

    private T data;

    public Result() {

    }

    public Result(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMsg();
    }

    public Result(ResultEnum resultEnum, T t) {
        this.code = resultEnum.getCode();
        if (t instanceof String) {
            this.message = t.toString();
        } else {
            this.message = resultEnum.getMsg();
            this.data = t;
        }
    }

    public Result(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

}
