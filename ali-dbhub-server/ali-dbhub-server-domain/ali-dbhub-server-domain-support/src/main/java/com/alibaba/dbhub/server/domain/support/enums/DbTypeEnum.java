package com.alibaba.dbhub.server.domain.support.enums;

import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;
import com.alibaba.dbhub.server.domain.support.dialect.common.model.SpiExample;
import com.alibaba.dbhub.server.domain.support.dialect.h2.H2MetaSchemaSupport;
import com.alibaba.dbhub.server.domain.support.dialect.mysql.MysqlMetaSchemaSupport;
import com.alibaba.dbhub.server.domain.support.dialect.oracle.OracleMetaSchemaSupport;
import com.alibaba.dbhub.server.domain.support.dialect.postgresql.PostgresqlMetaSchemaSupport;
import com.alibaba.dbhub.server.domain.support.dialect.sqlserver.SqlServerMetaSchemaSupport;
import com.alibaba.dbhub.server.tools.base.enums.BaseEnum;

import lombok.Getter;

import static com.alibaba.dbhub.server.domain.support.dialect.common.SQLKeyConst.H2_ALTER_TABLE_SIMPLE;
import static com.alibaba.dbhub.server.domain.support.dialect.common.SQLKeyConst.H2_CREATE_TABLE_SIMPLE;
import static com.alibaba.dbhub.server.domain.support.dialect.common.SQLKeyConst.MYSQL_ALTER_TABLE_SIMPLE;
import static com.alibaba.dbhub.server.domain.support.dialect.common.SQLKeyConst.MYSQL_CREATE_TABLE_SIMPLE;
import static com.alibaba.dbhub.server.domain.support.dialect.common.SQLKeyConst.ORACLE_ALTER_TABLE_SIMPLE;
import static com.alibaba.dbhub.server.domain.support.dialect.common.SQLKeyConst.ORACLE_CREATE_TABLE_SIMPLE;
import static com.alibaba.dbhub.server.domain.support.dialect.common.SQLKeyConst.PG_ALTER_TABLE_SIMPLE;
import static com.alibaba.dbhub.server.domain.support.dialect.common.SQLKeyConst.PG_CREATE_TABLE_SIMPLE;

/**
 * 数据类型
 *
 * @author Jiaju Zhuang
 */
@Getter
public enum DbTypeEnum implements BaseEnum<String> {
    /**
     * MySQL
     */
    MYSQL("MySQL", "com.mysql.cj.jdbc.Driver"),

    /**
     * PostgreSQL
     */
    POSTGRESQL("PostgreSQL", "org.postgresql.Driver"),

    /**
     * Oracle
     */
    ORACLE("Oracle", "oracle.jdbc.driver.OracleDriver"),

    /**
     * SQLServer
     */
    SQLSERVER("SQLServer", "com.microsoft.sqlserver.jdbc.SQLServerDriver"),

    /**
     * SQLite
     */
    SQLITE("SQLite", "org.sqlite.JDBC"),

    /**
     * H2
     */
    H2("H2", "org.h2.Driver"),

    ADB_POSTGRESQL("PostgreSQL", "org.postgresql.Driver"),
    ;

    final String description;
    final String className;

    DbTypeEnum(String description, String className) {
        this.description = description;
        this.className = className;
    }

    /**
     * 通过名称获取枚举
     *
     * @param name
     * @return
     */
    public static DbTypeEnum getByName(String name) {
        for (DbTypeEnum dbTypeEnum : DbTypeEnum.values()) {
            if (dbTypeEnum.name().equals(name)) {
                return dbTypeEnum;
            }
        }
        return null;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    public MetaSchema metaSchema() {
        MetaSchema metaSchema = null;
        switch (this) {
            case H2:
                metaSchema = new H2MetaSchemaSupport();
                break;
            case MYSQL:
                metaSchema = new MysqlMetaSchemaSupport();
                break;
            case POSTGRESQL:
                metaSchema = new PostgresqlMetaSchemaSupport();
                break;
            case ORACLE:
                metaSchema = new OracleMetaSchemaSupport();
                break;
            case SQLSERVER:
                metaSchema = new SqlServerMetaSchemaSupport();
            default:
        }
        return metaSchema;
    }

    public SpiExample example() {
        SpiExample SpiExample = null;
        switch (this) {
            case H2:
                SpiExample = SpiExample.builder().createTable(H2_CREATE_TABLE_SIMPLE).alterTable(H2_ALTER_TABLE_SIMPLE)
                    .build();
                break;
            case MYSQL:
                SpiExample = SpiExample.builder().createTable(MYSQL_CREATE_TABLE_SIMPLE).alterTable(
                    MYSQL_ALTER_TABLE_SIMPLE).build();
                break;
            case POSTGRESQL:
                SpiExample = SpiExample.builder().createTable(PG_CREATE_TABLE_SIMPLE).alterTable(PG_ALTER_TABLE_SIMPLE)
                    .build();
                break;
            case ORACLE:
                SpiExample = SpiExample.builder().createTable(ORACLE_CREATE_TABLE_SIMPLE).alterTable(
                    ORACLE_ALTER_TABLE_SIMPLE).build();
                break;
            case SQLSERVER:
                SpiExample = SpiExample.builder().createTable(ORACLE_CREATE_TABLE_SIMPLE).alterTable(
                    ORACLE_ALTER_TABLE_SIMPLE).build();
            default:
        }
        return SpiExample;
    }

}
