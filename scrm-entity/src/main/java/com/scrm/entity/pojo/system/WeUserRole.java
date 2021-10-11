package com.scrm.entity.pojo.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.scrm.entity.common.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 用户和角色关联表对象 we_user_role
 *
 * @author S-CRM
 * @date 2021-10-11 11:27:13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("we_user_role")
@SuperBuilder
public class WeUserRole extends BaseModel {
    /**
     * 角色ID(=we_role.id)
     */
    private Long roleId;
    /**
     * 用户Id(=we_user.id)
     */
    private Long userId;
    /**
     * 用户ID(=we_user.user_id)
     */
    private String userComId;
}
