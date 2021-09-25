/**
 * Copyright(C) 2021 Fugle Technology Co., Ltd. All rights reserved.
 */
package com.scrm.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.scrm.generator.service.GeneratorService;
import com.scrm.generator.utils.SpringUtils;

/**
 * @author LiuZhengyang
 * @since 2021年09月25日 23:01
 */
@SpringBootApplication
@MapperScan
public class GeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class);

        GeneratorService bean = SpringUtils.getBean(GeneratorService.class);
        bean.generatorCode("/user", "sys_user");
    }
}