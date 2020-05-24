/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.23 : Database - rt_admin
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rt_admin` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `rt_admin`;

/*Table structure for table `file_log` */

DROP TABLE IF EXISTS `file_log`;

CREATE TABLE `file_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(64) NOT NULL COMMENT '操作用户ip',
  `user_cert` varchar(64) DEFAULT NULL COMMENT '用户凭证',
  `type` tinyint(4) DEFAULT NULL COMMENT '操作类型，1：上传，2：下载',
  `result` tinyint(2) NOT NULL COMMENT '操作成功还是失败，1：成功， 2：失败',
  `fail_desc` varchar(256) DEFAULT NULL COMMENT '失败原因',
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_user` (`user_cert`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COMMENT='文件操作日志';

/*Table structure for table `system_log` */

DROP TABLE IF EXISTS `system_log`;

CREATE TABLE `system_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action_addr` varchar(64) NOT NULL COMMENT '用户行为路径，前端使用',
  `user_id` bigint(20) NOT NULL COMMENT '权限描述',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `result` tinyint(2) NOT NULL COMMENT '操作成功还是失败，1：成功， 2：失败',
  `fail_desc` varchar(256) DEFAULT NULL COMMENT '失败原因',
  `detail` text COMMENT '描述',
  `is_del` bigint(20) NOT NULL DEFAULT '0' COMMENT '0:表示没有删除，时间戳表示已经删除',
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `add_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '添加人',
  `update_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=509 DEFAULT CHARSET=utf8mb4 COMMENT='系统日志表';

/*Table structure for table `system_login_session` */

DROP TABLE IF EXISTS `system_login_session`;

CREATE TABLE `system_login_session` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `token` varchar(64) NOT NULL COMMENT 'token值',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `next_expire_time` bigint(20) NOT NULL COMMENT '下次过期时间',
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `add_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '添加人',
  `update_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_token` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `system_perm` */

DROP TABLE IF EXISTS `system_perm`;

CREATE TABLE `system_perm` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `limit_detail` varchar(5120) DEFAULT NULL COMMENT '前端控制展示的权限（后端不直接使用）',
  `api` varchar(5120) DEFAULT NULL COMMENT '后端控制api权限，如果是-1表示拥有所有权限',
  `is_del` bigint(20) NOT NULL DEFAULT '0' COMMENT '0:表示没有删除，时间戳表示已经删除',
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `add_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '添加人',
  `update_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=519 DEFAULT CHARSET=utf8mb4 COMMENT='系统权限表';

/*Table structure for table `system_role` */

DROP TABLE IF EXISTS `system_role`;

CREATE TABLE `system_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '权限名',
  `desc` varchar(128) DEFAULT NULL COMMENT '权限描述',
  `is_del` bigint(20) NOT NULL DEFAULT '0' COMMENT '0:表示没有删除，时间戳表示已经删除',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父角色id',
  `parent_chain` varchar(128) DEFAULT NULL COMMENT '父角色链',
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `add_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '添加人',
  `update_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

/*Table structure for table `system_role_perm` */

DROP TABLE IF EXISTS `system_role_perm`;

CREATE TABLE `system_role_perm` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '用户id',
  `perm_id` bigint(20) NOT NULL COMMENT '权限id',
  `is_del` bigint(20) NOT NULL DEFAULT '0' COMMENT '0:表示没有删除，时间戳表示已经删除',
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `add_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '添加人',
  `update_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_role_limit` (`role_id`,`perm_id`,`is_del`)
) ENGINE=InnoDB AUTO_INCREMENT=4222 DEFAULT CHARSET=utf8mb4 COMMENT='角色和权限的映射表';

/*Table structure for table `system_user` */

DROP TABLE IF EXISTS `system_user`;

CREATE TABLE `system_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL COMMENT '用戶名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(256) DEFAULT NULL COMMENT '头像',
  `salt` varchar(64) NOT NULL COMMENT '加盐',
  `parent_id` bigint(20) NOT NULL COMMENT '添加者的用户id,如果没有就是-1（只有最高权限是-1）',
  `is_del` bigint(20) unsigned DEFAULT '0' COMMENT '0:表示没有删除，时间戳表示已经删除',
  `signature` varchar(256) DEFAULT NULL COMMENT '签名',
  `desc` varchar(128) DEFAULT NULL COMMENT '用户的描述信息',
  `parent_chain` varchar(1024) NOT NULL COMMENT '父级用户链（中间使用-隔开）',
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `add_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '添加人',
  `update_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user` (`username`,`is_del`)
) ENGINE=InnoDB AUTO_INCREMENT=1239 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

/*Table structure for table `system_user_role` */

DROP TABLE IF EXISTS `system_user_role`;

CREATE TABLE `system_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '权限id',
  `is_del` bigint(20) NOT NULL DEFAULT '0' COMMENT '0:表示没有删除，时间戳表示已经删除',
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `add_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '添加人',
  `update_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_limit` (`user_id`,`role_id`,`is_del`)
) ENGINE=InnoDB AUTO_INCREMENT=1146 DEFAULT CHARSET=utf8mb4 COMMENT='用户和角色的映射表';

/*Table structure for table `system_user_stat` */

DROP TABLE IF EXISTS `system_user_stat`;

CREATE TABLE `system_user_stat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用戶id',
  `ip` varchar(32) NOT NULL COMMENT 'ip地址',
  `operate` int(8) NOT NULL COMMENT '用户操作类型,100001:刷新页面',
  `detail` varchar(512) DEFAULT NULL COMMENT '操作详情，json格式',
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `add_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '添加人',
  `update_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户行为统计';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
