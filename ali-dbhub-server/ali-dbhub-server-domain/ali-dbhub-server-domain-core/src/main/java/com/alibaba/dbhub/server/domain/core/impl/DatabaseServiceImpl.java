package com.alibaba.dbhub.server.domain.core.impl;

import com.alibaba.dbhub.server.domain.api.service.DatabaseService;
import com.alibaba.dbhub.server.domain.support.model.Database;
import com.alibaba.dbhub.server.domain.support.operations.DatabaseOperations;
import com.alibaba.dbhub.server.domain.support.param.database.DatabaseQueryAllParam;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author moji
 * @version DataSourceCoreServiceImpl.java, v 0.1 2022年09月23日 15:51 moji Exp $
 * @date 2022/09/23
 */
@Service
public class DatabaseServiceImpl implements DatabaseService {

    @Autowired
    private DatabaseOperations databaseOperations;

    @Override
    public ListResult<Database> queryAll(DatabaseQueryAllParam param) {
        return ListResult.of(databaseOperations.queryAll(param));
    }

}
