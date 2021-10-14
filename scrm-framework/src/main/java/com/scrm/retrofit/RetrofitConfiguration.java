package com.scrm.retrofit;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitScan;
import com.github.lianjiatech.retrofit.spring.boot.config.RetrofitAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuKevin
 * @date 2021年10月13日 13:50
 *
 * 需要插件, 参考{@link com.scrm.retrofit.interceptor.AccessTokenInterceptor}
 * 需要重写log输出, 参考{@link com.github.lianjiatech.retrofit.spring.boot.interceptor.DefaultLoggingInterceptor}
 * 需要重写错误返回, 参考{@link com.github.lianjiatech.retrofit.spring.boot.interceptor.DefaultHttpExceptionMessageFormatter}
 * {@see https://zhuanlan.zhihu.com/p/168453299}
 */
@Configuration
@RetrofitScan("com.scrm.service.wecom")
@AutoConfigureAfter(RetrofitAutoConfiguration.class)
public class RetrofitConfiguration {

}
