package com.scrm.annotation;

import java.lang.annotation.*;

/**
 * @author liuKevin
 * @date 2021年10月08日 11:42
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
}
