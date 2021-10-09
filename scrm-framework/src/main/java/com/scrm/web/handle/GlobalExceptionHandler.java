package com.scrm.web.handle;

import com.scrm.entity.common.Response;
import com.scrm.entity.enums.CodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局最后的异常拦截器
 *
 * @author liuKevin
 * @date 2021年10月08日 19:58
 */
@RestControllerAdvice
@Order(0)
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 所有异常信息
     *
     * @param exception {@link Exception}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public Response<String> handlerException(Exception exception) {
        log.error("exceptionHandle [{}]", exception.getMessage(), exception);
        return Response.error(CodeEnum.UNKNOWN_ERROR, exception.getMessage());
    }

}
