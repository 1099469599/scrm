package com.scrm;

import com.scrm.entity.pojo.user.WeUser;
import com.scrm.service.biz.user.WeUserService;
import com.scrm.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication
@MapperScan(basePackages = "com.scrm.mapper.*")
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

        WeUserService bean = SpringUtils.getBean(WeUserService.class);
        List<WeUser> list = bean.list();
        System.exit(9);

    }
}
