package com.scrm.retrofit.interceptor;

import cn.hutool.core.util.StrUtil;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import com.scrm.context.BaseContextHandler;
import com.scrm.exception.CommonException;
import com.scrm.retrofit.interceptor.service.AccessTokenService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author liuKevin
 * @date 2021年10月13日 13:52
 */
@Component
@Slf4j
public class AccessTokenInterceptor extends BasePathMatchInterceptor {

    @Autowired
    private AccessTokenService accessTokenService;

    @Override
    protected Response doIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl httpUrl = request.url();
        String corpId = BaseContextHandler.getCorpId();
        if (StrUtil.isBlank(corpId)) {
            log.error("[AccessTokenInterceptor] 获取accessToken失败, corpId获取为空, path:{}", httpUrl.encodedPath());
            throw new CommonException("当前corpId为空");
        }
        // 此处需要补全accessToken
        HttpUrl newHttp = httpUrl.newBuilder().addQueryParameter(AccessTokenService.ACCESS_TOKEN, accessTokenService.accessToken(httpUrl.encodedPath(), corpId)).build();
        Request newRequest = request.newBuilder().url(newHttp).build();
        return chain.proceed(newRequest);
    }
}
