package com.scrm.retrofit.interceptor.service;

/**
 * @author liuKevin
 * @date 2021年10月13日 15:12
 */
public interface AccessTokenService {

    String ACCESS_TOKEN = "access_token";

    /**
     * 获取当前企业的accessToken
     *
     * @param url    请求的url
     * @param corpId 企业唯一标识
     * @return accessToken
     */
    String accessToken(String url, String corpId);

}
