package com.alibaba.dbhub.server.domain.core.impl;

import java.util.List;

import com.alibaba.dbhub.server.domain.api.service.DatabaseService;
import com.alibaba.dbhub.server.domain.support.model.Database;
import com.alibaba.dbhub.server.domain.support.model.Schema;
import com.alibaba.dbhub.server.domain.support.param.database.DatabaseQueryAllParam;
import com.alibaba.dbhub.server.domain.support.param.database.SchemaQueryParam;
import com.alibaba.dbhub.server.domain.support.sql.DbhubContext;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dbhub.server.tools.common.util.EasyCollectionUtils;

import org.springframework.stereotype.Service;

/**
 * @author moji
 * @version DataSourceCoreServiceImpl.java, v 0.1 2022年09月23日 15:51 moji Exp $
 * @date 2022/09/23
 */
@Service
public class DatabaseServiceImpl implements DatabaseService {

    @Override
    public ListResult<Database> queryAll(DatabaseQueryAllParam param) {
        List<String> databases = DbhubContext.getMetaSchema().databases();
        return ListResult.of(EasyCollectionUtils.toList(databases, name -> Database.builder().name(name).build()));
    }

    @Override
    public ListResult<Schema> querySchema(SchemaQueryParam param) {
        List<String> databases = DbhubContext.getMetaSchema().schemas(param.getDataBaseName());
        return ListResult.of(EasyCollectionUtils.toList(databases, name -> Schema.builder().name(name).build()));
    }

}
