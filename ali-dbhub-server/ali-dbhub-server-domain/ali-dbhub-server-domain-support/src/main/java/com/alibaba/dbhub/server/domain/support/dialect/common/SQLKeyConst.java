/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.common;

/**
 * @author jipengfei
 * @version : SQLConst.java, v 0.1 2022年12月08日 15:08 jipengfei Exp $
 */
public class SQLKeyConst {
    public static final String PG_CREATE_TABLE_SIMPLE = "create table if not exists main.test_table( column_1 serial, column_2 varchar default 'dd'::character varying, column_3 bigserial, column_4 integer default 100, column_5 varchar not null constraint test_table_pk primary key, column_6 varchar, column_7 integer); comment on table main.test_table is '测试表'; comment on column main.test_table.column_6 is '设置备注'; alter table main.test_table owner to ali_dbhub_test; create index if not exists test_table_column_2_index on main.test_table (column_2); create unique index if not exists test_table_column_2_uindex on main.test_table (column_2); comment on index main.test_table_column_2_uindex is 'add'; ";
    public static final String PG_ALTER_TABLE_SIMPLE = "alter table main.test_table rename column column_1 to column_001; alter table main.test_table add column_8 integer not null; create index test_table_column_8_index on main.test_table(column_8); ";

    public static final String MYSQL_CREATE_TABLE_SIMPLE = "CREATE TABLE `test`( `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键', `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间', `gmt_modified` datetime NULL DEFAULT CURRENT_TIMESTAMP ONUPDATE CURRENT_TIMESTAMP COMMENT '修改时间', `date` datetime NULL COMMENT '日期', `string` varchar(128) NOT NULL DEFAULT 'Test' COMMENT '字符串', PRIMARY KEY (`id`), KEY `idx_string` (`string`)) DEFAULT CHARACTER SET=utf8mb4 COMMENT='测试表'; ";
    public static final String MYSQL_ALTER_TABLE_SIMPLE = "ALTER TABLE `test` ADD COLUMN `number` bigint unsigned NULL COMMENT '数字'; ALTER TABLE `test` ADD UNIQUE INDEX uk_number(number); ALTER TABLE `test` DROP COLUMN `number`; ";


    public static final String H2_CREATE_TABLE_SIMPLE = "CREATE TABLE `test`( `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键', `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间', `gmt_modified` datetime NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '修改时间', `date` datetime NULL COMMENT '日期', `string` varchar(128) NOT NULL DEFAULT 'Test' COMMENT '字符串', PRIMARY KEY (`id`));";

    public static final String H2_ALTER_TABLE_SIMPLE = "ALTER TABLE `test` ADD COLUMN `number` bigint NULL COMMENT '数字'; CREATE UNIQUE INDEX uk_number ON `test`(`number`); ALTER TABLE `test` DROP COLUMN `number`;";


    public static final String ORACLE_CREATE_TABLE_SIMPLE = "create table if not exists main.test_table( column_1 serial, column_2 varchar default 'dd'::character varying, column_3 bigserial, column_4 integer default 100, column_5 varchar not null constraint test_table_pk primary key, column_6 varchar, column_7 integer); comment on table main.test_table is '测试表'; comment on column main.test_table.column_6 is '设置备注'; alter table main.test_table owner to ali_dbhub_test; create index if not exists test_table_column_2_index on main.test_table (column_2); create unique index if not exists test_table_column_2_uindex on main.test_table (column_2); comment on index main.test_table_column_2_uindex is 'add'; ";
    public static final String ORACLE_ALTER_TABLE_SIMPLE = "alter table main.test_table rename column column_1 to column_001; alter table main.test_table add column_8 integer not null; create index test_table_column_8_index on main.test_table(column_8); ";

}