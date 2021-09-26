package com.scrm.entity.enums;

import lombok.AllArgsConstructor;

/**
 * @author liuKevin
 * @date 2021年09月26日 17:46
 */
@AllArgsConstructor
public enum CodeEnum {

    SUCCESS(200, "操作成功"),

    ERROR(500, "操作失败"),;

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
