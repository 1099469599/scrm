package com.scrm.controller.system;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.scrm.entity.common.BaseController;
import com.scrm.dto.user.UserInfo;
import com.scrm.entity.common.Response;
import com.scrm.entity.constants.Constant;
import com.scrm.query.system.LoginQuery;
import com.scrm.service.biz.user.WeUserService;
import com.scrm.service.common.CaptchaService;
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
        UserInfo info = weUserService.getSuperAdminByUsername(query.getUsername());
        // TODO 设置权限
        // TODO 设置允许访问的resource
        // TODO 设置数据权限掌控范围
        // sa-token 登陆
        StpUtil.login(info.getId());
        // 获取token
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        // 获取session
        SaSession session = StpUtil.getSession();
        // 设置用户信息
        session.set(Constant.SESSION_USER_KEY, info);
        return success(tokenInfo.getTokenValue());
    }


}
