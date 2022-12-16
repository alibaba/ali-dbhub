/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.model.support;

import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.util.SqlSessionFactoryUtils;

import org.apache.ibatis.session.SqlSession;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author jipengfei
 * @version : JdbcAccessor.java
 */
public class JdbcAccessor {

    private SqlSession sqlSession;

    private MetaSchema service;

    private NamedParameterJdbcTemplate jdbcTemplate;

    private DataDataSource dataDataSource;

    private Long dataSourceId;

    public JdbcAccessor(Long dataSourceId, DataDataSource dataDataSource) {
        this.dataSourceId = dataSourceId;
        this.dataDataSource = dataDataSource;
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataDataSource);
        this.sqlSession = SqlSessionFactoryUtils.build(dataDataSource,dataDataSource.getDbType()).openSession();
        this.service = dataDataSource.getDbType().metaSchema(sqlSession);
    }

    /**
     * Getter method for property <tt>sqlSession</tt>.
     *
     * @return property value of sqlSession
     */
    public SqlSession getSqlSession() {
        return sqlSession;
    }

    /**
     * Setter method for property <tt>sqlSession</tt>.
     *
     * @param sqlSession value to be assigned to property sqlSession
     */
    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    /**
     * Getter method for property <tt>service</tt>.
     *
     * @return property value of service
     */
    public MetaSchema getService() {
        return service;
    }

    /**
     * Setter method for property <tt>service</tt>.
     *
     * @param service value to be assigned to property service
     */
    public void setService(MetaSchema service) {
        this.service = service;
    }

    /**
     * Getter method for property <tt>jdbcTemplate</tt>.
     *
     * @return property value of jdbcTemplate
     */
    public NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * Setter method for property <tt>jdbcTemplate</tt>.
     *
     * @param jdbcTemplate value to be assigned to property jdbcTemplate
     */
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Getter method for property <tt>dataDataSource</tt>.
     *
     * @return property value of dataDataSource
     */
    public DataDataSource getDataDataSource() {
        return dataDataSource;
    }

    /**
     * Setter method for property <tt>dataDataSource</tt>.
     *
     * @param dataDataSource value to be assigned to property dataDataSource
     */
    public void setDataDataSource(DataDataSource dataDataSource) {
        this.dataDataSource = dataDataSource;
    }

    /**
     * Getter method for property <tt>dataSourceId</tt>.
     *
     * @return property value of dataSourceId
     */
    public Long getDataSourceId() {
        return dataSourceId;
    }

    /**
     * Setter method for property <tt>dataSourceId</tt>.
     *
     * @param dataSourceId value to be assigned to property dataSourceId
     */
    public void setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
}