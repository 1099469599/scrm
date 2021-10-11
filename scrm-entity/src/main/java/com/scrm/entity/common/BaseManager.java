package com.scrm.entity.common;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.function.Function;

/**
 * @author liuKevin
 * @description 数据层的基础
 * @date 2021年09月26日 16:46
 */
@SuppressWarnings({"unchecked"})
public class BaseManager<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    private static final String ORDER_ASC = "asc";

    /**
     * 分页查询
     *
     * @param pageModel    pageModel
     * @param queryWrapper query
     * @return 分页结果
     */
    protected <T1> IPage<T1> page(PageModel pageModel, Wrapper<T1> queryWrapper) {
        IPage<T> page = getPageInfo(pageModel);
        return (IPage<T1>) page(page, (Wrapper<T>) queryWrapper);
    }

    /**
     * 分页查询
     *
     * @param pageModel    pageModel
     * @param queryWrapper query
     * @param mapper       转化函数
     * @return 分页结果
     */
    protected <R> IPage<R> page(PageModel pageModel, Wrapper<T> queryWrapper, Function<? super T, R> mapper) {
        IPage<T> page = getPageInfo(pageModel);
        IPage<T> result = page(page, queryWrapper);
        return result.convert(mapper);
    }


    protected IPage<T> getPageInfo(PageModel pageModel) {
        Page<T> page = new Page<>();
        page.setCurrent(pageModel.getCurrentPageIndex());
        page.setSize(pageModel.getCurrentPageSize());
        if (StrUtil.isNotBlank(pageModel.getSort()) && StrUtil.isNotBlank(pageModel.getOrder())) {
            OrderItem orderItem = new OrderItem();
            // 驼峰转换下划线
            orderItem.setColumn(StrUtil.toUnderlineCase(pageModel.getSort()));
            orderItem.setAsc(ORDER_ASC.equalsIgnoreCase(pageModel.getOrder()));
            // 添加排序字段
            page.addOrder(orderItem);
        }
        return page;
    }


}
