package com.scrm.user;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.scrm.BaseTest;
import com.scrm.dto.user.UserInfo;
import com.scrm.entity.pojo.user.WeUser;
import com.scrm.service.biz.user.WeUserService;
import com.scrm.transform.user.UserInfoMapper;
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
    private WeUserService weUserService;

    @Test
    public void insertTest() {
        WeUser byId = weUserService.getById(10000L);
        WeUser one = weUserService.getOne(Wrappers.lambdaQuery(WeUser.class).eq(WeUser::getUserId, "11"));
        UserInfo obj = weUserService.getObj(Wrappers.lambdaQuery(WeUser.class).eq(WeUser::getId, 10000L), i -> UserInfoMapper.INSTANCE.userInfoConvert((WeUser) i));
        weUserService.removeById(obj.getId());
        log.info("sss");
    }


}
