package com.scrm.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liuKevin
 * @date 2021年10月11日 11:45
 */
@AllArgsConstructor
@Getter
public enum MenuEnum {

    MENU_TYPE(10, "菜单类型的分界点, <10为菜单, =10是接口"),

    MENU_VISIBLE_TRUE(0, "可见"),

    MENU_VISIBLE_FALSE(1, "不可见"),

    MENU_VISIBLE_ADMIN(2, "仅超管可见"),

    MENU_STATUS_TRUE(0, "可用"),

    MENU_STATUS_FALSE(1, "禁用"),
    ;


    private final Integer code;

    private final String msg;


}
