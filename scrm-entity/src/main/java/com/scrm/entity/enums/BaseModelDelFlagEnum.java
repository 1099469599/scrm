package com.scrm.entity.enums;

/**
 * 公共类删除标识枚举类
 *
 * @author liuKevin
 * @date 2021年10月08日 19:58
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
