package com.scrm.controller.system;

import com.scrm.base.BaseController;
import com.scrm.dto.system.LoginDTO;
import com.scrm.entity.common.Response;
import com.scrm.query.system.LoginQuery;
import com.scrm.service.biz.user.WeUserService;
import com.scrm.service.common.CaptchaService;
import com.scrm.transform.user.UserInfoMapper;
import com.scrm.vo.system.CaptchaVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuKevin
 * @date 2021年10月08日 16:51
 */
@RestController
@Slf4j
public class LoginController extends BaseController {

    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private WeUserService weUserService;


    /**
     * 获取验证码
     *
     * @return 验证码信息
     */
    @GetMapping("/s-crm/b/captcha")
    public Response<CaptchaVO> getCaptcha() {
        CaptchaVO captcha = captchaService.createCaptcha();
        return success(captcha);
    }

    /**
     * PC账号、密码登录
     * 因为存在多租户概念, 账密登陆仅支持超管登陆
     *
     * @param query 登录信息
     * @return token
     */
    @PostMapping("/s-crm/m/login")
    public Response<String> login(@Validated @RequestBody LoginQuery query) {
        // 校验验证码
        captchaService.checkCaptcha(query.getUuid(), query.getCode());
        // 获取超管身份信息
        weUserService.getSuperAdminByUsername(query.getUsername());
        String token = "";
        //
        //
        return success(token);
    }


}
