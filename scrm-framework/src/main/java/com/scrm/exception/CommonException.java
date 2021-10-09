package com.scrm.exception;

import com.scrm.entity.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 通用逻辑的错误
 *
 * @author liuKevin
 * @date 2021年10月08日 19:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonException extends RuntimeException {

    private Integer errCode;

    private String errMsg;

    public CommonException(String message) {
        super(message);
        this.errMsg = message;
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
        this.errMsg = message;
    }

    public Integer getErrCode() {
        return Objects.isNull(errCode) ? CodeEnum.BIZ_ERROR.getCode() : errCode;
    }
}
