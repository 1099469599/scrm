package com.scrm.service.biz.user.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.scrm.dto.user.UserInfo;
import com.scrm.entity.enums.UserType;
import com.scrm.entity.pojo.user.WeUser;
import com.scrm.exception.BizException;
import com.scrm.manager.user.WeUserManager;
import com.scrm.service.biz.user.WeUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * 通讯录相关用户Service业务层处理
 *
 * @author S-CRM
 * @date 2021-10-08 16:46:36
 */
@Service
public class WeUserServiceImpl extends WeUserManager implements WeUserService {

    @Value("${default_corp:wwb7bc0ee558e60842}")
    private String DEFAULT_CORP;

    /**
     * 根据手机号获取超管信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    @Override
    public UserInfo getSuperAdminByUsername(String mobile) {
        WeUser one = getOne(Wrappers.lambdaQuery(WeUser.class).eq(WeUser::getCorpId, DEFAULT_CORP).eq(WeUser::getMobile, mobile).last("limit 1"));
        Optional.ofNullable(one).orElseThrow(() -> new BizException("当前用户不存在"));
        return transformInfo(one, UserType.SUPER_ADMIN);
    }
}
