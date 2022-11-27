package com.alibaba.dbhub.server.domain.data.core.service.impl;

import java.sql.SQLException;

import com.alibaba.dbhub.server.domain.data.api.model.ExecuteResultDTO;
import com.alibaba.dbhub.server.domain.data.api.param.template.TemplateCountParam;
import com.alibaba.dbhub.server.domain.data.api.param.template.TemplateExecuteParam;
import com.alibaba.dbhub.server.domain.data.api.service.JdbcTemplateDataService;
import com.alibaba.dbhub.server.domain.data.core.model.JdbcDataTemplate;
import com.alibaba.dbhub.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;
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
public class JdbcTemplateDataServiceImpl implements JdbcTemplateDataService {

    @Override
    public DataResult<ExecuteResultDTO> execute(TemplateExecuteParam param) {
        JdbcDataTemplate jdbcDataTemplate = DataCenterUtils.getJdbcDataTemplate(param.getDataSourceId(),
            param.getConsoleId(), param.getDatabaseName());
        String sql = param.getSql();
        ExecuteResultDTO executeResult;
        try {
            executeResult = jdbcDataTemplate.execute(sql, param.getPageSize());
        } catch (SQLException e) {
            log.warn("执行sql:{}异常", JSON.toJSONString(param), e);
            executeResult = ExecuteResultDTO.builder()
                .sql(param.getSql())
                .success(Boolean.FALSE)
                .message(e.getMessage())
                .build();
        }
        return DataResult.of(executeResult);
    }

    @Override
    public DataResult<Long> count(TemplateCountParam param) {
        JdbcDataTemplate jdbcDataTemplate = DataCenterUtils.getJdbcDataTemplate(param.getDataSourceId(),
            param.getConsoleId(), param.getDatabaseName());
        DbType dbType = DataCenterUtils.getDruidDbTypeByDataSourceId(param.getDataSourceId());

        String countSql = PagerUtils.count(param.getSql(), dbType);
        ExecuteResultDTO executeResult = null;
        try {
            executeResult = jdbcDataTemplate.execute(countSql, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return DataResult.of(executeResult.getDataList().get(0).get(0).getBigDecimalValue().longValue());
    }
}
