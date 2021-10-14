package com.scrm.exception;

import com.scrm.entity.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author liuKevin
 * @date 2021年10月13日 17:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeComException extends RuntimeException {

    private Integer errCode;

    private String errMsg;

    public WeComException(String message) {
        super(message);
        this.errMsg = message;
    }

    public WeComException(String message, Throwable cause){
        super(message, cause);
        this.errMsg = message;
    }

    public Integer getErrCode() {
        return Objects.isNull(errCode) ? CodeEnum.BIZ_ERROR.getCode() : errCode;
    }

}
