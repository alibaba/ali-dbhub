package com.alibaba.dbhub.server.domain.support.operations;

import java.util.List;

import com.alibaba.dbhub.server.domain.support.model.Database;
import com.alibaba.dbhub.server.domain.support.model.Schema;
import com.alibaba.dbhub.server.domain.support.param.database.DatabaseQueryAllParam;
import com.alibaba.dbhub.server.domain.support.param.database.SchemaQueryParam;

/**
 * 数据库连接源服务
 *
 * @author Jiaju Zhuang
 */
public interface DatabaseOperations {

    /**
     * 查询全部数据库
     *
     * @param param
     * @return
     */
    List<Database> queryAll(DatabaseQueryAllParam param);

    /**
     * 查询数据库下的SCHEMA
     * @param param
     * @return
     */
    List<Schema> querySchema(SchemaQueryParam param);

}
