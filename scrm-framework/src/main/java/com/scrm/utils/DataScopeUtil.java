package com.scrm.utils;

import com.scrm.annotation.DataScope;

import java.util.*;

/**
 * @author liuKevin
 * @date 2021年10月12日 20:09
 */
public class DataScopeUtil {

    protected static final ThreadLocal<Set<DataScope>> LOCAL_DATE_SCOPE = new ThreadLocal<>();

    /**
     * 开启数据权限接口注解
     *
     * @param annotation 注解
     */
    public static void startScope(DataScope annotation) {
        Set<DataScope> collect = Optional.ofNullable(LOCAL_DATE_SCOPE.get()).orElse(new HashSet<>());
        if (annotation.used()) {
            collect.add(annotation);
        }
        LOCAL_DATE_SCOPE.set(collect);
    }

    /**
     * 获取 dataScope
     */
    public static Set<DataScope> getLocalDataScope() {
        return Optional.ofNullable(LOCAL_DATE_SCOPE.get()).orElse(new HashSet<>());
    }

    /**
     * 清空
     */
    public static void close() {
        LOCAL_DATE_SCOPE.remove();
    }

}
