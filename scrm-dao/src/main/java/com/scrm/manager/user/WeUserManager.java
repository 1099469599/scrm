package com.scrm.manager.user;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.scrm.dto.user.UserInfo;
import com.scrm.entity.enums.UserType;
import com.scrm.entity.pojo.user.WeUser;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scrm.mapper.user.WeUserMapper;
import com.scrm.transform.user.UserInfoMapper;


/**
 * 通讯录相关用户Manager数据层处理
 *
 * @author S-CRM
 * @date 2021-10-08 16:46:36
 */
public class WeUserManager extends ServiceImpl<WeUserMapper, WeUser> {

    /**
     * 将用户pojo转化成dto
     *
     * @param pojo     pojo
     * @param userType 用户身份
     */
    public UserInfo transformInfo(WeUser pojo, UserType userType) {
        UserInfo info = UserInfoMapper.INSTANCE.userInfoConvert(pojo);
        if (StrUtil.isNotBlank(pojo.getAlias())) {
            // 优先使用别名
            info.setUserName(pojo.getAlias());
        }
        info.setUserType(userType);
        return info;
    }


}
