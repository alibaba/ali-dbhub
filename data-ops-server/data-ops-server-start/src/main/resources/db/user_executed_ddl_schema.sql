/******************************************/
/*   TableName = user_executed_ddl   */
/******************************************/
CREATE TABLE IF NOT EXISTS `user_executed_ddl` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `data_source_id` bigint(20) unsigned NOT NULL COMMENT '数据源连接ID',
    `database_name` varchar(128) DEFAULT NULL COMMENT 'db名称',
    `type` varchar(32) NOT NULL COMMENT '数据库类型',
    `ddl` text DEFAULT NULL COMMENT 'ddl内容',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='我的执行记录表'
;
