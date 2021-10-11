package com.scrm.service.common;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scrm.dto.system.AuthRoleDTO;
import com.scrm.entity.pojo.system.WeRole;
import com.scrm.vo.system.AuthMenuTreeVO;
import com.scrm.vo.system.AuthRoleVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表Service接口
 *
 * @author S-CRM
 * @date 2021-10-11 11:12:16
 */
public interface WeRoleService extends IService<WeRole> {

    /**
     * 获取角色列表
     *
     * @param dto      角色查询条件
     * @param corpId   企业唯一标示
     * @param pageNum  当前页数
     * @param pageSize 每页条数
     * @return 分页结果
     */
    IPage<WeRole> getList(AuthRoleDTO dto, String corpId, Integer pageNum, Integer pageSize);

    /**
     * 查询所有的角色
     *
     * @param corpId 企业唯一标示
     * @return 全部角色
     */
    List<WeRole> all(String corpId);

    /**
     * 查询角色详情
     *
     * @param corpId 企业唯一标示
     * @param roleId 角色Id
     * @param userId 用户Id(=we_user.id)
     * @return 角色详情
     */
    AuthRoleVO getRole(String corpId, Long roleId, Long userId);

    /**
     * 获取某个员工的绑定角色
     *
     * @param corpId 企业唯一标示
     * @param userId 用户Id(=we_user.id)
     * @return 角色集合
     */
    List<WeRole> getRole(String corpId, Long userId);



    /**
     * 凡是存在下级页面的菜单, 全部取消choseFlag, 只保留最底层的菜单的choseFlag状态
     *
     * @param menuList 角色详情
     */
    default void removeChoseFlag(List<AuthMenuTreeVO> menuList) {
        menuList.forEach(k -> {
            // 一级菜单取消
            List<AuthMenuTreeVO> children = k.getChildren();
            if (CollectionUtil.isNotEmpty(children)) {
                k.setChoseFlag(false);
                removeChoseFlag(children);
            }
        });
    }

    /**
     * 补全基础菜单的上级菜单
     *
     * @param baseMenuIds 基础菜单Id
     * @return 补全后的菜单Id集合
     */
    default List<Long> complementMenuId(List<Long> baseMenuIds, List<AuthMenuTreeVO> authMenuList) {
        List<Long> result = new ArrayList<>();
        baseMenuIds.forEach(k -> {
            AuthMenuTreeVO menu = authMenuList.stream().filter(item -> k.equals(item.getId())).findFirst().orElse(null);
            if (ObjectUtil.isNotNull(menu) && ObjectUtil.isNotNull(menu.getParentId()) && menu.getParentId() > 0) {
                result.addAll(complementMenuId(CollectionUtil.newArrayList(menu.getParentId()), authMenuList));
            }
            result.add(k);
        });
        return result;
    }
}
