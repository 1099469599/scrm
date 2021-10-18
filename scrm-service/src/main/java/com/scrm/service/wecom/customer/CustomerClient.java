package com.scrm.service.wecom.customer;

import com.github.lianjiatech.retrofit.spring.boot.annotation.Intercept;
import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.retry.Retry;
import com.scrm.entity.enums.AccessTokenEnum;
import com.scrm.retrofit.interceptor.AccessTokenInterceptor;
import com.scrm.retrofit.interceptor.annotation.AccessToken;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.Map;

/**
 * @author liuKevin
 * @date 2021年10月13日 16:15
 */
@RetrofitClient(baseUrl = "https://qyapi.weixin.qq.com/cgi-bin/")
@Intercept(handler = AccessTokenInterceptor.class)
public interface CustomerClient {

    /**
     * 获取配置了客户联系功能的成员列表
     */
    @GET(value = "externalcontact/get_follow_user_list")
    @AccessToken(type = AccessTokenEnum.CONTACT)
    @Retry(maxRetries = 4)
    String getFollowUserList();

    /**
     * 获取该用户管理的客户集合
     *
     * @param userId 用户Id
     */
    @GET(value = "externalcontact/list")
    @AccessToken(type = AccessTokenEnum.CONTACT)
    @Retry(maxRetries = 4)
    String list(@Query(value = "userid") String userId);

    /**
     * 获取客户详情
     *
     * @param externalUserId 客户Id
     */
    @GET(value = "externalcontact/get")
    @AccessToken(type = AccessTokenEnum.CONTACT)
    @Retry(maxRetries = 4)
    String get(@Query(value = "external_userid") String externalUserId);

    /**
     * 根据企业成员id批量获取客户详情
     * userid ----> 用户Id
     * cursor ----> 上一次请求的游标值, 默认为 ""
     * limit  ----> 偏移量
     *
     * @param query 请求参数
     */
    @POST(value = "batch/get_by_user")
    @AccessToken(type = AccessTokenEnum.CONTACT)
    @Retry(maxRetries = 4)
    String getByUser(@Body Map<String, Object> query);

}
