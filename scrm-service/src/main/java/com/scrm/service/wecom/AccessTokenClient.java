package com.scrm.service.wecom;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.scrm.dto.wecom.WeAccessTokenDTO;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author liuKevin
 * @date 2021年10月13日 15:22
 */
@RetrofitClient(baseUrl = "https://qyapi.weixin.qq.com/cgi-bin/")
public interface AccessTokenClient {

    @GET(value = "gettoken")
    WeAccessTokenDTO getAccessToken(@Query("corpid") String corpId, @Query("corpsecret") String corpSecret);


}
