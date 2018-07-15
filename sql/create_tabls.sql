CREATE TABLE `admin_user`(
	`user_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'admin用户号' ,
	`role_id` INT(11) COMMENT '角色号',
	`real_name` CHAR(20) NOT NULL DEFAULT '' COMMENT 'admin用户真实名字，可以用来记录日志' ,
	`nick_name` VARCHAR(255)  DEFAULT '' COMMENT 'admin用户昵称，可以用来显示' ,
	`ip` CHAR(30) DEFAULT '' COMMENT '记录登陆用户ip地址' ,
	`avatar` VARCHAR(255) DEFAULT '' COMMENT '用户头像' ,
	`create_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	PRIMARY KEY(`user_id`)
) ENGINE = INNODB AUTO_INCREMENT = 10000 DEFAULT CHARSET = utf8 COMMENT = 'admin用户信息表';


CREATE TABLE `admin_roles`(
	`role_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '角色号' ,
	`name` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '角色名称' ,
	`remark` TEXT COMMENT '角色备注信息' ,
	`apis` MEDIUMTEXT COMMENT '后端api限制' ,
	`views` MEDIUMTEXT COMMENT '前端页面限制' ,
	`create_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
	`update_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间' ,
	PRIMARY KEY(`role_id`)
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8 COMMENT = 'admin用户角色表';


CREATE TABLE `login_user` (
    `user_id` INT(11) NOT NULL COMMENT '对应 admin_user 的 user_id',
    `username` CHAR(30) NOT NULL COMMENT 'admin用户登陆账号',
    `password` CHAR(30) NOT NULL COMMENT 'admin用户密码',
    PRIMARY KEY (`user_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT = 'admin用户的登陆表';