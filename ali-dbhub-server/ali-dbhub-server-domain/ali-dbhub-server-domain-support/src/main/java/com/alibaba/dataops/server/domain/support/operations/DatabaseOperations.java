package com.alibaba.dataops.server.domain.support.operations;

import java.util.List;

import com.alibaba.dataops.server.domain.support.model.Database;
import com.alibaba.dataops.server.domain.support.param.database.DatabaseQueryAllParam;

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

}
