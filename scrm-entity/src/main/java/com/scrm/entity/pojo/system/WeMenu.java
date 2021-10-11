package com.scrm.entity.pojo.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.scrm.entity.common.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 菜单权限表对象 we_menu
 *
 * @author S-CRM
 * @date 2021-10-11 11:17:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("we_menu")
@SuperBuilder
public class WeMenu extends BaseModel {


    /**
     * 父菜单ID
     */
    private Long parentId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单类型(菜单<10, 接口=10)
     */
    private Long type;
    /**
     * 显示顺序
     */
    private Long orderNum;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 组件路径
     */
    private String component;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 菜单状态(0:显示,1:隐藏,2:仅超管可见)
     */
    private Long visible;
    /**
     * 菜单状态（0正常,1停用）
     */
    private Long status;
    /**
     * 备注
     */
    private String remark;
}
