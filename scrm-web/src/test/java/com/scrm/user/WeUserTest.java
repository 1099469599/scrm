package com.scrm.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.scrm.BaseTest;
import com.scrm.dto.user.UserInfo;
import com.scrm.entity.pojo.user.WeUser;
import com.scrm.service.biz.user.impl.WeUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liuKevin
 * @date 2021年10月09日 11:32
 */
@Slf4j
public class WeUserTest extends BaseTest {

    @Autowired
    private WeUserServiceImpl weUserService;

    @Test
    public void insertTest() {
        IPage<UserInfo> page = weUserService.page(1, 1);
        IPage<UserInfo> page2 = weUserService.page(2, 1);
        IPage<UserInfo> page3 = weUserService.page(3, 1);
        log.info("sss");
    }


    @Test
    public void removeTest() {
        weUserService.remove(Wrappers.lambdaQuery(WeUser.class)
                .eq(WeUser::getCorpId, "1111")
                .eq(WeUser::getHeadImageUrl, "11"));

//        WeUser byId = weUserService.getById(10000);
//        if (Objects.nonNull(byId)) {
//            weUserService.removeById(byId);
//        }
    }

}
