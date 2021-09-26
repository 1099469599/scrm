package com.scrm.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.scrm.entity.common.PageResult;
import com.scrm.entity.common.Response;

import java.util.List;

/**
 * 基础controller
 *
 * @author liuKevin
 * @date 2021年09月26日 16:46
 */
public class BaseController {

    /**
     * 转换page输出
     *
     * @param page model分页数据
     * @param list vo数据
     * @return ignore
     */
    protected <VO, T> PageResult<VO> toPage(IPage<T> page, List<VO> list) {
        PageResult<VO> pageResult = new PageResult<>();
        pageResult.setCurrent(page.getCurrent());
        pageResult.setPages(page.getPages());
        pageResult.setSize(page.getSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setRecords(list);
        return pageResult;
    }

    protected <T> Response<T> success(T obj) {
        return Response.success(obj);
    }

    protected <T> Response<T> error(String msg) {
        return Response.error(msg);
    }
}
