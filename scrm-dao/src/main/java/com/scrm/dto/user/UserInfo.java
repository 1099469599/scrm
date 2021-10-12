package com.scrm.dto.user;

import com.scrm.entity.enums.UserType;
import lombok.Data;

import java.util.List;

/**
 * @author liuKevin
 * @date 2021年10月08日 17:17
 */
@Data
public class UserInfo {

    /**
     * 用户主键Id
     */
    private Long id;
    /**
     * 企业唯一标示
     */
    private String corpId;
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 头像地址
     */
    private String headImageUrl;
    /**
     * 用户名称
     * 如果存在别名, 优先使用别名
     *
     * @see com.scrm.entity.pojo.user.WeUser#getAlias
     * @see com.scrm.entity.pojo.user.WeUser#getUserName
     */
    private String userName;
    /**
     * 部门Id集合
     * 一个用户可以存在于多个部门中
     */
    private List<Long> deptIdList;
    /**
     * 当前用户权限下的用户集合
     */
    private List<Long> permissionUserId;
    /**
     * 接口权限集合
     */
    private List<String> permissionList;
    /**
     * 菜单权限集合
     */
    private List<String> resources;
    /**
     * 用户身份
     * 超管 {@link UserType#SUPER_ADMIN}
     * 企业管理员 {@link UserType#ADMIN}
     * 普通员工 {@link UserType#EMPLOY}
     */
    private UserType userType;


}
