package com.scrm.service.common.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.scrm.dto.system.AuthRoleDTO;
import com.scrm.entity.common.PageModel;
import com.scrm.entity.enums.RoleEnum;
import com.scrm.entity.pojo.system.WeRole;
import com.scrm.entity.pojo.system.WeUserRole;
import com.scrm.exception.AuthException;
import com.scrm.manager.system.WeRoleManager;
import com.scrm.service.common.WeMenuService;
import com.scrm.service.common.WeRoleService;
import com.scrm.service.common.WeUserRoleService;
import com.scrm.transform.system.MWeRoleMapper;
import com.scrm.vo.system.AuthMenuTreeVO;
import com.scrm.vo.system.AuthRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 角色信息表Service业务层处理
 *
 * @author S-CRM
 * @date 2021-10-11 11:12:16
 */
@Service
public class WeRoleServiceImpl extends WeRoleManager implements WeRoleService {

    @Autowired
    private WeMenuService weMenuService;
    @Autowired
    private WeUserRoleService weUserRoleService;

    /**
     * 获取角色列表
     *
     * @param dto      角色查询条件
     * @param corpId   企业唯一标示
     * @param pageNum  当前页数
     * @param pageSize 每页条数
     * @return 分页结果
     */
    @Override
    public IPage<WeRole> getList(AuthRoleDTO dto, String corpId, Integer pageNum, Integer pageSize) {
        //
        PageModel pageModel = PageModel.builder().currentPageIndex(pageNum).currentPageSize(pageSize).build();
        //
        LambdaQueryWrapper<WeRole> wrappers = Wrappers.lambdaQuery(WeRole.class)
                .eq(StrUtil.isNotBlank(corpId), WeRole::getCorpId, corpId)
                .like(StrUtil.isNotBlank(dto.getRoleName()), WeRole::getRoleName, dto.getRoleName())
                .ge(ObjectUtil.isNotNull(dto.getStartDate()), WeRole::getCreateTime, dto.getStartDate())
                .le(ObjectUtil.isNotNull(dto.getStartDate()), WeRole::getCreateTime, dto.getEndDate())
                .eq(ObjectUtil.isNotNull(dto.getVisible()), WeRole::getVisible, dto.getVisible());
        //
        return super.page(pageModel, wrappers);
    }

    /**
     * 查询所有的角色
     *
     * @param corpId 企业唯一标示
     * @return 全部角色
     */
    @Override
    public List<WeRole> all(String corpId) {
        //
        LambdaQueryWrapper<WeRole> wrappers = Wrappers.lambdaQuery(WeRole.class)
                .eq(StrUtil.isNotBlank(corpId), WeRole::getCorpId, corpId)
                .eq(WeRole::getVisible, RoleEnum.ROLE_VISIBLE_TRUE.getCode());
        //
        return list(wrappers);
    }

    /**
     * 查询角色详情
     *
     * @param corpId 企业唯一标示
     * @param roleId 角色Id
     * @param userId 用户Id(=we_user.id)
     * @return 角色详情
     */
    @Override
    public AuthRoleVO getRole(String corpId, Long roleId, Long userId) {
        // 获取基础信息
        AuthRoleVO authRoleVO = MWeRoleMapper.INSTANCE.toVO(Optional.ofNullable(super.getById(roleId)).orElseThrow(() -> new AuthException("当前角色不存在")));
        // 获取当前用户的菜单上限
        List<AuthMenuTreeVO> userMenuList = weMenuService.selectMenuList(corpId, userId);
        // 获取当前角色的绑定菜单
        List<AuthMenuTreeVO> roleMenuList = weMenuService.selectMenuListByRoleId(corpId, roleId);
        // 将当前角色绑定的菜单选中
        userMenuList.forEach(k -> {
            if (roleMenuList.stream().anyMatch(m -> m.getId().equals(k.getId()))) {
                k.setChoseFlag(true);
            }
        });
        // 菜单List组成菜单tree
        authRoleVO.setMenuList(weMenuService.buildTree(userMenuList).getChildren());
        // 凡是存在下级页面的菜单, 全部取消choseFlag, 只保留最底层的菜单的choseFlag状态
        removeChoseFlag(authRoleVO.getMenuList());
        return authRoleVO;
    }

    /**
     * 获取某个员工的绑定角色
     *
     * @param corpId 企业唯一标示
     * @param userId 用户Id(=we_user.id)
     * @return 角色集合
     */
    @Override
    public List<WeRole> getRole(String corpId, Long userId) {
        // 查询所有关联的角色Id
        List<Long> collect = weUserRoleService.list(Wrappers.lambdaQuery(WeUserRole.class)
                .select(WeUserRole::getRoleId)
                .eq(StrUtil.isNotBlank(corpId), WeUserRole::getCorpId, corpId)
                .eq(ObjectUtil.isNotNull(userId), WeUserRole::getUserId, userId)).stream().filter(Objects::nonNull).map(WeUserRole::getRoleId).collect(Collectors.toList());
        // 如果集合为空, 直接返回
        if (CollectionUtil.isEmpty(collect)) {
            return new ArrayList<>();
        }
        // 返回数据
        return super.listByIds(collect);
    }
}
