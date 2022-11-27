package com.alibaba.dbhub.server.test.domain.data.service.dialect;

import java.util.Date;

import com.alibaba.dbhub.server.domain.data.api.enums.DbTypeEnum;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * mysql
 *
 * @author Jiaju Zhuang
 */
@Component
public class MysqlDialectProperties implements DialectProperties {

    @Override
    public DbTypeEnum getDbType() {
        return DbTypeEnum.MYSQL;
    }

    @Override
    public String getUrl() {
        return "jdbc:mysql://127.0.0.1:3306";
    }

    @Override
    public String getErrorUrl() {
        return "jdbc:mysql://error.rm-8vb099vo8309mcngk.mysql.zhangbei.rds.aliyuncs.com:3306";
    }

    @Override
    public String getUsername() {
        return "root";
    }

    @Override
    public String getPassword() {
        return "ali_dbhub";
    }

    @Override
    public String getDatabaseName() {
        return "ali_dbhub_test";
    }

    @Override
    public String getCrateTableSql(String tableName) {
        return "CREATE TABLE `" + tableName + "`\n"
            + "(\n"
            + "    `id`     bigint PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT '主键自增',\n"
            + "    `date`   datetime(3)                          not null COMMENT '日期',\n"
            + "    `number` bigint COMMENT '长整型',\n"
            + "    `string` VARCHAR(100) default 'DATA' COMMENT '名字',\n"
            + "    index " + tableName + "_idx_date (date desc) comment '日期索引',\n"
            + "    unique " + tableName + "_uk_number (number) comment '唯一索引',\n"
            + "    index " + tableName + "_idx_number_string (number, date) comment '联合索引'\n"
            + ") COMMENT ='测试表';";
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
        return StringUtils.toRootLowerCase(string);
    }
}
