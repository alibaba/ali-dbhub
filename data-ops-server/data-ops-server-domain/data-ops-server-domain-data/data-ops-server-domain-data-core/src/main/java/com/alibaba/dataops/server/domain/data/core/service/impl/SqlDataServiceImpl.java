package com.alibaba.dataops.server.domain.data.core.service.impl;

import java.util.List;

import com.alibaba.dataops.server.domain.data.api.enums.DbTypeEnum;
import com.alibaba.dataops.server.domain.data.api.model.SqlDTO;
import com.alibaba.dataops.server.domain.data.api.param.sql.SqlAnalyseParam;
import com.alibaba.dataops.server.domain.data.api.service.SqlDataService;
import com.alibaba.dataops.server.domain.data.core.model.DataSourceWrapper;
import com.alibaba.dataops.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dataops.server.domain.data.core.util.DriverClassUtils;
import com.alibaba.dataops.server.tools.base.excption.BusinessException;
import com.alibaba.dataops.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dataops.server.tools.common.enums.ErrorEnum;
import com.alibaba.dataops.server.tools.common.util.EasyCollectionUtils;
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
        DataSourceWrapper dataSourceWrapper = DataCenterUtils.DATA_SOURCE_CACHE.get(param.getDataSourceId());
        if (dataSourceWrapper == null) {
            throw new BusinessException(ErrorEnum.DATA_SOURCE_NOT_FOUND);
        }
        DbTypeEnum driverClass = dataSourceWrapper.getDriverClass();
        List<SQLStatement> sqlStatementList = SQLUtils.parseStatements(param.getSql(),
            DriverClassUtils.parse2dbType(driverClass));
        return ListResult.of(EasyCollectionUtils.toList(sqlStatementList,
            sqlStatement -> SqlDTO.builder().sql(sqlStatement.toString()).build()));
    }

}
