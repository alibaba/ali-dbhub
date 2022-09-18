-- 表结构初始化sql
CREATE TABLE  IF NOT EXISTS `test`
(
    `id`     bigint PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `name`   VARCHAR(100) ,
     `date`   datetime ,
     `number`  integer   COMMENT '数据库类型'
);