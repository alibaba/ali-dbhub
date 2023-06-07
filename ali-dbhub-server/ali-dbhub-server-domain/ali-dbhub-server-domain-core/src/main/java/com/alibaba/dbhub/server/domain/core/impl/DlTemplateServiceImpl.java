package com.alibaba.dbhub.server.domain.core.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.dbhub.server.domain.api.param.DlExecuteParam;
import com.alibaba.dbhub.server.domain.api.service.DlTemplateService;
import com.alibaba.dbhub.server.domain.support.model.ExecuteResult;
import com.alibaba.dbhub.server.domain.api.param.SqlAnalyseParam;
import com.alibaba.dbhub.server.domain.support.sql.DbhubContext;
import com.alibaba.dbhub.server.domain.support.sql.SQLExecutor;
import com.alibaba.dbhub.server.domain.support.util.JdbcUtils;
import com.alibaba.dbhub.server.tools.base.excption.BusinessException;
import com.alibaba.dbhub.server.tools.base.excption.DatasourceErrorEnum;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;
import com.alibaba.druid.sql.parser.DbhubSQLParserUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author moji
 * @version DataSourceCoreServiceImpl.java, v 0.1 2022年09月23日 15:51 moji Exp $
 * @date 2022/09/23
 */
@Slf4j
@Service
public class DlTemplateServiceImpl implements DlTemplateService {


    @Override
    public ListResult<ExecuteResult> execute(DlExecuteParam param) {
        if (StringUtils.isBlank(param.getSql())) {
            return ListResult.empty();
        }
        // 解析sql
        List<String> sqlList = DbhubSQLParserUtils.splitAndRemoveComment(param.getSql(),
            JdbcUtils.parse2DruidDbType(DbhubContext.getConnectInfo().getDbType()));
        if (CollectionUtils.isEmpty(sqlList)) {
            throw new BusinessException(DatasourceErrorEnum.SQL_ANALYSIS_ERROR);
        }

        List<ExecuteResult> result = new ArrayList<>();
        ListResult listResult = ListResult.of(result);
        // 执行sql
        for (String sql : sqlList) {
            ExecuteResult executeResult = execute(sql,500);
            result.add(executeResult);
            if(!executeResult.getSuccess()){
                listResult.setSuccess(false);
                listResult.errorCode(executeResult.getDescription());
                listResult.setErrorMessage(executeResult.getMessage());
            }
        }
        return listResult;
    }

    private ExecuteResult execute(String sql,int size) {
        ExecuteResult executeResult;
        try {
            executeResult = SQLExecutor.getInstance().execute(sql, size);
        } catch (SQLException e) {
            log.warn("执行sql:{}异常", sql, e);
            executeResult = ExecuteResult.builder()
                .sql(sql)
                .success(Boolean.FALSE)
                .message(e.getMessage())
                .build();
        }
        return executeResult;
    }


}
