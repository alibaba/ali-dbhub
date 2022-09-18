-- 表结构初始化sql
CREATE TABLE IF NOT EXISTS `test`
(
    `id`     bigint PRIMARY KEY AUTOINCREMENT NOT NULL,
    `name`   VARCHAR(100) NOT NULL,
     `date`   datetime NOT NULL,
     `number`   integer NOT NULL
);