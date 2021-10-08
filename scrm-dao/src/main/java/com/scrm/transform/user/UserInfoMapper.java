package com.scrm.transform.user;

import com.scrm.dto.system.LoginDTO;
import com.scrm.query.system.LoginQuery;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author liuKevin
 * @date 2021年10月08日 16:57
 */
public interface UserInfoMapper {

    UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);

    @Mappings({
            @Mapping(source = "userName", target = "mobile")
    })
    LoginDTO loginQueryConvert(LoginQuery query);






}
