package com.scrm.transform.user;

import com.scrm.dto.user.UserInfo;
import com.scrm.entity.pojo.user.WeUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author liuKevin
 * @date 2021年10月08日 16:57
 */
@Mapper(componentModel = "spring")
public interface MWeUserMapper {

    MWeUserMapper INSTANCE = Mappers.getMapper(MWeUserMapper.class);

    UserInfo userInfoConvert(WeUser pojo);


}
