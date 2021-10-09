package com.scrm.aspect;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.scrm.context.BaseContextHandler;
import com.scrm.dto.user.UserInfo;
import com.scrm.entity.constants.Constant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 获取session中的用户信息
 */
@Aspect
@Slf4j
@Component
@Order(-200)
public class BaseContextAspect {

    /**
     * 切入点
     */
    @Pointcut("execution(public * com.scrm.*.controller..*(..)) ")
    public void controllerMethod() {
    }

    /**
     * 前置通知
     *
     * @param joinPoint joinPoint
     * @since set相关信息
     */
    @Before("controllerMethod()")
    public void logRequestInfo(JoinPoint joinPoint) {
        // 设置用户信息
        if (StpUtil.isLogin()) {
            SaSession session = StpUtil.getSession(false);
            UserInfo userinfo = session.getModel(Constant.SESSION_USER_KEY, UserInfo.class);
            BaseContextHandler.setId(userinfo.getId());
            BaseContextHandler.setUserID(userinfo.getUserId());
            BaseContextHandler.setUsername(userinfo.getUserName());
        }
    }

    /**
     * 后置通知
     */
    @AfterReturning(returning = "rvt", pointcut = "controllerMethod()")
    public void logResultVoInfo(Object rvt) throws Exception {
        BaseContextHandler.remove();
    }

    /**
     * 异常通知
     *
     * @param joinPoint joinPoint
     * @param exception exception
     */
    @AfterThrowing(value = "controllerMethod()", throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
        BaseContextHandler.remove();
    }

}
