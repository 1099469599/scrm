package com.scrm.transform.user;

import com.scrm.dto.system.LoginDTO;
import com.scrm.dto.user.UserInfo;
import com.scrm.entity.pojo.user.WeUser;
import com.scrm.query.system.LoginQuery;
import org.mapstruct.factory.Mappers;

/**
 * @author liuKevin
 * @date 2021年10月08日 16:57
 */
public interface UserInfoMapper {

    UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);

    LoginDTO loginQueryConvert(LoginQuery query);

    UserInfo userInfoConvert(WeUser pojo);


}
