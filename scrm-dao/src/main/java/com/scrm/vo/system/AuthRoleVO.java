package com.scrm.vo.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author liuKevin
 * @date 2021年10月11日 13:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRoleVO {

    /**
     * 角色Id
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 角色绑定菜单集合
     */
    private List<AuthMenuTreeVO> menuList;

    /**
     * 描述
     */
    private String description;

    /**
     * 权限范围
     * {@link com.scrm.entity.enums.RoleEnum#ROLE_DATA_SCOPE_ALL}
     * {@link com.scrm.entity.enums.RoleEnum#ROLE_DATA_SCOPE_DEPT}
     * {@link com.scrm.entity.enums.RoleEnum#ROLE_DATA_SCOPE_IN_DEPT}
     * {@link com.scrm.entity.enums.RoleEnum#ROLE_DATA_SCOPE_PERSON}
     */
    @Builder.Default
    private Integer dataScope = 4;

    public Integer getDataScope() {
        return dataScope == null ? 4 : dataScope;
    }
}
