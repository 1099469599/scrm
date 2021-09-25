/**
 * Copyright(C) 2021 Fugle Technology Co., Ltd. All rights reserved.
 */
package com.scrm.generator.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author LiuZhengyang
 * @since 2021年09月25日 23:06
 */
@Service
@Slf4j
public class GeneratorService {

    /**
     * 自动根据table创建对应的
     * pojo
     * mapper
     * manager
     * service
     *
     * @param path 生成的路径地址
     * @param tables 表名
     */
    public void generatorCode(String path, String... tables) {

    }

}