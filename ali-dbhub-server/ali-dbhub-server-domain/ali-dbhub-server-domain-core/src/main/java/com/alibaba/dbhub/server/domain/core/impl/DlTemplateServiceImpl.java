package com.alibaba.dbhub.server.domain.core.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dbhub.server.domain.api.param.DlExecuteParam;
import com.alibaba.dbhub.server.domain.api.service.DlTemplateService;
import com.alibaba.dbhub.server.domain.support.model.ExecuteResult;
import com.alibaba.dbhub.server.domain.support.model.Sql;
import com.alibaba.dbhub.server.domain.support.operations.JdbcOperations;
import com.alibaba.dbhub.server.domain.support.operations.SqlOperations;
import com.alibaba.dbhub.server.domain.support.param.sql.SqlAnalyseParam;
import com.alibaba.dbhub.server.domain.support.param.template.TemplateExecuteParam;
import com.alibaba.dbhub.server.tools.base.excption.BusinessException;
import com.alibaba.dbhub.server.tools.base.excption.DatasourceErrorEnum;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author moji
 * @version DataSourceCoreServiceImpl.java, v 0.1 2022年09月23日 15:51 moji Exp $
 * @date 2022/09/23
 */
@Service
public class DlTemplateServiceImpl implements DlTemplateService {

    @Autowired
    private JdbcOperations jdbcOperations;

    @Autowired
    private SqlOperations sqlOperations;

    @Override
    public ListResult<ExecuteResult> execute(DlExecuteParam param) {
        if (StringUtils.isBlank(param.getSql())) {
            return ListResult.empty();
        }

        // 解析sql
        SqlAnalyseParam sqlAnalyseParam = new SqlAnalyseParam();
        sqlAnalyseParam.setDataSourceId(param.getDataSourceId());
        sqlAnalyseParam.setSql(param.getSql());
        List<Sql> sqlList = sqlOperations.analyse(sqlAnalyseParam);
        if (CollectionUtils.isEmpty(sqlList)) {
            throw new BusinessException(DatasourceErrorEnum.SQL_ANALYSIS_ERROR);
        }

        List<ExecuteResult> result = new ArrayList<>();
        // 执行sql
        for (Sql sql : sqlList) {
            TemplateExecuteParam templateQueryParam = new TemplateExecuteParam();
            templateQueryParam.setConsoleId(param.getConsoleId());
            templateQueryParam.setDataSourceId(param.getDataSourceId());
            templateQueryParam.setSql(sql.getSql());
            ExecuteResult executeResult = jdbcOperations.execute(templateQueryParam);
            result.add(executeResult);
        }

        return ListResult.of(result);
    }
}
