package com.alibaba.dataops.server.domain.data.core.service.impl;

import com.alibaba.dataops.server.domain.data.api.model.ExecuteResultDTO;
import com.alibaba.dataops.server.domain.data.api.param.template.TemplateExecuteParam;
import com.alibaba.dataops.server.domain.data.api.service.JdbcTemplateDataService;
import com.alibaba.dataops.server.domain.data.core.model.JdbcDataTemplate;
import com.alibaba.dataops.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;

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
            param.getConsoleId());

        // 解析sql
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(param.getSql(),
            DataCenterUtils.getDruidDbTypeByDataSourceId(param.getDataSourceId()));
        if (sqlStatement instanceof SQLSelectStatement) {
            return DataResult.of(jdbcDataTemplate.query(param.getSql()));
        } else {
            return DataResult.of(jdbcDataTemplate.update(param.getSql()));
        }
    }
}
