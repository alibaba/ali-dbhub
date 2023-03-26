package com.alibaba.dbhub.server.domain.api.service;

import java.util.List;

import com.alibaba.dbhub.server.domain.support.model.Database;
import com.alibaba.dbhub.server.domain.support.model.Schema;
import com.alibaba.dbhub.server.domain.support.param.database.DatabaseQueryAllParam;
import com.alibaba.dbhub.server.domain.support.param.database.SchemaQueryParam;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;

/**
 * 数据源管理服务
 *
 * @author moji
 * @version DataSourceCoreService.java, v 0.1 2022年09月23日 15:22 moji Exp $
 * @date 2022/09/23
 */
public interface DatabaseService {

    /**
     * 查询数据源下的所有database
     *
     * @param param
     * @return
     */
    ListResult<Database> queryAll(DatabaseQueryAllParam param);

    /**
     * 查询某个database下的schema
     * @param param
     * @return
     */
    ListResult<Schema> querySchema(SchemaQueryParam param);
}
