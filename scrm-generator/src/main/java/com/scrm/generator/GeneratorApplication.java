package com.scrm.generator;

import com.scrm.generator.service.GeneratorService;
import com.scrm.generator.utils.SpringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author LiuZhengyang
 * @since 2021年09月25日 23:01
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.scrm.generator.mapper"})
public class GeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class);

        GeneratorService bean = SpringUtils.getBean(GeneratorService.class);
        bean.generatorCode("/Users/mockuai/logs", "we_department");
        System.exit(9);
    }
}