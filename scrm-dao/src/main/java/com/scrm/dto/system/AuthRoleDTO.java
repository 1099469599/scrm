package com.scrm.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author liuKevin
 * @date 2021年10月11日 13:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRoleDTO {

    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 创建开始时间
     */
    private Date startDate;
    /**
     * 创建结束时间
     */
    private Date endDate;
    /**
     * 权限列表集合
     */
    private List<Long> permissionList;
    /**
     * 描述
     */
    private String description;
    /**
     * 是否可见, 0是可见, 1是不可见
     */
    @Builder.Default
    private Integer visible = 0;
    /**
     * 数据权限
     */
    private Integer dataScope;


    public Integer getVisible() {
        return visible == null ? 0 : visible;
    }
}
