-- 表结构初始化sql
CREATE TABLE IF NOT EXISTS `test`
(
    `id`     bigint PRIMARY KEY AUTOINCREMENT NOT NULL,
    `name`   VARCHAR(100) NOT NULL,
     `date`   datetime NOT NULL,
     `number`   integer NOT NULL
);

CREATE TABLE IF NOT EXISTS `data_source` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `gmt_create` datetime NOT NULL COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL COMMENT '修改时间',
    `alias` varchar(128) DEFAULT NULL COMMENT '别名',
    `url` varchar(1024) DEFAULT NULL COMMENT '连接地址',
    `user` varchar(128) DEFAULT NULL COMMENT '用户名',
    `password` varchar(256) DEFAULT NULL COMMENT '密码',
    `type` varchar(32) DEFAULT NULL COMMENT '数据库类型',
    `env_type` varchar(32) DEFAULT NULL COMMENT '环境类型',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='数据源连接表'
;

CREATE TABLE IF NOT EXISTS `user_executed_ddl` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `data_source_id` bigint(20) unsigned NOT NULL COMMENT '数据源连接ID',
    `data_base_name` vachar(128) DEFAULT NULL COMMENT 'db名称',
    `type` varchar(32) NOT NULL COMMENT '数据库类型',
    `ddl` text DEFAULT NULL COMMENT 'ddl内容',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='我的执行记录表'
;

CREATE TABLE IF NOT EXISTS `user_saved_ddl` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `data_source_id` bigint(20) unsigned NOT NULL COMMENT '数据源连接ID',
    `data_base_name` vachar(128) DEFAULT NULL COMMENT 'db名称',
    `name` varchar(128) DEFAULT NULL COMMENT '保存名称',
    `type` varchar(32) NOT NULL COMMENT '数据库类型',
    `ddl` text DEFAULT NULL COMMENT 'ddl内容',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='我的保存表'
;
