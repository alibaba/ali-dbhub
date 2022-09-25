package com.alibaba.dataops.server.domain.data.core.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlProvider;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.support.JdbcUtils;
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
public class JdbcDataTemplate extends JdbcTemplate {

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
     * 本方法未做任何修改
     *
     * @param sql the SQL query to execute
     * @param rse a callback that will extract all rows of results
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    @Override
    @Nullable
    public <T> T query(final String sql, final ResultSetExtractor<T> rse) throws DataAccessException {
        Assert.notNull(sql, "SQL must not be null");
        Assert.notNull(rse, "ResultSetExtractor must not be null");
        if (logger.isDebugEnabled()) {
            logger.debug("Executing SQL query [" + sql + "]");
        }

        /**
         * Callback to execute the query.
         */
        class QueryStatementCallback implements StatementCallback<T>, SqlProvider {
            @Override
            @Nullable
            public T doInStatement(Statement stmt) throws SQLException {
                ResultSet rs = null;
                try {
                    rs = stmt.executeQuery(sql);
                    return rse.extractData(rs);
                } finally {
                    JdbcUtils.closeResultSet(rs);
                }
            }

            @Override
            public String getSql() {
                return sql;
            }
        }

        return execute(new QueryStatementCallback(), true);
    }

    @Nullable
    private <T> T execute(StatementCallback<T> action, boolean closeResources) throws DataAccessException {
        Assert.notNull(action, "Callback object must not be null");

        // change 修改连接直接用固定的
        Connection con = connection;
        Statement stmt = null;

        try {
            stmt = con.createStatement();
            applyStatementSettings(stmt);
            T result = action.doInStatement(stmt);
            handleWarnings(stmt);
            return result;
        } catch (SQLException ex) {
            // Release Connection early, to avoid potential connection pool deadlock
            // in the case when the exception translator hasn't been initialized yet.
            String sql = getSql(action);
            JdbcUtils.closeStatement(stmt);

            // change 不再关闭连接
            throw translateException("StatementCallback", sql, ex);
        } finally {
            if (closeResources) {
                JdbcUtils.closeStatement(stmt);
                // change 不再关闭连接
            }
        }
    }

    /**
     * 本方法未做任何修改
     *
     * @param sqlProvider
     * @return
     */
    private static String getSql(Object sqlProvider) {
        if (sqlProvider instanceof SqlProvider) {
            return ((SqlProvider)sqlProvider).getSql();
        } else {
            return null;
        }
    }

}
