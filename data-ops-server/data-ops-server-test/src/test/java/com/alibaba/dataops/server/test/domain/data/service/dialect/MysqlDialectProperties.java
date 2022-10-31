package com.alibaba.dataops.server.test.domain.data.service.dialect;

import java.util.Date;

import com.alibaba.dataops.server.domain.data.api.enums.DbTypeEnum;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
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
        return "jdbc:mysql://rm-8vb099vo8309mcngk.mysql.zhangbei.rds.aliyuncs.com:3306";
    }

    @Override
    public String getUsername() {
        return "grow";
    }

    @Override
    public String getPassword() {
        return "v5EdRurYac";
    }

    @Override
    public String getDatabaseName() {
        return "data_ops_test";
    }

    @Override
    public String getCrateTableSql(String tableName) {
        return "CREATE TABLE `" + tableName + "`\n"
            + "(\n"
            + "    `ID`     bigint PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT '主键自增',\n"
            + "    `DATE`   datetime(3)                          not null COMMENT '日期',\n"
            + "    `NUMBER` bigint COMMENT '长整型',\n"
            + "    `STRING` VARCHAR(100) default 'DATA' COMMENT '名字',\n"
            + "    INDEX " + tableName + "_IDX_DATE (DATE desc) comment '日期索引',\n"
            + "    unique " + tableName + "_UK_NUMBER (NUMBER) comment '唯一索引',\n"
            + "    INDEX " + tableName + "_IDX_NUMBER_STRING (NUMBER, DATE) comment '联合索引'\n"
            + ") COMMENT ='测试表';";
    }


    @Override
    public String getDropTableSql(String tableName) {
        return "drop table " + tableName + ";";
    }

    @Override
    public String getInsertSql(String tableName, Date date, Long number, String string) {
        return "INSERT INTO `" + tableName + "` (DATE,NUMBER,STRING) VALUES ('" + DateUtil.format(date,
            DatePattern.NORM_DATETIME_MS_FORMAT) + "','" + number + "','" + string + "');";
    }

    @Override
    public String getSelectSqlById(String tableName, Long id) {
        return "select *\n"
            + "from " + tableName + "\n"
            + "where `ID` = '" + id + "';";
    }
}
