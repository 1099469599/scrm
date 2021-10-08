package com.scrm.dto.system;

import lombok.Data;

/**
 * @author liuKevin
 * @date 2021年10月08日 17:02
 */
@Data
public class LoginDTO {
    /**
     * 用户名
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String code;


}
