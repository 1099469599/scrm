package com.scrm.entity.common;

import com.scrm.entity.enums.CodeEnum;
import lombok.Data;

/**
 * 返回数据的统一包装
 *
 * @author liuKevin
 * @date 2021年09月26日 17:42
 */
@Data
public class Response<T> {

    /**
     * 响应码, 成功是1，失败是0
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 成功
     */
    public static <T> Response<T> success(T data) {
        Integer code;
        String msg;
        if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
            code = CodeEnum.ERROR.getCode();
            msg = CodeEnum.ERROR.getMsg();
        } else {
            code = CodeEnum.SUCCESS.getCode();
            msg = CodeEnum.SUCCESS.getMsg();
        }
        return result(code, msg, data);
    }

    /**
     * 失败
     */
    public static <T> Response<T> error(String msg) {
        return result(CodeEnum.ERROR.getCode(), msg, null);
    }


    private static <T> Response<T> result(Integer code, String msg, T data) {
        Response<T> result = new Response<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
