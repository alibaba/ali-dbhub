package com.alibaba.dbhub.server.domain.support.model.support;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.alibaba.dbhub.server.domain.support.enums.CellTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.Cell;
import com.alibaba.dbhub.server.domain.support.model.ExecuteResult;
import com.alibaba.dbhub.server.tools.base.constant.EasyToolsConstant;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.util.Assert;

/**
 * jdbc模板
 *
 * @author Jiaju Zhuang
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
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
    private DbhubDataSource dbhubDataSource;

    /**
     * 执行错误异常转换器
     */
    private SQLExceptionTranslator sqlExceptionTranslator;

    public JdbcDataTemplate(Long dataSourceId, Long consoleId, Connection connection, DbhubDataSource dbhubDataSource) {
        this.dataSourceId = dataSourceId;
        this.consoleId = consoleId;
        this.connection = connection;
        this.dbhubDataSource = dbhubDataSource;
        this.sqlExceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(dbhubDataSource);
    }

    /**
     * 执行sql
     *
     * @param sql
     * @return
     */
    public ExecuteResult execute(final String sql, Integer pageSize) throws SQLException {
        Assert.notNull(sql, "SQL must not be null");
        log.info("execute:{}", sql);

        ExecuteResult executeResult = ExecuteResult.builder()
            .sql(sql)
            .success(Boolean.TRUE)
            .build();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            boolean query = stmt.execute(sql);
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
}
