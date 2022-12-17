package com.alibaba.dbhub.server.domain.support.template;

import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.dbhub.server.domain.support.model.Database;
import com.alibaba.dbhub.server.domain.support.operations.DatabaseOperations;
import com.alibaba.dbhub.server.domain.support.param.database.DatabaseQueryAllParam;
import com.alibaba.dbhub.server.domain.support.util.DataCenterUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import static com.alibaba.dbhub.server.domain.support.util.DataCenterUtils.getMetaSchema;

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
        List<String> databases = getMetaSchema(param.getDataSourceId()).showDatabases();
        return databases.stream().map(name -> Database.builder().name(name)
            .build()).collect(Collectors.toList());
    }
}
