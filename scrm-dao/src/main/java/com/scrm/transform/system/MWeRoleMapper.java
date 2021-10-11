package com.scrm.transform.system;

import com.scrm.entity.pojo.system.WeRole;
import com.scrm.vo.system.AuthRoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author liuKevin
 * @date 2021年10月11日 14:10
 */
@Mapper
public interface MWeRoleMapper {

    MWeRoleMapper INSTANCE = Mappers.getMapper(MWeRoleMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "roleId"),
            @Mapping(source = "remark", target = "description")
    })
    AuthRoleVO toVO(WeRole pojo);

}
