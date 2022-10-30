package com.alibaba.dataops.server.domain.data.core.service.impl;

import com.alibaba.dataops.server.domain.data.api.model.DatabaseDTO;
import com.alibaba.dataops.server.domain.data.api.param.database.DatabaseQueryAllParam;
import com.alibaba.dataops.server.domain.data.api.service.DatabaseDataService;
import com.alibaba.dataops.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dataops.server.tools.base.wrapper.result.ListResult;
import com.alibaba.druid.DbType;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = DataCenterUtils.getDefaultJdbcTemplate(
            param.getDataSourceId());
        return ListResult.of(namedParameterJdbcTemplate.query("show databases;",
            (rs, rowNum) -> DatabaseDTO.builder()
                .name(rs.getString(1))
                .build()));
    }
}
