CREATE TABLE IF NOT EXISTS `test69`
(
    `id`     bigint PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT '主键',
    `name`   VARCHAR(100) COMMENT '名字',
     `date`   datetime  COMMENT '时间',
     `number`   int  COMMENT '数字'
)  ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='数据源连接表';
