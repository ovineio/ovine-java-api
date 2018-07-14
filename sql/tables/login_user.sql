CREATE TABLE `login_user` (
    `user_id` INT(11) NOT NULL COMMENT '对应 admin_user 的 user_id',
    `username` CHAR(30) NOT NULL COMMENT 'admin用户登陆账号',
    `password` CHAR(30) NOT NULL COMMENT 'admin用户密码',
    PRIMARY KEY (`user_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT = 'admin用户的登陆表';