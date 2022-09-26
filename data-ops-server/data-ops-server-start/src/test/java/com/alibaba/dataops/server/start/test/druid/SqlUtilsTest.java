package com.alibaba.dataops.server.start.test.druid;

import java.util.List;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;

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
}
