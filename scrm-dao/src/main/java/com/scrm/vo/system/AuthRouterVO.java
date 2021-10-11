package com.scrm.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户登陆的前端路由
 *
 * @author liuKevin
 * @date 2021年10月11日 11:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRouterVO {

    /**
     * 路由名字
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
     */
    private boolean hidden;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
     */
    private Boolean alwaysShow;

    /**
     * 设置该路由的图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 子路由
     */
    private List<AuthRouterVO> children;

}
