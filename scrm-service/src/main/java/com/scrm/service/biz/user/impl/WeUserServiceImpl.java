package com.scrm.service.biz.user.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.scrm.annotation.CacheExpire;
import com.scrm.dto.user.UserInfo;
import com.scrm.entity.common.PageModel;
import com.scrm.entity.enums.UserType;
import com.scrm.entity.pojo.user.WeUser;
import com.scrm.exception.BizException;
import com.scrm.manager.user.WeUserManager;
import com.scrm.service.biz.user.WeUserService;
import com.scrm.transform.user.MWeUserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public IPage<UserInfo> page(Integer pageNum, Integer pageSize) {
        PageModel pageModel = PageModel.builder().currentPageIndex(pageNum).currentPageSize(pageSize).build();
        return page(pageModel, Wrappers.emptyWrapper(), MWeUserMapper.INSTANCE::userInfoConvert);
    }

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

    /**
     * 查询当前用户权限下的用户Id集合
     *
     * @param corpId 企业唯一标示
     * @param userId 用户Id(=we_user.id)
     * @return 人员Id集合
     */
    @Override
    public List<Long> getPermissionUserList(String corpId, Long userId) {
        // TODO
        return null;
    }
}
