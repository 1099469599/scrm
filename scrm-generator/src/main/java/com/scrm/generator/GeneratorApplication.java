package com.scrm.generator;

import com.scrm.generator.test.WeUser;
import com.scrm.generator.test.WeUserService;
import com.scrm.generator.utils.SpringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * @author LiuZhengyang
 * @since 2021年09月25日 23:01
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.scrm.generator.mapper"})
public class GeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class);

//        GeneratorService bean = SpringUtils.getBean(GeneratorService.class);
//        bean.generatorCode("/Users/mockuai/logs", "we_user");
        WeUserService bean = SpringUtils.getBean(WeUserService.class);
        List<WeUser> list = bean.list();
        System.exit(9);
    }
}