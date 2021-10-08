package com.scrm.web.handle;

import com.scrm.entity.common.Response;
import com.scrm.entity.enums.CodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * 校验参数异常处理
 *
 * @author liuKevin
 * @date 2021年10月08日 19:35
 */
@RestControllerAdvice
@Order(-10)
@Slf4j
public class CheckExceptionHandler {


    /**
     * 参数未填写 @RequestParam
     *
     * @param missingServletRequestParameterException {@link MissingServletRequestParameterException}
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Response<String> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException missingServletRequestParameterException) {
        log.error("missingServletRequestParameterException [{}]", missingServletRequestParameterException.getMessage(), missingServletRequestParameterException);
        return Response.error(CodeEnum.CHECK_ERROR, missingServletRequestParameterException.getMessage());
    }

    /**
     * 校验异常
     *
     * @param bindException {@link BindException}
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public Response<String> bindExceptionExceptionHandler(BindException bindException) {
        log.error("bindException  errorSize:[{}]", bindException.getErrorCount(), bindException);
        if (bindException.hasErrors()) {
            for (ObjectError error : bindException.getAllErrors()) {
                return Response.error(CodeEnum.CHECK_ERROR, error.getDefaultMessage());
            }
        }
        return Response.error(CodeEnum.CHECK_ERROR, bindException.getMessage());
    }

    /**
     * 校验异常
     *
     * @param exception {@link MethodArgumentNotValidException}
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<String> MethodArgumentNotValidExceptionHandle(MethodArgumentNotValidException exception) {
        log.error("MethodArgumentNotValidException: [{}] ", exception.getMessage(), exception);
        return Response.error(CodeEnum.CHECK_ERROR, exception.getMessage());
    }


    /**
     * 基本数据类型，验证错误
     *
     * @param exception {@link ValidationException}
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ValidationException.class)
    public Response<String> validationExceptionHandler(ValidationException exception) {
        log.error("validationExceptionHandle [{}]", exception.getMessage(), exception);
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                return Response.error(CodeEnum.CHECK_ERROR, item.getMessage());
            }
        }
        return Response.error(CodeEnum.CHECK_ERROR, exception.getMessage());
    }

}
