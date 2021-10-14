package com.scrm.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liuKevin
 * @date 2021年10月13日 16:58
 */
@AllArgsConstructor
@Getter
public enum AccessTokenEnum {

    SUITE_APP("suiteApp", "三方应用token"),

    NO_ACCESS("noAccess", "无需token"),

    COMMON("common", "通用token, 使用corpSecret"),

    CONTACT("contactSecret", "通讯录token, 使用contactSecret"),
    ;

    private final String code;

    private final String desc;
}
