package com.scrm.transform.system;

import com.scrm.entity.pojo.system.WeMenu;
import com.scrm.vo.system.AuthMenuTreeVO;
import com.scrm.vo.system.AuthPermissionVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author liuKevin
 * @date 2021年10月11日 11:53
 */
@Mapper
public interface MWeMenuMapper {

    MWeMenuMapper INSTANCE = Mappers.getMapper(MWeMenuMapper.class);

    @Mappings({
            @Mapping(source = "name", target = "menuName"),
            @Mapping(source = "type", target = "level"),
            @Mapping(source = "orderNum", target = "sort", defaultValue = "10"),
            @Mapping(source = "visible", target = "visible", defaultValue = "1")
    })
    AuthMenuTreeVO toTreeVO(WeMenu pojo);


    AuthPermissionVO toPermissionVO(WeMenu pojo);


}
