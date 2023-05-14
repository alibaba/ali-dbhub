/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.start.config.config;

import com.alibaba.dbhub.server.domain.support.util.JdbcJarUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author jipengfei
 * @version : JarDownloadTask.java
 */
@Component
public class JarDownloadTask implements CommandLineRunner {

    @Value("${jdbc.jar.download.url}")
    private String jdbcJarDownLoadUrl;

    @Override
    public void run(String... args) throws Exception {
        String[] urls = jdbcJarDownLoadUrl.split(",");
        if (urls != null && urls.length >= 1) {
            JdbcJarUtils.asyncDownload(urls);
        }
    }
}