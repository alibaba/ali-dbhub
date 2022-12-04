package com.alibaba.dbhub.server.domain.support.template;

import com.alibaba.dbhub.server.domain.support.dialect.DatabaseSpi;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.operations.ExampleOperations;
import com.alibaba.dbhub.server.domain.support.util.DataCenterUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 样例
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class ExampleTemplate implements ExampleOperations {

    @Override
    public String createTable(DbTypeEnum dbType) {
        DatabaseSpi databaseSpi = DataCenterUtils.getDatabaseSpi(dbType);
        return databaseSpi.example().getCreateTable();
    }

    @Override
    public String alterTable(DbTypeEnum dbType) {
        DatabaseSpi databaseSpi = DataCenterUtils.getDatabaseSpi(dbType);
        return databaseSpi.example().getAlterTable();
    }
}
