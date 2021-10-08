package com.scrm.service.common;

import com.scrm.vo.system.CaptchaVO;

/**
 * 验证码相关群
 *
 * @author liuKevin
 * @date 2021年10月08日 17:49
 */
public interface CaptchaService {

    /**
     * 创建数字验证码
     *
     * @return vo
     */
    CaptchaVO createCaptcha();

    /**
     * 校验验证码
     *
     * @param uuid 验证码在redis中的唯一标示
     * @param code 验证码结果
     */
    void checkCaptcha(String uuid, String code);

}
