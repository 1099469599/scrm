package com.scrm.annotation;

import java.lang.annotation.*;

/**
 * @author liuKevin
 * @date 2021年10月14日 15:33
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheExpire {

    /**
     * 过期时间
     *
     * 默认是30分钟
     */
    long ttl() default 30  + 60 * 1000L;

}
