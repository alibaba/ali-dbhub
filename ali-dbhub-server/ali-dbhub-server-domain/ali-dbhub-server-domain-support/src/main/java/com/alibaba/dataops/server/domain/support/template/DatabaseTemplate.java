package com.alibaba.dataops.server.domain.support.template;

import java.util.List;

import com.alibaba.dataops.server.domain.support.model.Database;
import com.alibaba.dataops.server.domain.support.operations.DatabaseOperations;
import com.alibaba.dataops.server.domain.support.param.database.DatabaseQueryAllParam;
import com.alibaba.dataops.server.domain.support.util.DataCenterUtils;

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
public class DatabaseTemplate implements DatabaseOperations {

    @Override
    public List<Database> queryAll(DatabaseQueryAllParam param) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = DataCenterUtils.getDefaultJdbcTemplate(
            param.getDataSourceId());
        return namedParameterJdbcTemplate.query("show databases;",
            (rs, rowNum) -> Database.builder()
                .name(rs.getString(1))
                .build());
    }
}
