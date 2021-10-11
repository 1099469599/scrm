package com.scrm.entity.pojo.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.scrm.entity.common.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 角色信息表对象 we_role
 *
 * @author S-CRM
 * @date 2021-10-11 11:12:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("we_role")
@SuperBuilder
public class WeRole extends BaseModel {
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 显示顺序
     */
    private Long roleSort;
    /**
     * 数据范围(1:全部数据权限 2:本部门数据权限 3:本部门及以下数据权限 4:仅个人数据权限)
     */
    private Long dataScope;
    /**
     * 是否可见(0=可见,1=不可见)
     */
    private Long visible;
    /**
     * 是否有效(0=有效, 1=无效)
     */
    private Long status;
    /**
     * 备注
     */
    private String remark;
}
