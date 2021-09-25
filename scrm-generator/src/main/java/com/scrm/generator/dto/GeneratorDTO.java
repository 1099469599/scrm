/**
 * Copyright(C) 2021 Fugle Technology Co., Ltd. All rights reserved.
 */
package com.scrm.generator.dto;

import lombok.Data;

/**
 * @author LiuZhengyang
 * @version $Id$
 * @since 2021年09月25日 23:16
 */
@Data
public class GeneratorDTO {

    /**
     * 表名
     */
    private String tableName;
    /**
     * 表注释
     */
    private String tableComment;
    /**
     * 列名
     */
    private String columnName;
    /**
     * 列注释
     */
    private String columnComment;
    /**
     * 列类型
     */
    private String columnType;
    /**
     * 是否是必要的
     */
    private Integer isRequired;
    /**
     * 是否是主键
     */
    private Integer isPk;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否是自增
     */
    private String isIncrement;
    /**
     * Java别名
     */
    private String javaField;
    /**
     * Java类型
     */
    private String javaType;

}