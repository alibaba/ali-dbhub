package com.alibaba.dataops.server.domain.data.core.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.alibaba.dataops.server.domain.data.api.enums.CellTypeEnum;
import com.alibaba.dataops.server.domain.data.api.model.CellDTO;
import com.alibaba.dataops.server.domain.data.api.model.ExecuteResultDTO;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
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

    public ExecuteResultDTO queryData(final String sql) {

        /**
         * Callback to execute the query.
         */
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

        return execute(new QueryStatementCallback(), true);
    }

    /**
     * Query using a prepared statement, allowing for a PreparedStatementCreator
     * and a PreparedStatementSetter. Most other query methods use this method,
     * but application code will always work with either a creator or a setter.
     * @param psc a callback that creates a PreparedStatement given a Connection
     * @param pss a callback that knows how to set values on the prepared statement.
     * If this is {@code null}, the SQL will be assumed to contain no bind parameters.
     * @param rse a callback that will extract results
     * @return an arbitrary result object, as returned by the ResultSetExtractor
     * @throws DataAccessException if there is any problem
     */
    @Nullable
    @Override
    public <T> T query(
        PreparedStatementCreator psc, @Nullable final PreparedStatementSetter pss, final ResultSetExtractor<T> rse)
        throws DataAccessException {

        Assert.notNull(rse, "ResultSetExtractor must not be null");
        logger.debug("Executing prepared SQL query");

        return execute(psc, new PreparedStatementCallback<T>() {
            @Override
            @Nullable
            public T doInPreparedStatement(PreparedStatement ps) throws SQLException {
                ResultSet rs = null;
                try {
                    if (pss != null) {
                        pss.setValues(ps);
                    }
                    rs = ps.executeQuery();
                    return rse.extractData(rs);
                }
                finally {
                    JdbcUtils.closeResultSet(rs);
                    if (pss instanceof ParameterDisposer) {
                        ((ParameterDisposer) pss).cleanupParameters();
                    }
                }
            }
        }, true);
    }
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

    /**
     * 查询的执行器
     *
     * @param action
     * @param closeResources
     * @param <T>
     * @return
     * @throws DataAccessException
     */
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

    @Override
    public int update(final String sql) throws DataAccessException {
        Assert.notNull(sql, "SQL must not be null");
        if (logger.isDebugEnabled()) {
            logger.debug("Executing SQL update [" + sql + "]");
        }

        /**
         * Callback to execute the update statement.
         */
        class UpdateStatementCallback implements StatementCallback<Integer>, SqlProvider {
            @Override
            public Integer doInStatement(Statement stmt) throws SQLException {
                int rows = stmt.executeUpdate(sql);
                if (logger.isTraceEnabled()) {
                    logger.trace("SQL update affected " + rows + " rows");
                }
                return rows;
            }

            @Override
            public String getSql() {
                return sql;
            }
        }

        return updateCount(execute(new UpdateStatementCallback(), true));
    }


    /**
     * 修改时的执行器
     *
     * @param psc
     * @param action
     * @param closeResources
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    @Nullable
    private <T> T execute(PreparedStatementCreator psc, PreparedStatementCallback<T> action, boolean closeResources)
        throws DataAccessException {

        Assert.notNull(psc, "PreparedStatementCreator must not be null");
        Assert.notNull(action, "Callback object must not be null");
        if (logger.isDebugEnabled()) {
            String sql = getSql(psc);
            logger.debug("Executing prepared SQL statement" + (sql != null ? " [" + sql + "]" : ""));
        }

        // change 修改连接直接用固定的
        Connection con = connection;
        PreparedStatement ps = null;
        try {
            ps = psc.createPreparedStatement(con);
            applyStatementSettings(ps);
            T result = action.doInPreparedStatement(ps);
            handleWarnings(ps);
            return result;
        } catch (SQLException ex) {
            // Release Connection early, to avoid potential connection pool deadlock
            // in the case when the exception translator hasn't been initialized yet.
            if (psc instanceof ParameterDisposer) {
                ((ParameterDisposer)psc).cleanupParameters();
            }
            String sql = getSql(psc);
            psc = null;
            JdbcUtils.closeStatement(ps);
            ps = null;
            // change 不再关闭连接
            throw translateException("PreparedStatementCallback", sql, ex);
        } finally {
            if (closeResources) {
                if (psc instanceof ParameterDisposer) {
                    ((ParameterDisposer)psc).cleanupParameters();
                }
                JdbcUtils.closeStatement(ps);
                // change 不再关闭连接
            }
        }
    }


    /**
     * 本方法未做修改
     *
     * @param sql static SQL to execute
     * @throws DataAccessException
     */
    @Override
    public void execute(final String sql) throws DataAccessException {
        if (logger.isDebugEnabled()) {
            logger.debug("Executing SQL statement [" + sql + "]");
        }

        /**
         * Callback to execute the statement.
         */
        class ExecuteStatementCallback implements StatementCallback<Object>, SqlProvider {
            @Override
            @Nullable
            public Object doInStatement(Statement stmt) throws SQLException {
                stmt.execute(sql);
                return null;
            }

            @Override
            public String getSql() {
                return sql;
            }
        }

        execute(new ExecuteStatementCallback(), true);
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

    /**
     * 本方法未做任何修改
     *
     * @param result
     * @return
     */
    private static int updateCount(@Nullable Integer result) {
        Assert.state(result != null, "No update count");
        return result;
    }

}
