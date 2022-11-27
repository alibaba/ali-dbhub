package com.alibaba.dbhub.server.domain.data.core.service.impl;

import java.util.List;

import com.alibaba.dbhub.server.domain.data.api.model.SqlDTO;
import com.alibaba.dbhub.server.domain.data.api.param.sql.SqlAnalyseParam;
import com.alibaba.dbhub.server.domain.data.api.service.SqlDataService;
import com.alibaba.dbhub.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dbhub.server.tools.common.util.EasyCollectionUtils;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * sql服务实现
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class SqlDataServiceImpl implements SqlDataService {

    @Override
    public ListResult<SqlDTO> analyse(SqlAnalyseParam param) {
        List<SQLStatement> sqlStatementList = SQLUtils.parseStatements(param.getSql(),
            DataCenterUtils.getDruidDbTypeByDataSourceId(param.getDataSourceId()));
        return ListResult.of(EasyCollectionUtils.toList(sqlStatementList,
            sqlStatement -> SqlDTO.builder().sql(sqlStatement.toString()).build()));
    }

}
