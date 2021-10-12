package com.scrm.entity.pojo.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.scrm.entity.common.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 企业微信组织架构对象 we_department
 *
 * @author S-CRM
 * @date 2021-10-12 16:21:13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("we_department")
@SuperBuilder
public class WeDepartment extends BaseModel {

    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门id
     */
    private Long departmentId;
    /**
     * 父部门id
     */
    private Long parentId;
    /**
     * 当前组织全路径
     */
    private String path;
}
