package com.scrm.aspect;

import com.scrm.annotation.DataScope;
import com.scrm.utils.DataScopeUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author liuKevin
 * @date 2021年10月12日 20:33
 */
@Aspect
@Component
@Order(-1)
@Slf4j
public class DataScopeAspect {

    @Pointcut("@annotation(com.scrm.annotation.DataScope)")
    public void pointCut() {

    }


    @Around(value = "pointCut()")
    public Object handleMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取注解
        DataScope annotation = getAnnotation(joinPoint);
        if (Objects.nonNull(annotation) && annotation.used()) {
            DataScopeUtil.startScope(annotation);
        }
        // 执行逻辑
        return joinPoint.proceed();
    }

    @AfterReturning(pointcut = "pointCut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        DataScopeUtil.close();
    }


    /**
     * 异常通知
     *
     * @param joinPoint joinPoint
     * @param exception 错误类型
     */
    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint, Exception exception) {
        DataScopeUtil.close();
    }


    /**
     * 是否存在注解，如果存在就获取
     */
    private DataScope getAnnotation(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(DataScope.class);
        }
        return null;
    }

}
