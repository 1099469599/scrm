package com.scrm.web.handle;

import com.scrm.entity.common.Response;
import com.scrm.entity.enums.CodeEnum;
import com.scrm.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 业务处理错误
 *
 * @author liuKevin
 * @date 2021年10月08日 20:32
 */
@RestControllerAdvice
@Order(-3)
@Slf4j
public class FrameworkExceptionHandle {

    /**
     * 业务异常中的common异常
     *
     * @param exception {@link CommonException}
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(CommonException.class)
    public Response<String> handleCommonException(CommonException exception) {
        log.error("commonException: [{}]", exception.getErrMsg(), exception);
        return Response.error(exception.getErrCode(), exception.getErrMsg(), exception.getMessage());
    }


}
