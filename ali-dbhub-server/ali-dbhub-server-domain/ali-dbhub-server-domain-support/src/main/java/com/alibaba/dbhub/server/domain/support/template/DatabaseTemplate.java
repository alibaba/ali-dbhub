package com.alibaba.dbhub.server.domain.support.template;

import java.util.List;

import com.alibaba.dbhub.server.domain.support.model.Database;
import com.alibaba.dbhub.server.domain.support.operations.DatabaseOperations;
import com.alibaba.dbhub.server.domain.support.param.database.DatabaseQueryAllParam;
import com.alibaba.dbhub.server.domain.support.sql.DbhubContext;
import com.alibaba.dbhub.server.tools.common.util.EasyCollectionUtils;

import lombok.extern.slf4j.Slf4j;
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
        List<String> databases = DbhubContext.getMetaSchema().databases();
        return EasyCollectionUtils.toList(databases, name -> Database.builder().name(name).build());
    }
}
