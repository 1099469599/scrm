package com.scrm.service.biz.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scrm.dto.user.UserInfo;
import com.scrm.entity.pojo.user.WeUser;

/**
 * 通讯录相关用户Service接口
 *
 * @author S-CRM
 * @date 2021-10-08 16:46:36
 */
public interface WeUserService extends IService<WeUser> {

    /**
     * 根据手机号获取超管信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    UserInfo getSuperAdminByUsername(String mobile);
}
