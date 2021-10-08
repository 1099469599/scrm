package com.scrm.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liuKevin
 * @date 2021年10月08日 20:16
 */
@AllArgsConstructor
@Getter
public enum UserType {

    SUPER_ADMIN("超管"),

    ADMIN("管理员"),

    EMPLOY("普通员工");

    private final String desc;
}
