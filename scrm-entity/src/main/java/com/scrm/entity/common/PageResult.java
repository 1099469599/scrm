package com.scrm.entity.common;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 分页信息返回结果
 *
 * @author liuKevin
 * @date 2021年09月26日 17:33
 */
@Data
public class PageResult<VO> {

    /**
     * 当前页数
     */
    private long current;
    /**
     * 总页数
     */
    private long pages;
    /**
     * 每页条数
     */
    private long size;
    /**
     * 总条数
     */
    private long total;
    /**
     * 当前页数据
     */
    private List<VO> records;
}
