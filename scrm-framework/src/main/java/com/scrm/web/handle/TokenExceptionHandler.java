package com.scrm.web.handle;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.scrm.entity.common.Response;
import com.scrm.entity.enums.CodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 用户身份相关错误处理
 *
 * @author liuKevin
 * @date 2021年10月08日 19:50
 */
@RestControllerAdvice
@Order(-5)
@Slf4j
public class TokenExceptionHandler {


    /**
     * token相关异常
     *
     * @param notLoginException {@link NotLoginException}
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotLoginException.class)
    public Response<String> handlerNotLoginException(NotLoginException notLoginException) {
        log.error("handlerNotLoginException [{}]", notLoginException.getMessage(), notLoginException);
        // 判断场景值，定制化异常信息
        String message = "";
        switch (notLoginException.getType()) {
            case NotLoginException.NOT_TOKEN:
                message = NotLoginException.NOT_TOKEN_MESSAGE;
                break;
            case NotLoginException.INVALID_TOKEN:
                message = NotLoginException.INVALID_TOKEN_MESSAGE;
                break;
            case NotLoginException.TOKEN_TIMEOUT:
                message = NotLoginException.TOKEN_TIMEOUT_MESSAGE;
                break;
            case NotLoginException.BE_REPLACED:
                message = NotLoginException.BE_REPLACED_MESSAGE;
                break;
            case NotLoginException.KICK_OUT:
                message = NotLoginException.KICK_OUT_MESSAGE;
                break;
            default:
                message = NotLoginException.DEFAULT_MESSAGE;
                break;
        }
        return Response.error(CodeEnum.TOKEN_ERROR, message);
    }

    /**
     * 没有角色
     *
     * @param exception {@link NotRoleException}
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotRoleException.class)
    public Response<String> handlerNotRoleException(NotRoleException exception) {
        log.error("handlerNotRoleException [{}]", exception.getMessage(), exception);
        return Response.error(CodeEnum.USER_NO_ROLE_LOCKED_ERROR, exception.getMessage());
    }

    /**
     * 没有权限
     *
     * @param exception {@link NotPermissionException}
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotPermissionException.class)
    public Response<String> handlerNotPermissionException(NotPermissionException exception) {
        log.error("handlerNotPermissionException [{}]", exception.toString());
        return Response.error(CodeEnum.USER_NO_PERMISSION_LOCKED_ERROR, exception.getMessage());
    }


}
