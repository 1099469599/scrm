package com.scrm.db.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.scrm.db.interceptor.DataPermissionInterceptor;
import com.scrm.db.interceptor.DeleteInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus 配置类
 *
 * @author liuKevin
 * @date 2021年10月08日 14:22
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 数据权限拦截器
        mybatisPlusInterceptor.addInnerInterceptor(new DataPermissionInterceptor());
        // 分页拦截器
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // 删除时添加删除时间戳
        mybatisPlusInterceptor.addInnerInterceptor(new DeleteInterceptor());
        // 攻击 SQL 阻断解析器,防止全表更新与删除
        mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return mybatisPlusInterceptor;
    }


    /**
     * 下划线转骆峰
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        // 函数式编程
        return (configuration) -> {
            // 使用mybatis-plus 内置的
            configuration.setObjectWrapperFactory(new MybatisMapWrapperFactory());
        };
    }

}
