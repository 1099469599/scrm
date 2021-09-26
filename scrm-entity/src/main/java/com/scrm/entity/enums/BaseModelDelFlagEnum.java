package com.scrm.entity.enums;

/**
 * @Author：dcy
 * @Description: 公共类删除标识枚举类
 * @Date: 2021/9/2 15:28
 */
public enum BaseModelDelFlagEnum {

    //删除标识（0：正常；1：已删除）
    NORMAL("0", "正常"),
    DELETED("1", "已删除"),
    ;

    public final String code;
    public final String name;

    BaseModelDelFlagEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

}
