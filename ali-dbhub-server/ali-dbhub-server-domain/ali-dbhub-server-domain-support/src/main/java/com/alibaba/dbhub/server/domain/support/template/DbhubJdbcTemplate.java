package com.alibaba.dbhub.server.domain.support.template;

import java.sql.SQLException;

import com.alibaba.dbhub.server.domain.support.model.ExecuteResult;
import com.alibaba.dbhub.server.domain.support.model.support.JdbcDataTemplate;
import com.alibaba.dbhub.server.domain.support.operations.JdbcOperations;
import com.alibaba.dbhub.server.domain.support.param.template.TemplateCountParam;
import com.alibaba.dbhub.server.domain.support.param.template.TemplateExecuteParam;
import com.alibaba.dbhub.server.domain.support.util.DataCenterUtils;
import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.PagerUtils;
import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * jdbc服务实现
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class DbhubJdbcTemplate implements JdbcOperations {

    @Override
    public ExecuteResult execute(TemplateExecuteParam param) {
        JdbcDataTemplate jdbcDataTemplate = DataCenterUtils.getJdbcDataTemplate(param.getDataSourceId(),
            param.getConsoleId(), param.getDatabaseName());
        String sql = param.getSql();
        ExecuteResult executeResult;
        try {
            executeResult = jdbcDataTemplate.execute(sql, param.getPageSize());
        } catch (SQLException e) {
            log.warn("执行sql:{}异常", JSON.toJSONString(param), e);
            executeResult = ExecuteResult.builder()
                .sql(param.getSql())
                .success(Boolean.FALSE)
                .message(e.getMessage())
                .build();
        }
        return executeResult;
    }

    @Override
    public long count(TemplateCountParam param) {
        JdbcDataTemplate jdbcDataTemplate = DataCenterUtils.getJdbcDataTemplate(param.getDataSourceId(),
            param.getConsoleId(), param.getDatabaseName());
        DbType dbType = DataCenterUtils.getDruidDbTypeByDataSourceId(param.getDataSourceId());

        String countSql = PagerUtils.count(param.getSql(), dbType);
        ExecuteResult executeResult = null;
        try {
            executeResult = jdbcDataTemplate.execute(countSql, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return executeResult.getDataList().get(0).get(0).getBigDecimalValue().longValue();
    }
}
