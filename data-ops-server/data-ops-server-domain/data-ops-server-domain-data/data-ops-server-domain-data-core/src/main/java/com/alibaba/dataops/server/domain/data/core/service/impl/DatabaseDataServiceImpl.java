package com.alibaba.dataops.server.domain.data.core.service.impl;

import java.util.Objects;
import java.util.stream.Collectors;

import com.alibaba.dataops.server.domain.data.api.model.CellDTO;
import com.alibaba.dataops.server.domain.data.api.model.DatabaseDTO;
import com.alibaba.dataops.server.domain.data.api.model.ExecuteResultDTO;
import com.alibaba.dataops.server.domain.data.api.param.database.DatabaseQueryAllParam;
import com.alibaba.dataops.server.domain.data.api.service.DatabaseDataService;
import com.alibaba.dataops.server.domain.data.core.model.JdbcDataTemplate;
import com.alibaba.dataops.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dataops.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dataops.server.tools.common.util.EasyCollectionUtils;
import com.alibaba.druid.DbType;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * 数据库服务
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class DatabaseDataServiceImpl implements DatabaseDataService {

    @Override
    public ListResult<DatabaseDTO> queryAll(DatabaseQueryAllParam param) {
        JdbcDataTemplate jdbcDataTemplate = DataCenterUtils.getDefaultJdbcDataTemplate(param.getDataSourceId());
        // TODO 根据不同的数据库类型来区分
        DbType dbType = DataCenterUtils.getDruidDbTypeByDataSourceId(param.getDataSourceId());
        ExecuteResultDTO executeResult = jdbcDataTemplate.queryData("show databases");
        return ListResult.of(EasyCollectionUtils.stream(executeResult.getDataList())
            .filter(CollectionUtils::isNotEmpty)
            // 获取第一个
            .map(dataList -> dataList.get(0))
            .filter(Objects::nonNull)
            .map(CellDTO::getStringValue)
            .filter(Objects::nonNull)
            .map(stringValue -> DatabaseDTO.builder().name(stringValue).build())
            .collect(Collectors.toList())
        );
    }
}
