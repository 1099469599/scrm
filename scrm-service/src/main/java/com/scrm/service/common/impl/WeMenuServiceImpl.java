package com.scrm.service.common.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.scrm.context.BaseContextHandler;
import com.scrm.entity.enums.MenuEnum;
import com.scrm.entity.pojo.system.WeMenu;
import com.scrm.entity.pojo.system.WeRole;
import com.scrm.entity.pojo.system.WeRoleMenu;
import com.scrm.manager.system.WeMenuManager;
import com.scrm.service.common.WeMenuService;
import com.scrm.service.common.WeRoleMenuService;
import com.scrm.service.common.WeRoleService;
import com.scrm.transform.system.MWeMenuMapper;
import com.scrm.vo.system.AuthMenuTreeVO;
import com.scrm.vo.system.AuthPermissionVO;
import com.scrm.vo.system.AuthRouterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 菜单权限表Service业务层处理
 *
 * @author S-CRM
 * @date 2021-10-11 11:17:21
 */
@Service
@Slf4j
public class WeMenuServiceImpl extends WeMenuManager implements WeMenuService {

    @Autowired
    private WeRoleService weRoleService;
    @Autowired
    private WeRoleMenuService weRoleMenuService;

    /**
     * 用户登陆时, 根据用户Id找到整个路由
     *
     * @param corpId 企业唯一标示
     * @param userId 用户Id(=we_user.id)
     * @return 页面路由
     */
    @Override
    public List<AuthRouterVO> getRout(String corpId, Long userId) {
        // 获取菜单集合
        List<AuthMenuTreeVO> menuList = selectMenuList(corpId, userId);
        // 计算得到层级
        List<AuthMenuTreeVO> childPerms = getChildPerms(menuList, 0);
        // 最终汇总成路由
        return buildRoute(childPerms);
    }

    /**
     * 查询所有的菜单列表(超管视角下)
     *
     * @return 菜单集合
     */
    @Override
    public List<AuthMenuTreeVO> selectAllMenu() {
        return super.list(Wrappers.lambdaQuery(WeMenu.class)
                .lt(WeMenu::getType, MenuEnum.MENU_TYPE.getCode()))
                .stream().map(MWeMenuMapper.INSTANCE::toTreeVO).collect(Collectors.toList());
    }

    /**
     * 根据用户Id查询当前用户可以看到的菜单集合(反推父菜单)
     *
     * @param corpId 企业唯一标示
     * @param userId 用户Id(=we_user.id)
     * @return 菜单列表
     */
    @Override
    public List<AuthMenuTreeVO> selectMenuList(String corpId, Long userId) {
        if (BaseContextHandler.isSuperAdmin()) {
            return selectAllMenu();
        }
        // 得到当前用户的角色集合
        List<Long> roleIds = weRoleService.getRole(corpId, userId).stream().map(WeRole::getId).collect(Collectors.toList());
        // 获取菜单集合
        return selectMenuList(corpId, roleIds);
    }

    /**
     * 查询所有的接口集合(超管视角下)
     *
     * @return 接口列表
     */
    @Override
    public List<AuthPermissionVO> selectAllPermission() {
        return super.list(Wrappers.lambdaQuery(WeMenu.class)
                .eq(WeMenu::getType, MenuEnum.MENU_TYPE.getCode()))
                .stream().filter(Objects::nonNull).map(MWeMenuMapper.INSTANCE::toPermissionVO).collect(Collectors.toList());
    }

    /**
     * 根据用户Id查询当前用户可以看到的接口集合
     *
     * @param corpId 企业唯一标示
     * @param userId 用户Id(=we_user.id)
     * @return 接口列表
     */
    @Override
    public List<AuthPermissionVO> selectPermissionList(String corpId, Long userId) {
        if (BaseContextHandler.isSuperAdmin()) {
            return selectAllPermission();
        }
        // 得到当前用户的角色集合
        List<Long> roleIds = weRoleService.getRole(corpId, userId).stream().map(WeRole::getId).collect(Collectors.toList());
        //
        return selectPermissionList(corpId, roleIds);
    }


    /**
     * 根据角色Id查询当前角色绑定的菜单集合(不受用户权限限制)
     *
     * @param corpId 企业唯一Id
     * @param roleId 角色Id
     * @return 菜单集合
     */
    @Override
    public List<AuthMenuTreeVO> selectMenuListByRoleId(String corpId, Long roleId) {
        return selectMenuList(corpId, CollectionUtil.newArrayList(roleId));
    }

    /**
     * 根据角色Id查询当前角色绑定的接口集合(不受用户权限限制)
     *
     * @param corpId 企业唯一标示
     * @param roleId 角色Id
     * @return 接口权限集合
     */
    @Override
    public List<AuthPermissionVO> selectPermissionByRoleId(String corpId, Long roleId) {
        return selectPermissionList(corpId, CollectionUtil.newArrayList(roleId));
    }

    /**
     * 根据角色Id查询当前角色绑定的权限集合(受当前用户权限限制)
     *
     * @param corpId 企业唯一标示
     * @param userId 用户Id
     * @param roleId 角色Id
     * @return 菜单集合
     */
    @Override
    public List<AuthPermissionVO> selectPermissionByRoleId(String corpId, Long userId, Long roleId) {
        // 首先查询出该角色的菜单接口
        List<Long> menuIds = selectMenuListByRoleId(corpId, roleId).stream().map(AuthMenuTreeVO::getId).collect(Collectors.toList());
        // 获取接口集合同时根据menuIds进行过滤
        return selectPermissionList(corpId, userId).stream().filter(k -> menuIds.contains(k.getParentId())).collect(Collectors.toList());
    }


    /**
     * 获取菜单的底层通用方法
     *
     * @param corpId  企业唯一标示
     * @param roleIds 角色Id集合
     * @return 菜单集合
     */
    private List<AuthMenuTreeVO> selectMenuList(String corpId, List<Long> roleIds) {
        if (BaseContextHandler.isSuperAdmin()) {
            // 超管直接返回全菜单
            return selectAllMenu();
        }
        List<AuthMenuTreeVO> result = new ArrayList<>();
        // 查询到关联关系
        List<Long> menuList = weRoleMenuService.list(Wrappers.lambdaQuery(WeRoleMenu.class)
                .eq(StrUtil.isNotBlank(corpId), WeRoleMenu::getCorpId, corpId)
                .in(CollectionUtil.isNotEmpty(roleIds), WeRoleMenu::getRoleId, roleIds))
                .stream().map(WeRoleMenu::getMenuId).distinct().collect(Collectors.toList());
        // 分组查询出所有的menu
        List<List<Long>> partitions = Lists.partition(menuList, 30);
        partitions.forEach(k -> result.addAll(
                super.list(Wrappers.lambdaQuery(WeMenu.class)
                        .in(WeMenu::getId, k)
                        // menuType < 10
                        .lt(WeMenu::getType, MenuEnum.MENU_TYPE.getCode())
                        // visible < 2
                        .lt(WeMenu::getVisible, MenuEnum.MENU_VISIBLE_ADMIN.getCode()))
                        .stream().map(MWeMenuMapper.INSTANCE::toTreeVO).collect(Collectors.toList())));
        return result;
    }


    /**
     * 获取接口权限的底层通用方法
     *
     * @param corpId  企业唯一标示
     * @param roleIds 角色Id集合
     * @return 接口权限集合
     */
    private List<AuthPermissionVO> selectPermissionList(String corpId, List<Long> roleIds) {
        if (BaseContextHandler.isSuperAdmin()) {
            // 超管直接返回所有的接口
            return selectAllPermission();
        }
        if (CollectionUtil.isEmpty(roleIds)) {
            log.error("[selectPermissionList] 当前用户没有绑定任何角色, 仍旧进入到当前方法内部, 请查验逻辑是否正确, corpId:[{}], user:{}", corpId, BaseContextHandler.getId());
            return new ArrayList<>();
        }
        List<AuthPermissionVO> result = new ArrayList<>();
        // 查询到管理关系
        List<Long> permissionList = weRoleMenuService.list(Wrappers.lambdaQuery(WeRoleMenu.class)
                .eq(StrUtil.isNotBlank(corpId), WeRoleMenu::getCorpId, corpId)
                .in(CollectionUtil.isNotEmpty(roleIds), WeRoleMenu::getRoleId, roleIds))
                .stream().map(WeRoleMenu::getMenuId).distinct().collect(Collectors.toList());
        // 分组查询出所有的permission
        List<List<Long>> partitions = Lists.partition(permissionList, 50);
        partitions.forEach(k -> result.addAll(
                super.list(Wrappers.lambdaQuery(WeMenu.class)
                        .in(WeMenu::getId, k)
                        .eq(WeMenu::getType, MenuEnum.MENU_TYPE.getCode())).stream().map(MWeMenuMapper.INSTANCE::toPermissionVO).collect(Collectors.toList())));
        return result;
    }

}
