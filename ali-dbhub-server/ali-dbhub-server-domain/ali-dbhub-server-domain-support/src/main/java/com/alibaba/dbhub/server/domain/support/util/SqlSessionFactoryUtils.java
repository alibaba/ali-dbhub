/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.util;

import javax.sql.DataSource;

import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @author jipengfei
 * @version : SqlSessionFactoryUtils.java, v 0.1 2022年12月13日 17:27 jipengfei Exp $
 */
public class SqlSessionFactoryUtils {

    public static SqlSessionFactory build(DataSource dataSource, DbTypeEnum dbTypeEnum) {
        try {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dataSource);
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().
                getResources("classpath*:/" + dbTypeEnum.getCode() + "/TableMetaSchema.xml"));
            // 加载全局的配置文件
            //输入执行sql到控制台 for 本地调试
            //bean.setPlugins(new Interceptor[]{new SqlStatementInterceptor()});
            bean.setConfigLocation(
                new DefaultResourceLoader().getResource("classpath:/" + dbTypeEnum.getCode() + "/Mybatis.xml"));
            return bean.getObject();
        }catch (Exception e){
            throw new RuntimeException("SqlSessionFactoryUtils error",e);
        }
    }

}