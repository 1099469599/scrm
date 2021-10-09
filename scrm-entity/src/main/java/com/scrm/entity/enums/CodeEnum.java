package com.scrm.entity.enums;

import lombok.AllArgsConstructor;

/**
 * @author liuKevin
 * @date 2021年09月26日 17:46
 */
@AllArgsConstructor
public enum CodeEnum {

    SUCCESS(200, "操作成功"),

    ERROR(500, "操作失败"),

    CHECK_ERROR(1000, "校验公共错误"),
    BIZ_ERROR(1001, "通用的业务错误"),

    TOKEN_ERROR(2000, "token错误"),
    NO_TOKEN_ERROR(2001, "没有token，请重新登录"),
    USER_PASSWORD_ERROR(2002, "用户名和密码错误，请重新输入"),
    USER_UPDATE_PASS_ERROR(2005, "当前密码输入错误，请重新输入"),
    USER_UPDATE_PASS2_ERROR(2006, "两次密码输入不一致，请重新输入"),
    USER_LOCKED_ERROR(2003, "用户已锁定"),
    USER_NO_ROLE_LOCKED_ERROR(2004, "没有角色"),
    USER_NO_PERMISSION_LOCKED_ERROR(2005, "没有权限"),


    UNKNOWN_ERROR(500000, "未知错误");

    private final Integer code;

    private final String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
