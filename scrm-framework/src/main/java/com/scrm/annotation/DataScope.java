package com.scrm.annotation;

import java.lang.annotation.*;

/**
 * @author liuKevin
 * @date 2021年10月11日 10:03
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 别名
     * 多表关联查询时的表别名
     */
    String alias() default "";

    /**
     * 表内权限字段名
     * 默认是创建人字段
     */
    String name() default "create_by";

    /**
     * 是否生效
     * 默认是生效的
     */
    boolean used() default true;

}
