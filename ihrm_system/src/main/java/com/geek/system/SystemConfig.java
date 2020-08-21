package com.geek.system;

import com.geek.common.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class SystemConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
//        super.addInterceptors(registry);
        // 添加自定义拦截器。
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")// 指定拦截器的 url 地址。
                .excludePathPatterns("/sys/login", "/frame/register");// 指定不拦截的 url 地址。
    }
}
