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
import com.alibaba.dataops.server.tools.base.constant.EasyToolsConstant;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
    private DataDataSource dataDataSource ;

    /**
     * 执行错误异常转换器
     */
    private SQLExceptionTranslator sqlExceptionTranslator;

    public JdbcDataTemplate(Long dataSourceId, Long consoleId, Connection connection, DataDataSource dataDataSource ) {
        this.dataSourceId = dataSourceId;
        this.consoleId = consoleId;
        this.connection = connection;
        this.dataDataSource = dataDataSource;
        this.sqlExceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(dataDataSource);
    }

    /**
     * 执行sql
     *
     * @param sql
     * @return
     */
    public ExecuteResultDTO execute(final String sql, Integer pageSize) throws SQLException {
        Assert.notNull(sql, "SQL must not be null");
        log.info("execute:{}", sql);

        ExecuteResultDTO executeResult = ExecuteResultDTO.builder()
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
                    List<CellDTO> headerList = Lists.newArrayListWithExpectedSize(col);
                    executeResult.setHeaderList(headerList);
                    for (int i = 1; i <= col; i++) {
                        headerList.add(CellDTO.builder().type(CellTypeEnum.STRING.getCode())
                            .stringValue(resultSetMetaData.getColumnName(i)).build());
                    }

                    // 获取数据信息
                    List<List<CellDTO>> dataList = Lists.newArrayList();
                    executeResult.setDataList(dataList);

                    // 分页大小
                    executeResult.setHasNextPage(Boolean.FALSE);
                    if (pageSize == null) {
                        pageSize = EasyToolsConstant.MAX_PAGE_SIZE;
                    }
                    int rsSize = 0;
                    while (rs.next()) {
                        List<CellDTO> row = Lists.newArrayListWithExpectedSize(col);
                        dataList.add(row);
                        for (int i = 1; i <= col; i++) {
                            row.add(
                                com.alibaba.dataops.server.domain.data.core.util.JdbcUtils.getResultSetValue(rs, i));
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
