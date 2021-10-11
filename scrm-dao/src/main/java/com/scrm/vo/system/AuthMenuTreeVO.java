package com.scrm.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 菜单树
 *
 * @author liuKevin
 * @date 2021年10月11日 11:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthMenuTreeVO {

    /**
     * 主键Id
     */
    private Long id;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 父菜单Id
     */
    private Long parentId;
    /**
     * 路径
     */
    private String path;
    /**
     * 路由
     */
    private String component;
    /**
     * 菜单层级
     */
    private Integer level;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否被选中
     */
    private Boolean choseFlag = false;
    /**
     * 是否可见
     */
    private Integer visible;
    /**
     * 子菜单
     */
    private List<AuthMenuTreeVO> children;

    public Boolean getChoseFlag() {
        return choseFlag != null && choseFlag;
    }
}
