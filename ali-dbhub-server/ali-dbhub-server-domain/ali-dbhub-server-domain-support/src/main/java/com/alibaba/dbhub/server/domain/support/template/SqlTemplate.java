package com.alibaba.dbhub.server.domain.support.template;

import java.util.List;

import com.alibaba.dbhub.server.domain.support.model.Sql;
import com.alibaba.dbhub.server.domain.support.operations.SqlOperations;
import com.alibaba.dbhub.server.domain.support.param.sql.SqlAnalyseParam;
import com.alibaba.dbhub.server.domain.support.sql.DbhubContext;
import com.alibaba.dbhub.server.domain.support.util.JdbcUtils;
import com.alibaba.dbhub.server.tools.common.util.EasyCollectionUtils;
import com.alibaba.druid.sql.parser.DbhubSQLParserUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * sql服务实现
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class SqlTemplate implements SqlOperations {

    @Override
    public List<Sql> analyse(SqlAnalyseParam param) {
        log.info("解析sql:{}", param.getSql());
        List<String> sqlList = DbhubSQLParserUtils.splitAndRemoveComment(param.getSql(),
            JdbcUtils.parse2DruidDbType(DbhubContext.getConnectInfo().getDbType()));
        return EasyCollectionUtils.toList(sqlList, sql -> Sql.builder().sql(sql).build());
    }

}
