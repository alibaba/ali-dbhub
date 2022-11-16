package com.alibaba.dataops.server.tools.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author moji
 * @version SystemProperties.java, v 0.1 2022年11月13日 14:28 moji Exp $
 * @date 2022/11/13
 */
@Configuration
@ConfigurationProperties(prefix = "data.ops")
@Data
public class DataOpsProperties {

    /**
     * 版本
     */
    private String version;
}
