package com.scrm.redis.constants;

/**
 * redisKey集合,
 * 统一采用 StrUtil.format{@see cn.hutool.core.util.StrUtil#format(CharSequence, Object...)}方法做键的补全
 *
 * @author liuKevin
 * @date 2021年10月08日 14:39
 */
public class KeyConstants {

    /**
     * 验证码的缓存
     */
    public static String CAPTCHA_KEY = "S-CRM-CAPTCHA-{}";
    /**
     * 验证码存活时间
     */
    public static Integer CAPTCHA_KEY_EXPIRE = 10 * 60;
}
