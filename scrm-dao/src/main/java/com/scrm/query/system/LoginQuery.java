package com.scrm.query.system;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户名密码登陆
 *
 * @author liuKevin
 * @date 2021年10月08日 16:54
 */
@Data
@Builder
public class LoginQuery {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String code;

    /**
     * 唯一标识
     */
    @NotBlank(message = "验证码标示不能为空")
    private String uuid ;

}
