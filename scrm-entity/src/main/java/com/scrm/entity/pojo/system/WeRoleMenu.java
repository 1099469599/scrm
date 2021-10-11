package com.scrm.entity.pojo.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.scrm.entity.common.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 角色和菜单关联表对象 we_role_menu
 *
 * @author S-CRM
 * @date 2021-10-11 11:20:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("we_role_menu")
@SuperBuilder
public class WeRoleMenu extends BaseModel {


    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单ID
     */
    private Long menuId;
}
