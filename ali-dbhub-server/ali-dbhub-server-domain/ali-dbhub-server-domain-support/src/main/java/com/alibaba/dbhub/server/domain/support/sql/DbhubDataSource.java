/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.alibaba.dbhub.server.domain.support.enums.CellTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.Cell;
import com.alibaba.dbhub.server.domain.support.model.ExecuteResult;
import com.alibaba.dbhub.server.domain.support.sql.DbhubContext.ConnectInfo;
import com.alibaba.dbhub.server.tools.base.constant.EasyToolsConstant;
import com.alibaba.dbhub.server.tools.base.excption.CommonErrorEnum;
import com.alibaba.dbhub.server.tools.base.excption.SystemException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;

/**
 * Dbhub 统一数据库连接管理
 * TODO 长时间不用连接可以关闭，待优化
 *
 * @author jipengfei
 * @version : DbhubDataSource.java
 */
@Slf4j
public class DbhubDataSource extends DynamicDataSource {
    /**
     * 全局单例
     */
    private static final DbhubDataSource INSTANCE = new DbhubDataSource();

    /**
     * 存储每个数据源对应的连接信息
     */
    //private static Map<ConnectInfo, Connection> DATASOURCE_CONNECTION_MAP = Collections.synchronizedMap(
    //    new
    //        LRUCache<>(100, entry -> {
    //        try {
    //            Connection connection = entry.getValue();
    //            if (connection != null && !connection.isClosed()) {
    //                connection.close();
    //            }
    //        } catch (SQLException e) {
    //            throw new RuntimeException(e);
    //        }
    //    })
    //);

    private static Cache<ConnectInfo, Connection> DATASOURCE_CONNECTION_MAP = CacheBuilder.newBuilder()
        // 最大3个 //Cache中存储的对象,写入3秒后过期
        .maximumSize(100)
        .expireAfterWrite(30, TimeUnit.SECONDS)
        .recordStats()
        .removalListener(
            (RemovalListener<ConnectInfo, Connection>)notification -> {
                Connection connection = notification.getValue();
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        ).build();

    /**
     * 存储每个数据源对应的 mybatis SqlSessionFactoryBean
     */
    private static Cache<ConnectInfo, SqlSessionFactoryBean> SQL_SESSION_FACTORY_MAP = CacheBuilder.newBuilder()
        // 最大3个 //Cache中存储的对象,写入3秒后过期
        .maximumSize(100)
        .expireAfterWrite(30, TimeUnit.SECONDS)
        .recordStats().build();

    private DbhubDataSource() {}

    public static DbhubDataSource getInstance() {
        return INSTANCE;
    }

    @Override
    public Connection getConnection() throws SQLException {
        ConnectInfo info = DbhubContext.getConnectInfo();
        if (info == null || info.getDataSourceId() == null) {
            throw new UnsupportedOperationException();
        }
        Connection connection = DATASOURCE_CONNECTION_MAP.getIfPresent(info);
        if (connection == null || connection.isClosed()) {
            synchronized (info) {
                connection = DATASOURCE_CONNECTION_MAP.getIfPresent(info);
                if (connection == null) {
                    connection = DriverManager.getConnection(info.getUrl().trim(), info.getUser(),
                        info.getPassword());
                    DATASOURCE_CONNECTION_MAP.put(info, connection);
                }
            }
        }
        return connection;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        ConnectInfo info = DbhubContext.getConnectInfo();
        if (info == null || info.getDataSourceId() == null) {
            throw new UnsupportedOperationException();
        }
        SqlSessionFactoryBean factoryBean = SQL_SESSION_FACTORY_MAP.getIfPresent(info);
        try {
            if (factoryBean != null) {
                SqlSession session = factoryBean.getObject().openSession();
                Connection connection = session.getConnection();
                if (connection == null || connection.isClosed()) {
                    factoryBean = createSqlSessionMapper(info);
                }
            } else {
                factoryBean = createSqlSessionMapper(info);
            }
            SqlSession session = factoryBean.getObject().openSession();
            return session.getMapper(type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        ConnectInfo info = DbhubContext.getConnectInfo();
        Connection connection = DATASOURCE_CONNECTION_MAP.getIfPresent(info);
        if (connection != null) {
            closeConnection(connection);
            DATASOURCE_CONNECTION_MAP.invalidate(info);
        }
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private SqlSessionFactoryBean createSqlSessionMapper(ConnectInfo info) {
        synchronized (info) {
            SqlSessionFactoryBean bean = SQL_SESSION_FACTORY_MAP.getIfPresent(info);
            if (bean == null) {
                bean = new SqlSessionFactoryBean();
                bean.setDataSource(this);
                try {
                    bean.setMapperLocations(new PathMatchingResourcePatternResolver().
                        getResources("classpath*:/" + info.getDbType().getCode() + "/TableMetaSchema.xml"));
                    // 加载全局的配置文件 输入执行sql到控制台 for 本地调试,bean.setPlugins(new Interceptor[]{new SqlStatementInterceptor()});
                    bean.setConfigLocation(
                        new DefaultResourceLoader().getResource(
                            "classpath:/" + info.getDbType().getCode() + "/Mybatis.xml"));
                    SQL_SESSION_FACTORY_MAP.put(info, bean);

                } catch (Exception e) {
                    throw new RuntimeException("SqlSession error", e);
                }
            }
            return bean;
        }
    }

    public ExecuteResult execute(final String sql, Integer pageSize) throws SQLException {
        Assert.notNull(sql, "SQL must not be null");
        log.info("execute:{}", sql);

        ExecuteResult executeResult = ExecuteResult.builder()
            .sql(sql)
            .success(Boolean.TRUE)
            .build();
        Statement stmt = null;
        try {
            stmt = getConnection().createStatement();
            boolean query = stmt.execute(sql.replaceFirst(";", ""));
            executeResult.setDescription("执行成功");
            // 代表是查询
            if (query) {
                ResultSet rs = null;
                try {
                    rs = stmt.getResultSet();
                    // 获取有几列
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();
                    int col = resultSetMetaData.getColumnCount();

                    // 获取header信息
                    List<Cell> headerList = Lists.newArrayListWithExpectedSize(col);
                    executeResult.setHeaderList(headerList);
                    for (int i = 1; i <= col; i++) {
                        headerList.add(Cell.builder().type(CellTypeEnum.STRING.getCode())
                            .stringValue(resultSetMetaData.getColumnName(i)).build());
                    }

                    // 获取数据信息
                    List<List<Cell>> dataList = Lists.newArrayList();
                    executeResult.setDataList(dataList);

                    // 分页大小
                    executeResult.setHasNextPage(Boolean.FALSE);
                    if (pageSize == null) {
                        pageSize = EasyToolsConstant.MAX_PAGE_SIZE;
                    }
                    int rsSize = 0;
                    while (rs.next()) {
                        List<Cell> row = Lists.newArrayListWithExpectedSize(col);
                        dataList.add(row);
                        for (int i = 1; i <= col; i++) {
                            row.add(com.alibaba.dbhub.server.domain.support.util.JdbcUtils.getResultSetValue(rs, i));
                        }
                        rsSize++;
                        // 到达下一页了
                        if (rsSize >= pageSize) {
                            executeResult.setHasNextPage(Boolean.TRUE);
                            break;
                        }
                    }
                    return executeResult;
                } finally {
                    JdbcUtils.closeResultSet(rs);
                }
            } else {
                // 修改或者其他
                executeResult.setUpdateCount(stmt.getUpdateCount());
            }
        } finally {
            JdbcUtils.closeStatement(stmt);
        }
        return executeResult;
    }

    public void connectDatabase(String database) {
        ConnectInfo info = DbhubContext.getConnectInfo();
        switch (info.getDbType()) {
            case MYSQL, SQLSERVER -> {
                try {
                    execute("use " + database + ";", null);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            case ORACLE,H2,SQLITE -> {

            }
            case POSTGRESQL -> {
                //close();
                info.setDatabase(database);
                try {
                    getConnection();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            default -> {

            }
        }
    }

}
