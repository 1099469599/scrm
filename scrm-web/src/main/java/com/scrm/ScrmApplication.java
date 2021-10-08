package com.scrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@MapperScan
@Slf4j
public class ScrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrmApplication.class);
        log.info("(♥◠‿◠)ﾉﾞ  scrm启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "  _____  _____ _____  __  __ \n" +
                " / ____|/ ____|  __ \\|  \\/  |\n" +
                "| (___ | |    | |__) | \\  / |\n" +
                " \\___ \\| |    |  _  /| |\\/| |\n" +
                " ____) | |____| | \\ \\| |  | |\n" +
                "|_____/ \\_____|_|  \\_\\_|  |_|");
    }
}
