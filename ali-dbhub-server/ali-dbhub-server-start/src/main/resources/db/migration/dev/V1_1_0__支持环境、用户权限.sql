CREATE TABLE IF NOT EXISTS `environment`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `gmt_create`       datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`     datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user_id`   bigint(20) unsigned NOT NULL COMMENT '创建人用户id',
    `modified_user_id` bigint(20) unsigned NOT NULL COMMENT '修改人用户id',
    `name`             varchar(128)                 DEFAULT NOT NULL COMMENT '环境名称',
    `short_name`       varchar(128)                 DEFAULT NULL COMMENT '环境缩写',
    `style`            varchar(32)                  DEFAULT NULL COMMENT '样式类型',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据库连接环境'
;

INSERT INTO ENVIRONMENT
(GMT_CREATE, GMT_MODIFIED, CREATE_USER_ID, MODIFIED_USER_ID, NAME, SHORT_NAME, "STYLE")
VALUES(CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, '线上环境', '线上', 'RED');
INSERT INTO ENVIRONMENT
(GMT_CREATE, GMT_MODIFIED, CREATE_USER_ID, MODIFIED_USER_ID, NAME, SHORT_NAME, "STYLE")
VALUES(CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, '测试环境', '测试', 'GREEN');

ALTER TABLE `data_source`
    ADD COLUMN `environment_id` bigint(20) unsigned NULL COMMENT '环境id';

CREATE TABLE IF NOT EXISTS `team`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `gmt_create`       datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`     datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user_id`   bigint(20) unsigned NOT NULL COMMENT '创建人用户id',
    `modified_user_id` bigint(20) unsigned NOT NULL COMMENT '修改人用户id',
    `code`             varchar(128)                 DEFAULT NOT NULL COMMENT '团队编码',
    `name`             varchar(512)                 DEFAULT NULL COMMENT '团队名称',
    `description`      text                         DEFAULT NULL COMMENT '团队描述',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='团队'
;

CREATE TABLE IF NOT EXISTS `team_permission`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `gmt_create`       datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`     datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user_id`   bigint(20) unsigned NOT NULL COMMENT '创建人用户id',
    `modified_user_id` bigint(20) unsigned NOT NULL COMMENT '修改人用户id',
    `team_id`          bigint(20) unsigned NOT NULL COMMENT '团队id',
    `permission`       varchar(32)         NOT NULL COMMENT '权限名称',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='团队权限表'
;

CREATE TABLE IF NOT EXISTS `dbhub_user_team`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `gmt_create`       datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`     datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user_id`   bigint(20) unsigned NOT NULL COMMENT '创建人用户id',
    `modified_user_id` bigint(20) unsigned NOT NULL COMMENT '修改人用户id',
    `dbhub_user_id`    bigint(20) unsigned NOT NULL COMMENT '用户id',
    `team_id`          bigint(20) unsigned NOT NULL COMMENT '团队id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户团队表'
;

CREATE TABLE IF NOT EXISTS `data_source_access`
(
    `id`                 bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `gmt_create`         datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`       datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user_id`     bigint(20) unsigned NOT NULL COMMENT '创建人用户id',
    `modified_user_id`   bigint(20) unsigned NOT NULL COMMENT '修改人用户id',
    `data_source_id`     bigint(20) unsigned NOT NULL COMMENT '环境id',
    `access_object_type` varchar(32)         NOT NULL COMMENT '授权类型',
    `access_object_id`   bigint(20) unsigned NOT NULL COMMENT '授权id,根据类型区分是用户还是团队',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据源授权'
;