package com.scrm.entity.common;

import lombok.Data;

/**
 * 分页model
 *
 * @author liuKevin
 * @date 2021年09月26日 16:57
 */
@Data
public class PageModel {

    /**
     * 当前页数
     */
    private long currentPageIndex = 1;
    /**
     * 每页条数
     */
    private long currentPageSize = 30;
    /**
     * 排序字段
     */
    private String sort;
    /**
     * 排序类型
     * ASC
     * DESC
     */
    private String order;
}
