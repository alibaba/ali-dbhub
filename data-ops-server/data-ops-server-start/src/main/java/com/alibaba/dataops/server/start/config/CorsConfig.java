package com.alibaba.dataops.server.start.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author moji
 * @version CORSConfiguration.java, v 0.1 2022年11月20日 19:53 moji Exp $
 * @date 2022/11/20
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//项目中的所有接口都支持跨域
            .allowedOriginPatterns("*")//所有地址都可以访问，也可以配置具体地址
            .allowCredentials(true)
            .allowedMethods("*")//"GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"
            .maxAge(3600);// 跨域允许时间
    }
}
