package com.alibaba.dbhub.server.start.test.druid;

import java.util.List;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.PagerUtils;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLDataTypeImpl;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLNotNullConstraint;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.expr.MySqlCharExpr;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;

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
    public void test55() {
        List<SQLStatement> sqlStatements = SQLUtils.parseStatements("create table test(id int) comment 'xx';",
            DbType.mysql);
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
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(sql, DbType.mysql);
        SQLSelectStatement sqlSelectStatement = (SQLSelectStatement)sqlStatement;
        log.info("test{}", sqlSelectStatement);
    }

    @Test
    public void test56() {
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(
            "create table test(id int  ,name varchar(32) not null default 'xx' comment 'name',nu int auto_increment,index ds(id) ,primary key (id,nu)) "
                + "comment 'xx';",
            DbType.mysql);
        log.info("解析sql:{}", sqlStatement);
    }

    @Test
    public void test4() {
        MySqlCreateTableStatement x = new MySqlCreateTableStatement();
        x.setTableName("ff");
        x.setComment(new MySqlCharExpr(null));
        SQLColumnDefinition c = new SQLColumnDefinition();
        x.addColumn(c);

        c.setName("name");
        SQLDataTypeImpl sqlDataType=new SQLDataTypeImpl();
        sqlDataType.setName("varchar(32)");
        c.setDataType(sqlDataType);
        c.addConstraint(new SQLNotNullConstraint());
        c.setComment(new MySqlCharExpr("xname"));
        //x.addColumn();
        log.info(x.toString());
    }

    @Test
    public void testreaname() {
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(
            "rename table data_ops_table_test_1667268894825 to data_ops_table_test_166726889482511;",
            DbType.mysql);
        log.info("解析sql:{}", sqlStatement);
    }

    @Test
    public void testcomment() {
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(
            "\n"
                + "alter table data_ops_table_test_166726889482511\n"
                + "    comment '测试表33';",
            DbType.mysql);
        log.info("解析sql:{}", sqlStatement);
    }


    @Test
    public void dropindex() {
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(
            "drop index data_ops_table_test_1667268894825_idx_date on data_ops_table_test_1667268894825;",
            DbType.mysql);
        log.info("解析sql:{}", sqlStatement);
    }

    @Test
    public void createindex() {
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(
            "\n"
                + "create index data_ops_table_test_1667268894825_idx_date\n"
                + "    on data_ops_table_test_1667268894825 (date desc, id asc)\n"
                + "    comment '日期索引';",
            DbType.mysql);
        log.info("解析sql:{}", sqlStatement);
    }


    @Test
    public void addColumn() {
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(
            "alter table data_ops_table_test_1667268894825\n"
                + "    add column_5 int default de null;",
            DbType.mysql);
        log.info("解析sql:{}", sqlStatement);
    }
    @Test
    public void change() {
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(
            "alter table data_ops_table_test_1667268894825\n"
                + "    change number number1 bigint null comment '长整型';",
            DbType.mysql);
        log.info("解析sql:{}", sqlStatement);
    }

    @Test
    public void modify() {
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(
            "alter table data_ops_table_test_1667268894825\n"
                + "    modify number1 bigint null comment '长整型';",
            DbType.mysql);
        log.info("解析sql:{}", sqlStatement);
    }

    @Test
    public void dropColumn() {
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(
            "alter table data_ops_table_test_1667268894825\n"
                + "    drop column string;",
            DbType.mysql);
        log.info("解析sql:{}", sqlStatement);
    }

    @Test
    public void dropPrimaryKey() {
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(
            "ALTER TABLE `ali_dbhub_test`.`data_ops_table_test_1671368857363` \n"
                + "DROP PRIMARY KEY,\n"
                + "ADD PRIMARY KEY (`date`) USING BTREE;",
            DbType.mysql);
        log.info("解析sql:{}", sqlStatement);
    }
}
