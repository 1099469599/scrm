package com.scrm.service.wecom.customer;

import com.github.lianjiatech.retrofit.spring.boot.annotation.Intercept;
import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.retry.Retry;
import com.scrm.entity.enums.AccessTokenEnum;
import com.scrm.retrofit.interceptor.AccessTokenInterceptor;
import com.scrm.retrofit.interceptor.annotation.AccessToken;
import retrofit2.http.GET;

/**
 * @author liuKevin
 * @date 2021年10月13日 16:15
 */
@RetrofitClient(baseUrl = "https://qyapi.weixin.qq.com/cgi-bin/")
@Intercept(handler = AccessTokenInterceptor.class)
public interface CustomerClient {

    @GET(value = "externalcontact/list")
    @AccessToken(type = AccessTokenEnum.CONTACT)
    @Retry(maxRetries = 4)
    String list();


}
