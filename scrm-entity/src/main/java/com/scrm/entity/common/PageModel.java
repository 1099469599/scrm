package com.scrm.entity.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页model
 *
 * @author liuKevin
 * @date 2021年09月26日 16:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageModel {

    /**
     * 当前页数
     */
    @Builder.Default
    private long currentPageIndex = 1;
    /**
     * 每页条数
     */
    @Builder.Default
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
