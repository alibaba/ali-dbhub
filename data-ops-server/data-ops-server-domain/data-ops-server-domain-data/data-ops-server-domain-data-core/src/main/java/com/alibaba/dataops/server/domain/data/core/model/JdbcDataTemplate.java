package com.alibaba.dataops.server.domain.data.core.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.alibaba.dataops.server.domain.data.api.enums.CellTypeEnum;
import com.alibaba.dataops.server.domain.data.api.model.CellDTO;
import com.alibaba.dataops.server.domain.data.api.model.ExecuteResultDTO;
import com.alibaba.druid.pool.DruidDataSource;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.SqlProvider;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * jdbc模板
 *
 * @author Jiaju Zhuang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@Slf4j
public class JdbcDataTemplate {
    /**
     * 对应数据库存储的来源id
     */
    private Long dataSourceId;

    /**
     * 控制台的id
     */
    private Long consoleId;

    /**
     * 连接
     */
    private Connection connection;

    /**
     * 数据库连接源
     */
    private DruidDataSource druidDataSource;

    /**
     * 执行错误异常转换器
     */
    private SQLExceptionTranslator sqlExceptionTranslator;

    public JdbcDataTemplate(Long dataSourceId, Long consoleId, Connection connection, DruidDataSource druidDataSource) {
        this.dataSourceId = dataSourceId;
        this.consoleId = consoleId;
        this.connection = connection;
        this.druidDataSource = druidDataSource;
        this.sqlExceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(druidDataSource);
    }

    /**
     * 统计行数
     *
     * @param sql
     * @return
     */
    public long count(final String sql) throws DataAccessException {
        Assert.notNull(sql, "SQL must not be null");
        class CountStatementCallback implements StatementCallback<Long>, SqlProvider {
            @Override
            @Nullable
            public Long doInStatement(Statement stmt) throws SQLException {
                ResultSet rs = null;
                try {
                    rs = stmt.executeQuery(sql);
                    rs.next();
                    return rs.getLong(1);
                } finally {
                    JdbcUtils.closeResultSet(rs);
                }
            }

            @Override
            public String getSql() {
                return sql;
            }
        }

        return execute(new CountStatementCallback());
    }

    /**
     * 执行一个自定义的sql查询
     *
     * @param sql
     * @return
     */
    public ExecuteResultDTO query(final String sql) throws DataAccessException {
        Assert.notNull(sql, "SQL must not be null");

        class QueryStatementCallback implements StatementCallback<ExecuteResultDTO>, SqlProvider {
            @Override
            @Nullable
            public ExecuteResultDTO doInStatement(Statement stmt) throws SQLException {
                ResultSet rs = null;
                try {
                    rs = stmt.executeQuery(sql);
                    ExecuteResultDTO executeResult = ExecuteResultDTO.builder()
                        .sql(sql)
                        .build();
                    executeResult.setDescription("执行成功");
                    // 获取有几列
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();
                    int col = resultSetMetaData.getColumnCount();

                    // 获取header信息
                    List<CellDTO> headerList = Lists.newArrayListWithExpectedSize(col);
                    executeResult.setHeaderList(headerList);
                    for (int i = 1; i <= col; i++) {
                        headerList.add(CellDTO.builder().type(CellTypeEnum.STRING.getCode())
                            .stringValue(resultSetMetaData.getColumnName(i)).build());
                    }

                    // 获取数据信息
                    List<List<CellDTO>> dataList = Lists.newArrayList();
                    executeResult.setDataList(dataList);
                    while (rs.next()) {
                        List<CellDTO> row = Lists.newArrayListWithExpectedSize(col);
                        dataList.add(row);
                        for (int i = 1; i <= col; i++) {
                            row.add(
                                com.alibaba.dataops.server.domain.data.core.util.JdbcUtils.getResultSetValue(rs, i));
                        }
                    }
                    return executeResult;
                } finally {
                    JdbcUtils.closeResultSet(rs);
                }
            }

            @Override
            public String getSql() {
                return sql;
            }
        }

        return execute(new QueryStatementCallback());
    }

    /**
     * 执行一个自定义的sql修改
     *
     * @param sql
     * @return
     * @throws DataAccessException
     */
    public ExecuteResultDTO update(final String sql) throws DataAccessException {
        Assert.notNull(sql, "SQL must not be null");
        /**
         * Callback to execute the update statement.
         */
        class UpdateStatementCallback implements StatementCallback<ExecuteResultDTO>, SqlProvider {
            @Override
            public ExecuteResultDTO doInStatement(Statement stmt) throws SQLException {
                ExecuteResultDTO executeResult = ExecuteResultDTO.builder()
                    .sql(sql)
                    .build();
                executeResult.setUpdateCount(stmt.executeUpdate(sql));
                executeResult.setDescription("执行成功");
                return executeResult;
            }

            @Override
            public String getSql() {
                return sql;
            }
        }

        return execute(new UpdateStatementCallback());
    }

    /**
     * 执行一个sql
     *
     * @param action
     * @param <T>
     * @return
     * @throws SQLException
     */
    @Nullable
    private <T> T execute(StatementCallback<T> action) throws DataAccessException {
        String sql = getSql(action);
        log.info("执行自定义sql:{}", sql);
        Statement stmt = null;
        try {
            return action.doInStatement(connection.createStatement());
        } catch (SQLException ex) {
            throw translateException("CallableStatementCallback", sql, ex);
        } finally {
            JdbcUtils.closeStatement(stmt);
        }
    }

    /**
     * 执行一个sql
     *
     * @param action
     * @param <T>
     * @return
     * @throws SQLException
     */
    @Nullable
    private <T> T execute(PreparedStatementCallback<T> action) throws DataAccessException {
        String sql = getSql(action);
        log.info("执行自定义sql:{}", sql);
        Statement stmt = null;
        try {
            return action.doInPreparedStatement(connection.prepareStatement(sql));
        } catch (SQLException ex) {
            throw translateException("CallableStatementCallback", sql, ex);
        } finally {
            JdbcUtils.closeStatement(stmt);
        }
    }

    private String getSql(Object sqlProvider) {
        if (sqlProvider instanceof SqlProvider) {
            return ((SqlProvider)sqlProvider).getSql();
        } else {
            return null;
        }
    }

    protected DataAccessException translateException(String task, @Nullable String sql, SQLException ex) {
        DataAccessException dae = sqlExceptionTranslator.translate(task, sql, ex);
        return (dae != null ? dae : new UncategorizedSQLException(task, sql, ex));
    }

}
