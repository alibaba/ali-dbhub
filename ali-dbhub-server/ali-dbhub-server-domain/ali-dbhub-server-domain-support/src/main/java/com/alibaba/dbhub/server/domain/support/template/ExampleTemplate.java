package com.alibaba.dbhub.server.domain.support.template;

import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.operations.ExampleOperations;

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
        return dbType.example().getCreateTable();
    }

    @Override
    public String alterTable(DbTypeEnum dbType) {
        return dbType.example().getAlterTable();
    }
}
