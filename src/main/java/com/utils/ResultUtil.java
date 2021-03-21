package com.utils;

import com.domain.Result;
import com.enums.ResultEnum;

/**
 * @author Mango
 * @Date: 2021/1/17 20:08:12
 */
public class ResultUtil {

    public static <T> Result<T> success(T t) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), t);
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg());
    }

    public static <T> Result<T> error() {
        return new Result<>(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
    }

    public static <T> Result<T> error(Exception e, ResultEnum resultEnum) {
        return new Result<>(resultEnum.getCode(), e.getMessage());
    }

    public static <T> Result<T> error(ResultEnum resultEnum) {
        return new Result<>(resultEnum.getCode(), resultEnum.getMsg());
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(ResultEnum.ERROR.getCode(), msg);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg);
    }
}
