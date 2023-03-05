package com.alibaba.dbhub.server.start.config.config;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dbhub.server.start.config.interceptor.LoginInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author moji
 * @version CORSConfiguration.java, v 0.1 2022年11月20日 19:53 moji Exp $
 * @date 2022/11/20
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//项目中的所有接口都支持跨域
            .allowedOriginPatterns("*")//所有地址都可以访问，也可以配置具体地址
            .allowCredentials(true)
            .allowedMethods("*")//"GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"
            .maxAge(3600);// 跨域允许时间
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 创建拦截器对象
        HandlerInterceptor interceptor = new LoginInterceptor();
        List<String> patterns = new ArrayList<String>();
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/register.html");
        patterns.add("/login.html");
        patterns.add("/users/reg");
        patterns.add("/users/login");
        // 通过注册工具添加拦截器
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(patterns);
    }
}
