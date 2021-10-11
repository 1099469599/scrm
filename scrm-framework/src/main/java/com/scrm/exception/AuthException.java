package com.scrm.exception;

import com.scrm.entity.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 权限相关错误
 *
 * @author liuKevin
 * @date 2021年10月11日 14:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthException extends RuntimeException {

    private Integer errCode;

    private String errMsg;

    public AuthException(String message) {
        super(message);
        this.errMsg = message;
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
        this.errMsg = message;
    }

    public Integer getErrCode() {
        return Objects.isNull(errCode) ? CodeEnum.BIZ_ERROR.getCode() : errCode;
    }
}
