package com.scrm.service.wecom._impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitFactoryBean;
import com.scrm.entity.pojo.company.WeCorpAccount;
import com.scrm.exception.CommonException;
import com.scrm.retrofit.interceptor.annotation.AccessToken;
import com.scrm.retrofit.interceptor.service.AccessTokenService;
import com.scrm.service.biz.company.WeCorpAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author liuKevin
 * @date 2021年10月13日 16:21
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    private WeCorpAccountService weCorpAccountService;

    private final Map<String, AccessToken> annotationMap = new HashMap<>();

    @Autowired
    public AccessTokenServiceImpl(List<RetrofitFactoryBean> beans) {
        beans.forEach(k -> {
            Class klass = k.getObjectType();
            Assert.notNull(klass, "RetrofitFactoryBean 获取 getObjectType 失败, bean : " + k.getClass().getName());
            // client的注解
            RetrofitClient annotation = (RetrofitClient) klass.getAnnotation(RetrofitClient.class);
            Assert.notNull(annotation, "RetrofitFactoryBean 获取g RetrofitClient 失败, bean : " + klass.getName());
            // 遍历每个方法的注解
            for (Method method : klass.getMethods()) {
                // 处理get注解
                Optional.ofNullable(method.getAnnotation(GET.class)).ifPresent(get -> {
                    AccessToken token = method.getAnnotation(AccessToken.class);
                    if (Objects.nonNull(token)) {
                        annotationMap.put(annotation.baseUrl() + get.value(), token);
                    }
                });
                // 处理post注解
                Optional.ofNullable(method.getAnnotation(POST.class)).ifPresent(post -> {
                    AccessToken token = method.getAnnotation(AccessToken.class);
                    if (Objects.nonNull(token)) {
                        annotationMap.put(annotation.baseUrl() + post.value(), token);
                    }
                });
            }
        });

    }

    /**
     * 获取当前企业的accessToken
     *
     * @param url    请求的url
     * @param corpId 企业唯一标识
     * @return accessToken
     */
    @Override
    @Cacheable(cacheNames = "accessToken", key = "#corpId")
    public String accessToken(String url, String corpId) {
        AccessToken accessToken = annotationMap.get(url);
        if (Objects.isNull(accessToken)) {
            return "";
        }
        return getAccessTokenByAnnotation(accessToken, corpId);
    }


    public String getAccessTokenByAnnotation(AccessToken annotation, String corpId) {
        WeCorpAccount one = Optional.ofNullable(weCorpAccountService.getOne(Wrappers.lambdaQuery(WeCorpAccount.class)
                .eq(WeCorpAccount::getCorpId, corpId)
                .last("limit 1"))).orElseThrow(() -> new CommonException("当前企业不存在"));
        // TODO
        switch (annotation.type()) {
            case SUITE_APP:
                return "";

            case COMMON:
                return "";

            case CONTACT:
                return "";
            case NO_ACCESS:
                return "";
            default:
                return "";
        }
    }

}
