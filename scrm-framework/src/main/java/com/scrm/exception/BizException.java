package com.scrm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 业务错误
 *
 * @author liuKevin
 * @date 2021年10月08日 19:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BizException extends RuntimeException {

    private Integer errCode;

    private String errMsg;

    public BizException(String message) {
        super(message);
        this.errMsg = message;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.errMsg = message;
    }
}
