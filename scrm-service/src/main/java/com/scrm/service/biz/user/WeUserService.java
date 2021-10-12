package com.scrm.service.biz.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scrm.dto.user.UserInfo;
import com.scrm.entity.pojo.user.WeUser;

import java.util.List;

/**
 * 通讯录相关用户Service接口
 *
 * @author S-CRM
 * @date 2021-10-08 16:46:36
 */
public interface WeUserService extends IService<WeUser> {

    /**
     * 分页
     *
     * @param pageNum  当前页数
     * @param pageSize 每页条数
     */
    IPage<UserInfo> page(Integer pageNum, Integer pageSize);


    /**
     * 根据手机号获取超管信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    UserInfo getSuperAdminByUsername(String mobile);

    /**
     * 查询当前用户权限下的用户Id集合
     *
     * @param corpId 企业唯一标示
     * @param userId 用户Id(=we_user.id)
     * @return 人员Id集合
     */
    List<Long> getPermissionUserList(String corpId, Long userId);
}
