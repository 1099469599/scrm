package com.scrm.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author liuKevin
 * @date 2021年10月08日 13:44
 */
@Aspect
@Component
@Order(1)
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.scrm.annotation.Log)")
    public void controllerMethod() {

    }

    /**
     * 后置通知
     */
    @AfterReturning(pointcut = "controllerMethod()", returning = "result")
    public void logResultVoInfo(JoinPoint joinPoint, Object result) {
        handleLog(joinPoint, null, result);
    }

    /**
     * 异常通知
     *
     * @param joinPoint joinPoint
     * @param exception 错误类型
     */
    @AfterThrowing(value = "controllerMethod()", throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint, Exception exception) {
        handleLog(joinPoint, exception, null);
    }

    /**
     * 处理log
     *
     * @param joinPoint joinPoint
     * @param exception 错误
     * @param result    返回结果
     */
    protected void handleLog(final JoinPoint joinPoint, final Exception exception, Object result) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        // TODO
    }


}
