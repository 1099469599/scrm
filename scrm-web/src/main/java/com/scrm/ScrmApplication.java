package com.scrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;


@SpringBootApplication()
@Slf4j
public class ScrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrmApplication.class);
        log.info("成功启动");
    }
}
