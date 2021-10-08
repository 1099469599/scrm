package com.scrm.web.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.URLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author liuKevin
 * @date 2021年10月08日 14:35
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

//    @Autowired
//    private ResourcesService resourcesService;

    @Value("${ignored:'/**'}")
    private List<String> ignored;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // token拦截器 获取用户信息
        registry.addInterceptor(new SaRouteInterceptor((request, response, handler) -> {
            String url = URLUtil.getPath(request.getRequestPath());
            // 所有的URL请求都需要优先判断当前用户是否登陆
            SaRouter.match("/**", StpUtil::checkLogin);
            // 获取所有的路由表
//            List<Resources> resourcesList = resourcesService.getRouterList();
//            for (Resources resources : resourcesList) {
//                // 如果URL匹配成功就不循环了，直接退出循环
//                // 如果匹配不成功就匹配 /** URL路径了
//                if (antPathMatcher.match(resources.getResPath(), url) && request.getMethod().equalsIgnoreCase(resources.getHttpMethod())) {
//                    SaRouter.match(resources.getResPath(), () -> StpUtil.checkPermission(resources.getResCode()));
//                    break;
//                }
//            }
        })).addPathPatterns("/**").excludePathPatterns(ignored);
    }

}
