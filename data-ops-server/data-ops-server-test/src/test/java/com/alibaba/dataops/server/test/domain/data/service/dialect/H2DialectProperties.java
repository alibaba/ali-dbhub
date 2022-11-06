package com.alibaba.dataops.server.test.domain.data.service.dialect;

import java.util.Date;

import com.alibaba.dataops.server.domain.data.api.enums.DbTypeEnum;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * h2
 *
 * @author Jiaju Zhuang
 */
@Component
public class H2DialectProperties implements DialectProperties {

    @Override
    public DbTypeEnum getDbType() {
        return DbTypeEnum.H2;
    }

    @Override
    public String getUrl() {
        return "jdbc:h2:mem:data-ops-test;MODE=MYSQL";
    }

    @Override
    public String getErrorUrl() {
        return "jdbc:h2:tcp://error:8084/error";
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getDatabaseName() {
        return "PUBLIC";
    }

    @Override
    public String getCrateTableSql(String tableName) {
        String sql = "CREATE TABLE `" + tableName + "`\n"
            + "(\n"
            + "    `id`     bigint PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT '主键自增',\n"
            + "    `date`   datetime                          not null COMMENT '日期',\n"
            + "    `number` bigint COMMENT '长整型',\n"
            + "    `string` VARCHAR(100) default 'DATA' COMMENT '名字'"
            + ");\n";
        sql += "comment on table " + tableName + " is '测试表';\n";
        sql += "create index " + tableName + "_idx_date   on " + tableName + "(DATE desc);\n";
        sql += "comment on index " + tableName + "_idx_date is '日期索引';\n";
        sql += "create unique index " + tableName + "_uk_number   on " + tableName + "(NUMBER);\n";
        sql += "comment on index " + tableName + "_uk_number is '唯一索引';\n";
        sql += "create index " + tableName + "_idx_number_string   on " + tableName + "(NUMBER, DATE);\n";
        sql += "comment on index " + tableName + "_idx_number_string is '联合索引';\n";
        return sql;
    }

    @Override
    public String getDropTableSql(String tableName) {
        return "drop table " + tableName + ";";
    }

    @Override
    public String getInsertSql(String tableName, Date date, Long number, String string) {
        return "INSERT INTO `" + tableName + "` (date,number,string) VALUES ('" + DateUtil.format(date,
            DatePattern.NORM_DATETIME_MS_FORMAT) + "','" + number + "','" + string + "');";
    }

    @Override
    public String getSelectSqlById(String tableName, Long id) {
        return "select *\n"
            + "from " + tableName + "\n"
            + "where `id` = '" + id + "';";
    }

    @Override
    public String getTableNotFoundSqlById(String tableName) {
        return "select *\n"
            + "from " + tableName + "_notfound;";
    }

    @Override
    public String toCase(String string) {
        return StringUtils.toRootUpperCase(string);
    }
}
