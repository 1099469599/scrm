package com.scrm.service.common.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.scrm.exception.CommonException;
import com.scrm.redis.RedisCache;
import com.scrm.redis.constants.KeyConstants;
import com.scrm.service.common.CaptchaService;
import com.scrm.vo.system.CaptchaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author liuKevin
 * @date 2021年10月08日 17:51
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private RedisCache redisCache;

    /**
     * 创建数字验证码
     *
     * @return vo
     */
    @Override
    public CaptchaVO createCaptcha() {
        String uuid = IdUtil.simpleUUID();
        String redisKey = StrUtil.format(KeyConstants.CAPTCHA_KEY, uuid);
        // 创建验证码
        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(200, 50, 4, 4);
        String code = shearCaptcha.getCode();
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        shearCaptcha.write(os);
        String img = Base64.encode(os.toByteArray());
        // 缓存
        redisCache.setCacheObject(redisKey, code, KeyConstants.CAPTCHA_KEY_EXPIRE, TimeUnit.SECONDS);
        return CaptchaVO.builder().img(img).uuid(uuid).build();
    }


    /**
     * 校验验证码
     *
     * @param uuid 验证码在redis中的唯一标示
     * @param code 验证码结果
     */
    @Override
    public void checkCaptcha(String uuid, String code) {
        String redisKey = StrUtil.format(KeyConstants.CAPTCHA_KEY, uuid);
        // 缓存
        Object result = Optional.ofNullable(redisCache.getCacheObject(redisKey)).orElseThrow(() -> new CommonException("当前验证码已经失效"));
        // 验证
        if (!StrUtil.equalsAnyIgnoreCase(code, result.toString())) {
            throw new CommonException("当前验证码错误");
        }
    }
}
