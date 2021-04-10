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
    ERROR(40000, "错误"),

    // Service层自定义异常
    SERVICE_SAVE_ERROR(60001, "保存失败"),
    SERVICE_UPDATE_ERROR(60002, "更新失败"),
    SERVICE_DELETE_ERROR(60003, "删除失败"),

    // 数据库抛出异常
    DATA_NOT_FOUNT_ERROR(50001, "无法查找到实体对象"),
    DATA_INTEGRITY_VIOLATION_ERROR(50002, "违反完整性约束");

    private final int code;
    private final String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
