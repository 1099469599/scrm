/**
 * Copyright(C) 2021 Fugle Technology Co., Ltd. All rights reserved.
 */
package com.scrm.generator.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.scrm.generator.dto.GeneratorDTO;

/**
 * @author LiuZhengyang
 * @version $Id$
 * @since 2021年09月25日 23:21
 */
public interface GeneratorMapper {

    /**
     * 根据表名获取表的注释
     *
     * @param tableNames 表名集合
     *
     * @return 表名 + 表注释
     */
    List<GeneratorDTO> getTables(@Param("tableNames") List<String> tableNames);

    /**
     * 根据表名获取列结构
     *
     * @param tableName 表名
     *
     * @return 列结构
     */
    List<GeneratorDTO> getColumn(@Param("tableName") String tableName);
}