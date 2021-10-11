package com.scrm.service.common;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scrm.entity.pojo.system.WeMenu;
import com.scrm.vo.system.AuthMenuTreeVO;
import com.scrm.vo.system.AuthPermissionVO;
import com.scrm.vo.system.AuthRouterVO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单权限表Service接口
 *
 * @author S-CRM
 * @date 2021-10-11 11:17:21
 */
public interface WeMenuService extends IService<WeMenu> {

    /**
     * 用户登陆时, 根据用户Id找到整个路由
     *
     * @param corpId 企业唯一标示
     * @param userId 用户Id(=we_user.user_id)
     * @return 页面路由
     */
    List<AuthRouterVO> getRout(String corpId, Long userId);

    /**
     * 查询所有的菜单列表(超管视角下)
     *
     * @return 菜单集合
     */
    List<AuthMenuTreeVO> selectAllMenu();

    /**
     * 根据用户Id查询当前用户可以看到的菜单集合(反推父菜单)
     *
     * @param corpId 企业唯一标示
     * @param userId 用户Id(=we_user.user_id)
     * @return 菜单列表
     */
    List<AuthMenuTreeVO> selectMenuList(String corpId, Long userId);

    /**
     * 查询所有的接口集合(超管视角下)
     *
     * @return 接口列表
     */
    List<AuthPermissionVO> selectAllPermission();

    /**
     * 根据用户Id查询当前用户可以看到的接口集合
     *
     * @param corpId 企业唯一标示
     * @param userId 用户Id(=we_user.id)
     * @return 接口列表
     */
    List<AuthPermissionVO> selectPermissionList(String corpId, Long userId);

    /**
     * 根据角色Id查询当前角色绑定的菜单集合(不受用户权限限制)
     *
     * @param corpId 企业唯一Id
     * @param roleId 角色Id
     * @return 菜单集合
     */
    List<AuthMenuTreeVO> selectMenuListByRoleId(String corpId, Long roleId);

    /**
     * 根据角色Id查询当前角色绑定的接口集合(不受用户权限限制)
     *
     * @param corpId 企业唯一标示
     * @param roleId 角色Id
     * @return 接口权限集合
     */
    List<AuthPermissionVO> selectPermissionByRoleId(String corpId, Long roleId);


    /**
     * 根据角色Id查询当前角色绑定的权限集合(受当前用户权限限制)
     *
     * @param corpId 企业唯一标示
     * @param userId 用户Id
     * @param roleId 角色Id
     * @return 菜单集合
     */
    List<AuthPermissionVO> selectPermissionByRoleId(String corpId, Long userId, Long roleId);

    /**
     * 将菜单列表根据parent组合成为tree
     *
     * @param voList 菜单列表
     * @return 菜单树
     */
    default AuthMenuTreeVO buildTree(List<AuthMenuTreeVO> voList) {
        // 拿到树顶层Id
        Long rootId = voList.stream().map(AuthMenuTreeVO::getParentId).min(Long::compareTo).orElse(0L);
        // 刷入父子关系
        voList.forEach(k -> {
            List<AuthMenuTreeVO> collect = voList.stream().filter(m -> m.getParentId().equals(k.getId())).sorted(Comparator.comparing(AuthMenuTreeVO::getSort)).collect(Collectors.toList());
            k.setChildren(collect);
        });
        // 过滤得到root数据
        List<AuthMenuTreeVO> collect = voList.stream().filter(k -> rootId.equals(k.getParentId())).sorted(Comparator.comparing(AuthMenuTreeVO::getSort)).collect(Collectors.toList());
        AuthMenuTreeVO vo = new AuthMenuTreeVO();
        vo.setChildren(collect);
        return vo;
    }


    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    default List<AuthMenuTreeVO> getChildPerms(List<AuthMenuTreeVO> list, int parentId) {
        List<AuthMenuTreeVO> returnList = new ArrayList<>();
        for (AuthMenuTreeVO t : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t, 0);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list 总结点
     * @param t    单位节点
     */
    default void recursionFn(List<AuthMenuTreeVO> list, AuthMenuTreeVO t, Integer level) {
        // 得到子节点列表
        List<AuthMenuTreeVO> childList = list.stream().filter(k -> k.getParentId().equals(t.getId())).collect(Collectors.toList());
        t.setChildren(childList);
        t.setLevel(level);
        for (AuthMenuTreeVO tChild : childList) {
            if (list.stream().anyMatch(k -> k.getParentId().equals(tChild.getId()))) {
                // 判断是否有子节点
                for (AuthMenuTreeVO n : childList) {
                    recursionFn(list, n, level + 1);
                }
            }
        }
    }

    /**
     * 菜单构建出登陆时需要的路由
     *
     * @param menus 菜单集合
     * @return 路由
     */
    default List<AuthRouterVO> buildRoute(List<AuthMenuTreeVO> menus) {
        List<AuthRouterVO> routers = new LinkedList<>();
        for (AuthMenuTreeVO menu : menus) {
            AuthRouterVO router = new AuthRouterVO();
            router.setName(StrUtil.upperFirst(menu.getPath()));
            router.setSort(Optional.ofNullable(menu.getSort()).orElse(10));
            router.setHidden(Optional.ofNullable(menu.getVisible()).orElse(0) == 1 || StrUtil.isEmpty(menu.getPath()));
            List<AuthMenuTreeVO> cMenus = menu.getChildren();
            if (menu.getLevel() == 0) {
                // 最外层
                router.setComponent("Layout");
                router.setPath("/" + Optional.ofNullable(menu.getPath()).orElse(IdUtil.fastSimpleUUID()));
                router.setAlwaysShow(true);
                router.setChildren(buildRoute(cMenus));
            } else if (menu.getLevel() == 1 && CollectionUtil.isNotEmpty(cMenus)) {
                // 第二层的菜单
                router.setComponent("Room");
                router.setPath(Optional.ofNullable(menu.getPath()).orElse(IdUtil.fastSimpleUUID()));
                router.setAlwaysShow(true);
                router.setChildren(buildRoute(cMenus));
            } else {
                List<AuthMenuTreeVO> list = Optional.ofNullable(menu.getChildren()).orElse(new ArrayList<>());
                list.forEach(k -> {
                    AuthRouterVO children = new AuthRouterVO();
                    children.setPath(Optional.ofNullable(k.getPath()).orElse(IdUtil.fastSimpleUUID()));
                    children.setComponent(Optional.ofNullable(k.getComponent()).orElse(""));
                    children.setName(StrUtil.upperFirst(k.getPath()));
                    children.setHidden(true);
                    children.setSort(k.getSort());
                    routers.add(children);
                });
                router.setPath(Optional.ofNullable(menu.getPath()).orElse(IdUtil.fastSimpleUUID()));
                router.setComponent(Optional.ofNullable(menu.getComponent()).orElse(" "));
            }
            routers.add(router);
        }
        return routers.stream().sorted(Comparator.comparing(AuthRouterVO::getSort)).collect(Collectors.toList());
    }


}
