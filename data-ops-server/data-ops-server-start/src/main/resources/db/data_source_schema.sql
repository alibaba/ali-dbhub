/******************************************/
/*   TableName = data_source   */
/******************************************/
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
