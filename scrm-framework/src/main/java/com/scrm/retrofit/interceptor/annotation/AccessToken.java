package com.scrm.retrofit.interceptor.annotation;

import com.github.lianjiatech.retrofit.spring.boot.annotation.InterceptMark;
import com.scrm.entity.enums.AccessTokenEnum;

import java.lang.annotation.*;

/**
 * @author liuKevin
 * @date 2021年10月13日 16:17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@InterceptMark
public @interface AccessToken {

    AccessTokenEnum type() default AccessTokenEnum.NO_ACCESS;

}
