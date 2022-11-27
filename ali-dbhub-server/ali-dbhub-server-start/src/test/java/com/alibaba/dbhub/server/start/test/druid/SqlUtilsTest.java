package com.alibaba.dbhub.server.start.test.druid;

import java.util.List;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.PagerUtils;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class SqlUtilsTest {

    @Test
    public void test() {
        List<SQLStatement> sqlStatements = SQLUtils.parseStatements("select 1 from test;", DbType.mysql);
        log.info("解析sql:{}", sqlStatements);
        sqlStatements = SQLUtils.parseStatements("use xxx;select 1 from test;explain select 1 from test", DbType.mysql);
        log.info("解析sql:{}", sqlStatements);
        sqlStatements = SQLUtils.parseStatements("select 1 from1 test", DbType.mysql);
        log.info("解析sql:{}", sqlStatements);
    }

    @Test
    public void test2() {
        String sql = "select * from test";
        log.info("分页:{} ----- {}  ---  {}", PagerUtils.count(sql, DbType.mysql),
            PagerUtils.limit(sql, DbType.mysql, 1000, 999),
            PagerUtils.limit(sql, DbType.mysql, 1000, 999, true));
        sql = "select * from test where id=1 limit 100;";
        log.info("分页:{} ----- {}  ---  {}", PagerUtils.count(sql, DbType.mysql),
            PagerUtils.limit(sql, DbType.mysql, 1000, 999),
            PagerUtils.limit(sql, DbType.mysql, 1000, 999, true));

        sql = "select * from test where  id=1 limit 100,10;";
        log.info("分页:{} ----- {}  ---  {}", PagerUtils.count(sql, DbType.mysql),
            PagerUtils.limit(sql, DbType.mysql, 1000, 999),
            PagerUtils.limit(sql, DbType.mysql, 1000, 999, true));

        sql = "select * from test where  id=1 limit 100,10;";
        log.info("分页:{} ----- {}  ---  {}", PagerUtils.count(sql, DbType.mysql),
            PagerUtils.limit(sql, DbType.mysql, 2, 2),
            PagerUtils.limit(sql, DbType.mysql, 2, 2, true));

        sql = "select * from test  union select * from test2";
        log.info("分页:{} ----- {}  ---  {}", PagerUtils.count(sql, DbType.mysql),
            PagerUtils.limit(sql, DbType.mysql, 2, 2),
            PagerUtils.limit(sql, DbType.mysql, 2, 2, true));

        sql = "select * from test  union select * from test2";
        SQLStatement sqlStatement=  SQLUtils.parseSingleStatement(sql,DbType.mysql);
        SQLSelectStatement sqlSelectStatement= (SQLSelectStatement)sqlStatement;
        log.info("test{}",sqlSelectStatement);
    }
}
