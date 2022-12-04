package com.alibaba.dataops.server.domain.support.operations;

import java.util.List;

import com.alibaba.dataops.server.domain.support.model.Sql;
import com.alibaba.dataops.server.domain.support.param.sql.SqlAnalyseParam;

/**
 * Sql服务
 *
 * @author Jiaju Zhuang
 */
public interface SqlOperations {

    /**
     * 将一个复杂的sql 解析成多个简单的sql
     *
     * @param param
     * @return
     */
    List<Sql> analyse(SqlAnalyseParam param);
}
