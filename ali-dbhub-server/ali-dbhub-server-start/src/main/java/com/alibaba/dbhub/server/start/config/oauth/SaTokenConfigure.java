package com.alibaba.dbhub.server.start.config.oauth;

import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.stp.StpLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * satoken配置
 *
 * @author 是仪
 */
@Configuration
public class SaTokenConfigure {
    @Bean
    public StpLogic ttpLogic() {
    // Log in to display the state without a state without relying on redis 
         // After that, can you change to EHCAHE storage disk?
        return new StpLogicJwtForStateless();
    }
}