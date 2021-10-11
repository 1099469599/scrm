package com.scrm.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liuKevin
 * @date 2021年10月11日 13:48
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {

    ROLE_DATA_SCOPE_ALL(1, "全部数据权限"),
    ROLE_DATA_SCOPE_DEPT(2, "部门的数据权限"),
    ROLE_DATA_SCOPE_IN_DEPT(3, "部门及下属部门的数据权限"),
    ROLE_DATA_SCOPE_PERSON(4, "仅个人数据权限"),
    ROLE_VISIBLE_TRUE(0, "可见"),
    ROLE_VISIBLE_FALSE(1, "不可见"),
    ROLE_STATUS_TRUE(0, "有效"),
    ROLE_STATUS_FALSE(1, "无效"),
    ;

    private final Integer code;

    private final String msg;

}
