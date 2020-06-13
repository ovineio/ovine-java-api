/*
Navicat MySQL Data Transfer

Source Server         : mine-开发
Source Server Version : 50729
Source Database       : rt_admin

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2020-06-13 16:34:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(1000) NOT NULL COMMENT '配置内容',
  `desc` varchar(30) NOT NULL COMMENT '描述',
  `is_del` bigint(20) NOT NULL DEFAULT '0' COMMENT '0:表示没有删除，时间戳表示已经删除',
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `add_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '添加人',
  `update_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_user_limit` (`is_del`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='配置表';

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `config` VALUES ('1', '测试配置添加1', '这只是一个测试', '0', '2020-05-30 21:27:33', '2020-05-30 21:27:33', '1221', '0');
INSERT INTO `config` VALUES ('2', '1', '1', '20200530213844', '2020-05-30 21:38:14', '2020-05-30 21:38:44', '1221', '0');
INSERT INTO `config` VALUES ('5', '测试配置添加3', '这只是一个测试', '0', '2020-05-30 21:36:31', '2020-06-06 13:33:21', '1221', '1221')
INSERT INTO `config` VALUES ('14', '测试配置添加10', '这只是一个测试', '0', '2020-05-30 21:37:09', '2020-05-30 21:37:09', '1221', '0');

-- ----------------------------
-- Table structure for document
-- ----------------------------
DROP TABLE IF EXISTS `document`;
CREATE TABLE `document` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(30) NOT NULL COMMENT '标题',
  `content` varchar(200) NOT NULL COMMENT '内容',
  `desc` varchar(1000) NOT NULL COMMENT '描述',
  `is_del` bigint(20) NOT NULL DEFAULT '0' COMMENT '0:表示没有删除，时间戳表示已经删除',
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `add_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '添加人',
  `update_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_user_limit` (`is_del`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='文档表';

-- ----------------------------
-- Records of document
-- ----------------------------
INSERT INTO `document` VALUES ('1', '标题', '测试配置添加10', '这只是一个测试', '0', '2020-05-30 22:44:12', '2020-05-30 22:45:17', '1221', '0');
INSERT INTO `document` VALUES ('2', '不懂', '测试配置添加3', '这只是一个测试', '0', '2020-05-30 22:44:31', '2020-05-30 22:45:18', '1221', '1221');
INSERT INTO `document` VALUES ('3', '标题', '测试配置添加10', '这只是一个测试', '20200530225425', '2020-05-30 22:44:33', '2020-05-30 22:54:25', '1221', '0');
INSERT INTO `document` VALUES ('4', '标题323', '测试配置添加10', '这只是一个测试1', '0', '2020-05-30 22:44:37', '2020-06-03 20:42:35', '1221', '0');
INSERT INTO `document` VALUES ('5', '不懂11', '测试配置添加31', '这只是一个测试11', '0', '2020-05-30 22:46:00', '2020-06-03 13:08:35', '1221', '1221');
INSERT INTO `document` VALUES ('6', '标题4', '测试配置添加10', '这只是一个测试', '0', '2020-06-01 22:17:01', '2020-06-01 22:17:01', '1221', '0');
INSERT INTO `document` VALUES ('7', '标题323', '测试配置添加10', '这只是一个测试', '0', '2020-06-03 19:31:53', '2020-06-03 19:31:53', '1221', '0');

-- ----------------------------
-- Table structure for file_log
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8mb4 COMMENT='文件操作日志';

-- ----------------------------
-- Records of file_log
-- ----------------------------
INSERT INTO `file_log` VALUES ('108', '127.0.0.1', '1221', '1', '2', 'java.io.FileNotFoundException: F:\\deployer\\file\\file-39052378112 (系统找不到指定的文件。)', null, '2020-06-06 12:12:06');
INSERT INTO `file_log` VALUES ('109', '127.0.0.1', '1221', '1', '1', null, null, '2020-06-06 12:12:28');
INSERT INTO `file_log` VALUES ('110', '127.0.0.1', null, '2', '1', null, null, '2020-06-06 12:13:41');
INSERT INTO `file_log` VALUES ('111', '127.0.0.1', null, '2', '1', null, null, '2020-06-06 12:13:52');
INSERT INTO `file_log` VALUES ('112', '127.0.0.1', null, '2', '1', null, null, '2020-06-06 12:53:15');
INSERT INTO `file_log` VALUES ('113', '127.0.0.1', null, '2', '1', null, null, '2020-06-06 12:54:06');
INSERT INTO `file_log` VALUES ('114', '127.0.0.1', null, '2', '1', null, null, '2020-06-06 12:59:05');
INSERT INTO `file_log` VALUES ('115', '127.0.0.1', null, '2', '1', null, null, '2020-06-06 13:02:07');
INSERT INTO `file_log` VALUES ('116', '127.0.0.1', null, '2', '1', null, null, '2020-06-06 13:04:29');
INSERT INTO `file_log` VALUES ('117', '127.0.0.1', null, '2', '1', null, null, '2020-06-06 13:31:41');
INSERT INTO `file_log` VALUES ('118', '127.0.0.1', null, '2', '1', null, null, '2020-06-06 13:33:23');
INSERT INTO `file_log` VALUES ('119', '127.0.0.1', '1221', '1', '1', null, null, '2020-06-06 16:06:02');
INSERT INTO `file_log` VALUES ('120', '127.0.0.1', '1221', '1', '1', null, '2020-06-06 18:02:08', '2020-06-06 18:02:08');
INSERT INTO `file_log` VALUES ('121', '127.0.0.1', null, '2', '1', null, '2020-06-06 18:02:32', '2020-06-06 18:02:32');
INSERT INTO `file_log` VALUES ('122', '127.0.0.1', null, '2', '1', null, '2020-06-06 18:04:03', '2020-06-06 18:04:03');
INSERT INTO `file_log` VALUES ('123', '127.0.0.1', null, '2', '1', null, '2020-06-06 18:06:19', '2020-06-06 18:06:19');
INSERT INTO `file_log` VALUES ('124', '127.0.0.1', null, '2', '2', 'com.igroupes.rtadmin.file.exception.FileStoreException$FileStoreKeyException: key is invalid', '2020-06-06 18:08:00', '2020-06-06 18:08:00');

-- ----------------------------
-- Table structure for system_log
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=603 DEFAULT CHARSET=utf8mb4 COMMENT='系统日志表';

-- ----------------------------
-- Records of system_log
-- ----------------------------
INSERT INTO `system_log` VALUES ('522', '', '1221', '超级管理员', '2', '内容长度不能超过30;', '{\"_request_method\":\"POST\",\"content\":\"我的11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111\",\"_request_path\":\"/config/item\",\"desc\":\"123\"}', '0', '2020-05-30 21:23:37', '2020-05-30 21:23:37', '0', '0');
INSERT INTO `system_log` VALUES ('523', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"content\":\"测试配置添加\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:24:10', '2020-05-30 21:24:10', '0', '0');
INSERT INTO `system_log` VALUES ('524', '', '1221', '超级管理员', '2', '系统异常', '{\"_request_method\":\"POST\",\"content\":\"测试配置添加\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:24:41', '2020-05-30 21:24:41', '0', '0');
INSERT INTO `system_log` VALUES ('525', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"content\":\"测试配置添加\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:25:25', '2020-05-30 21:25:25', '0', '0');
INSERT INTO `system_log` VALUES ('526', '', '1221', '超级管理员', '2', '系统异常', '{\"_request_method\":\"POST\",\"content\":\"测试配置添加\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:25:28', '2020-05-30 21:25:28', '0', '0');
INSERT INTO `system_log` VALUES ('527', '', '1221', '超级管理员', '2', '系统异常', '{\"_request_method\":\"POST\",\"content\":\"测试配置添加\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:26:33', '2020-05-30 21:26:33', '0', '0');
INSERT INTO `system_log` VALUES ('528', '', '1221', '超级管理员', '2', '系统异常', '{\"_request_method\":\"POST\",\"content\":\"测试配置添加1\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:26:52', '2020-05-30 21:26:52', '0', '0');
INSERT INTO `system_log` VALUES ('529', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"content\":\"测试配置添加1\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:27:33', '2020-05-30 21:27:33', '0', '0');
INSERT INTO `system_log` VALUES ('530', '', '1221', '超级管理员', '2', '系统异常', '{\"_request_method\":\"POST\",\"content\":\"测试配置添加1\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:27:39', '2020-05-30 21:27:39', '0', '0');
INSERT INTO `system_log` VALUES ('531', '', '1221', '超级管理员', '2', '系统异常', '{\"_request_method\":\"POST\",\"content\":\"测试配置添加2\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:28:42', '2020-05-30 21:28:42', '0', '0');
INSERT INTO `system_log` VALUES ('532', '', '1221', '超级管理员', '2', '系统异常', '{\"_request_method\":\"POST\",\"content\":\"测试配置添加2\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:30:45', '2020-05-30 21:30:45', '0', '0');
INSERT INTO `system_log` VALUES ('533', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"content\":\"测试配置添加2\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:36:31', '2020-05-30 21:36:31', '0', '0');
INSERT INTO `system_log` VALUES ('534', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"content\":\"测试配置添加2\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:36:33', '2020-05-30 21:36:33', '0', '0');
INSERT INTO `system_log` VALUES ('535', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"content\":\"测试配置添加3\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:36:37', '2020-05-30 21:36:37', '0', '0');
INSERT INTO `system_log` VALUES ('536', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"content\":\"测试配置添加4\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:36:40', '2020-05-30 21:36:40', '0', '0');
INSERT INTO `system_log` VALUES ('537', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"content\":\"测试配置添加5\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:36:43', '2020-05-30 21:36:43', '0', '0');
INSERT INTO `system_log` VALUES ('538', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"content\":\"测试配置添加6\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:36:47', '2020-05-30 21:36:47', '0', '0');
INSERT INTO `system_log` VALUES ('539', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"content\":\"测试配置添加7\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:36:51', '2020-05-30 21:36:51', '0', '0');
INSERT INTO `system_log` VALUES ('540', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"content\":\"测试配置添加8\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:36:55', '2020-05-30 21:36:55', '0', '0');
INSERT INTO `system_log` VALUES ('541', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"content\":\"测试配置添加9\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:36:59', '2020-05-30 21:36:59', '0', '0');
INSERT INTO `system_log` VALUES ('542', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"content\":\"测试配置添加10\",\"_request_path\":\"/config/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:37:09', '2020-05-30 21:37:09', '0', '0');
INSERT INTO `system_log` VALUES ('543', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"DELETE\",\"content\":\"测试配置添加3\",\"_request_path\":\"/config/item/5\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:46:31', '2020-05-30 21:46:31', '0', '0');
INSERT INTO `system_log` VALUES ('544', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"PUT\",\"content\":\"测试配置添加3\",\"_request_path\":\"/config/item/5\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:47:07', '2020-05-30 21:47:07', '0', '0');
INSERT INTO `system_log` VALUES ('545', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"PUT\",\"content\":\"测试配置添加3\",\"_request_path\":\"/config/item/5\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 21:47:20', '2020-05-30 21:47:20', '0', '0');
INSERT INTO `system_log` VALUES ('546', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"title\":\"标题\",\"content\":\"测试配置添加10\",\"_request_path\":\"/document/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 22:44:12', '2020-05-30 22:44:12', '0', '0');
INSERT INTO `system_log` VALUES ('547', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"title\":\"标题\",\"content\":\"测试配置添加10\",\"_request_path\":\"/document/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 22:44:32', '2020-05-30 22:44:32', '0', '0');
INSERT INTO `system_log` VALUES ('548', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"title\":\"标题\",\"content\":\"测试配置添加10\",\"_request_path\":\"/document/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 22:44:33', '2020-05-30 22:44:33', '0', '0');
INSERT INTO `system_log` VALUES ('549', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"title\":\"标题323\",\"content\":\"测试配置添加10\",\"_request_path\":\"/document/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 22:44:37', '2020-05-30 22:44:37', '0', '0');
INSERT INTO `system_log` VALUES ('550', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"title\":\"标题4\",\"content\":\"测试配置添加10\",\"_request_path\":\"/document/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 22:46:00', '2020-05-30 22:46:00', '0', '0');
INSERT INTO `system_log` VALUES ('551', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"PUT\",\"title\":\"不懂\",\"content\":\"测试配置添加3\",\"_request_path\":\"/document/item/2\",\"desc\":\"这只是一个测试\"}', '0', '2020-05-30 22:46:32', '2020-05-30 22:46:32', '0', '0');
INSERT INTO `system_log` VALUES ('552', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"DELETE\",\"_request_path\":\"/document/item/3\"}', '0', '2020-05-30 22:54:26', '2020-05-30 22:54:26', '0', '0');
INSERT INTO `system_log` VALUES ('553', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"title\":\"标题4\",\"content\":\"测试配置添加10\",\"_request_path\":\"/document/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-06-01 22:17:01', '2020-06-01 22:17:01', '0', '0');
INSERT INTO `system_log` VALUES ('554', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"_request_path\":\"/user/login\",\"username\":\"admin\"}', '0', '2020-06-01 22:22:01', '2020-06-01 22:22:01', '0', '0');
INSERT INTO `system_log` VALUES ('555', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"_request_path\":\"/user/login\",\"username\":\"admin\"}', '0', '2020-06-01 22:43:26', '2020-06-01 22:43:26', '0', '0');
INSERT INTO `system_log` VALUES ('556', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"_request_path\":\"/user/login\",\"username\":\"admin\"}', '0', '2020-06-03 19:27:43', '2020-06-03 19:27:43', '0', '0');
INSERT INTO `system_log` VALUES ('557', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"PUT\",\"title\":\"不懂\",\"content\":\"测试配置添加3\",\"_request_path\":\"/document/item/5\",\"desc\":\"这只是一个测试\"}', '0', '2020-06-03 19:28:44', '2020-06-03 19:28:44', '0', '0');
INSERT INTO `system_log` VALUES ('558', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"title\":\"标题323\",\"content\":\"测试配置添加10\",\"_request_path\":\"/document/item\",\"desc\":\"这只是一个测试\"}', '0', '2020-06-03 19:31:53', '2020-06-03 19:31:53', '0', '0');
INSERT INTO `system_log` VALUES ('559', '', '1221', '超级管理员', '2', '不能为空;', '{\"_request_method\":\"POST\",\"title\":\"标题323\",\"content\":\"测试配置添加10\",\"_request_path\":\"/document/item\"}', '0', '2020-06-03 19:31:58', '2020-06-03 19:31:58', '0', '0');
INSERT INTO `system_log` VALUES ('560', '', '1221', '超级管理员', '2', '系统异常', '{\"_request_method\":\"POST\",\"title\":\"[标题323]\",\"content\":\"[测试配置添加10]\",\"_request_path\":\"/document/item\"}', '0', '2020-06-03 19:34:28', '2020-06-03 19:34:28', '0', '0');
INSERT INTO `system_log` VALUES ('561', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"PUT\",\"title\":\"不懂\",\"content\":\"测试配置添加3\",\"_request_path\":\"/document/item/5\",\"desc\":\"这只是一个测试\"}', '0', '2020-06-03 21:26:18', '2020-06-03 21:26:18', '0', '0');
INSERT INTO `system_log` VALUES ('562', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"PUT\",\"title\":\"不懂\",\"content\":\"测试配置添加3\",\"_request_path\":\"/document/item/5\",\"desc\":\"这只是一个测试\"}', '0', '2020-06-03 21:27:14', '2020-06-03 21:27:14', '0', '0');
INSERT INTO `system_log` VALUES ('563', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"PUT\",\"title\":\"不懂\",\"content\":\"测试配置添加3\",\"_request_path\":\"/document/item/5\",\"desc\":\"这只是一个测试\"}', '0', '2020-06-03 21:35:39', '2020-06-03 21:35:39', '0', '0');
INSERT INTO `system_log` VALUES ('564', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"PUT\",\"title\":\"不懂11\",\"content\":\"测试配置添加31\",\"_request_path\":\"/document/item/5\",\"desc\":\"这只是一个测试11\"}', '0', '2020-06-03 21:36:19', '2020-06-03 21:36:19', '0', '0');
INSERT INTO `system_log` VALUES ('565', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"PUT\",\"title\":\"不懂11\",\"content\":\"测试配置添加31\",\"_request_path\":\"/document/item/5\",\"desc\":\"这只是一个测试11\"}', '0', '2020-06-03 21:49:04', '2020-06-03 21:49:04', '0', '0');
INSERT INTO `system_log` VALUES ('566', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"PUT\",\"title\":\"不懂11\",\"content\":\"测试配置添加31\",\"_request_path\":\"/document/item/5\",\"desc\":\"这只是一个测试11\"}', '0', '2020-06-03 23:36:07', '2020-06-03 23:36:07', '0', '0');
INSERT INTO `system_log` VALUES ('567', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"PUT\",\"title\":\"不懂11\",\"content\":\"测试配置添加31\",\"_request_path\":\"/document/item/5\",\"desc\":\"这只是一个测试11\"}', '0', '2020-06-03 23:36:56', '2020-06-03 23:36:56', '0', '0');
INSERT INTO `system_log` VALUES ('568', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"PUT\",\"content\":\"测试配置添加3\",\"_request_path\":\"/hot_config/item/5\",\"desc\":\"这只是一个测试\"}', '0', '2020-06-03 23:39:58', '2020-06-03 23:39:58', '0', '0');
INSERT INTO `system_log` VALUES ('569', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"_request_path\":\"/user/login\",\"username\":\"admin\"}', '0', '2020-06-05 19:40:23', '2020-06-05 19:40:23', '0', '0');
INSERT INTO `system_log` VALUES ('570', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"_request_path\":\"/user/login\",\"username\":\"admin\"}', '0', null, '2020-06-06 11:34:37', '0', '0');
INSERT INTO `system_log` VALUES ('571', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 11:35:25', '0', '0');
INSERT INTO `system_log` VALUES ('572', '', '1221', '超级管理员', '2', '文件上传失败', '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 11:37:52', '0', '0');
INSERT INTO `system_log` VALUES ('573', '', '1221', '超级管理员', '2', '文件上传失败', '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 11:38:14', '0', '0');
INSERT INTO `system_log` VALUES ('574', '', '1221', '超级管理员', '2', '文件上传失败', '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 11:38:48', '0', '0');
INSERT INTO `system_log` VALUES ('575', '', '1221', '超级管理员', '2', '文件上传失败', '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 11:39:02', '0', '0');
INSERT INTO `system_log` VALUES ('576', '', '1221', '超级管理员', '2', '文件上传失败', '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 11:41:30', '0', '0');
INSERT INTO `system_log` VALUES ('577', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 11:42:18', '0', '0');
INSERT INTO `system_log` VALUES ('578', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 11:42:29', '0', '0');
INSERT INTO `system_log` VALUES ('579', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 11:42:40', '0', '0');
INSERT INTO `system_log` VALUES ('580', '', '1221', '超级管理员', '2', '文件上传失败', '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 11:43:08', '0', '0');
INSERT INTO `system_log` VALUES ('581', '', '1221', '超级管理员', '2', '文件上传失败', '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 11:46:22', '0', '0');
INSERT INTO `system_log` VALUES ('582', '', '1221', '超级管理员', '2', '文件上传失败', '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 12:09:57', '0', '0');
INSERT INTO `system_log` VALUES ('583', '', '1221', '超级管理员', '2', '文件类型错误', '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 12:10:13', '0', '0');
INSERT INTO `system_log` VALUES ('584', '', '1221', '超级管理员', '2', '文件上传失败', '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 12:10:24', '0', '0');
INSERT INTO `system_log` VALUES ('585', '', '1221', '超级管理员', '2', '文件上传失败', '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 12:10:35', '0', '0');
INSERT INTO `system_log` VALUES ('586', '', '1221', '超级管理员', '2', '文件上传失败', '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 12:10:51', '0', '0');
INSERT INTO `system_log` VALUES ('587', '', '1221', '超级管理员', '2', '文件上传失败', '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 12:11:27', '0', '0');
INSERT INTO `system_log` VALUES ('588', '', '1221', '超级管理员', '2', '文件上传失败', '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 12:11:38', '0', '0');
INSERT INTO `system_log` VALUES ('589', '', '1221', '超级管理员', '2', '文件上传失败', '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 12:12:06', '0', '0');
INSERT INTO `system_log` VALUES ('590', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 12:12:29', '0', '0');
INSERT INTO `system_log` VALUES ('591', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"PUT\",\"content\":\"测试配置添加3\",\"_request_path\":\"/hot_config/item/5\",\"desc\":\"这只是一个测试\"}', '0', null, '2020-06-06 13:00:17', '0', '0');
INSERT INTO `system_log` VALUES ('592', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"PUT\",\"content\":\"测试配置添加3\",\"_request_path\":\"/hot_config/item/5\",\"desc\":\"这只是一个测试\"}', '0', null, '2020-06-06 13:01:44', '0', '0');
INSERT INTO `system_log` VALUES ('593', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"PUT\",\"content\":\"测试配置添加3\",\"_request_path\":\"/hot_config/item/5\",\"desc\":\"这只是一个测试\"}', '0', null, '2020-06-06 13:04:11', '0', '0');
INSERT INTO `system_log` VALUES ('594', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"PUT\",\"content\":\"测试配置添加3\",\"_request_path\":\"/hot_config/item/5\",\"desc\":\"这只是一个测试\"}', '0', null, '2020-06-06 13:31:49', '0', '0');
INSERT INTO `system_log` VALUES ('595', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"PUT\",\"content\":\"测试配置添加3\",\"_request_path\":\"/hot_config/item/5\",\"desc\":\"这只是一个测试\"}', '0', null, '2020-06-06 13:33:21', '0', '0');
INSERT INTO `system_log` VALUES ('596', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', null, '2020-06-06 16:06:02', '0', '0');
INSERT INTO `system_log` VALUES ('597', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"_request_path\":\"/user/login\",\"username\":\"admin\"}', '0', null, '2020-06-06 17:02:14', '0', '0');
INSERT INTO `system_log` VALUES ('598', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"name\":\"role1\",\"_request_path\":\"/system/role/item\",\"desc\":\"123\"}', '0', null, '2020-06-06 17:02:44', '0', '0');
INSERT INTO `system_log` VALUES ('599', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"DELETE\",\"_request_path\":\"/system/role/item/125\"}', '0', null, '2020-06-06 17:04:40', '0', '0');
INSERT INTO `system_log` VALUES ('600', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"DELETE\",\"_request_path\":\"/system/role/item/125\"}', '0', null, '2020-06-06 17:06:40', '0', '0');
INSERT INTO `system_log` VALUES ('601', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"DELETE\",\"_request_path\":\"/system/role/item/125\"}', '0', null, '2020-06-06 17:09:03', '0', '0');
INSERT INTO `system_log` VALUES ('602', '', '1221', '超级管理员', '1', null, '{\"_request_method\":\"POST\",\"_request_path\":\"/file/image\"}', '0', '2020-06-06 18:02:08', '2020-06-06 18:02:08', '0', '0');

-- ----------------------------
-- Table structure for system_login_session
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of system_login_session
-- ----------------------------
INSERT INTO `system_login_session` VALUES ('111', '6b45e21bcc3243738402148369f4a09c', '1221', '1591788463332', '2020-06-03 19:27:43', '2020-06-03 19:27:43', '0', '0');
INSERT INTO `system_login_session` VALUES ('112', 'f3be7e1414044bc2b18411cebd0c6fe3', '1221', '1591962024008', '2020-06-05 19:40:23', '2020-06-05 19:40:23', '0', '0');

-- ----------------------------
-- Table structure for system_perm
-- ----------------------------
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

-- ----------------------------
-- Records of system_perm
-- ----------------------------
INSERT INTO `system_perm` VALUES ('511', '*', '*', '0', '2020-05-02 21:28:03', '2020-05-02 21:28:03', '0', '0');
INSERT INTO `system_perm` VALUES ('513', '/dashboard,/system,/system/user_list,/system/user_role,/system/user_log,/system/user_list/$page,/system/user_list/viewItem,/system/user_list/addItem,/system/user_list/editItem,/system/user_list/editMembers,/system/user_list/editLimit,/system/user_list/delItem,/system/user_role/$page,/system/user_role/editRole,/system/user_role/addIRole,/system/user_role/editLimit,/system/user_role/listMember,/system/user_role/editMember,/system/user_role/delRole,/system/user_log/$page', 'GET rtapi/system/user/item,POST rtapi/system/user/item,PUT rtapi/system/user/item/$id,DELETE rtapi/system/user/item/$id,GET rtapi/system/role/item,GET rtapi/system/role/filter?filter=$term,PUT rtapi/system/role/item/$id,DELETE rtapi/system/role/item/$id,GET rtapi/system/role/item/$id/limit,PUT rtapi/system/role/item/$id/limit,PUT rtapi/system/role/member,GET rtapi/system/log/item', '0', '2020-05-04 22:04:31', '2020-05-04 22:04:31', '0', '0');
INSERT INTO `system_perm` VALUES ('514', '/dashboard', 'GET rtapi/system/user/item,POST rtapi/system/user/item,PUT rtapi/system/user/item/$id,DELETE rtapi/system/user/item/$id,GET rtapi/system/role/item,GET rtapi/system/role/filter?filter=$term,POST rtapi/system/role/item/$id,PUT rtapi/system/role/item/$id,DELETE rtapi/system/role/item/$id,GET rtapi/system/role/item/$id/limit,PUT rtapi/system/role/item/$id/limit,PUT rtapi/system/role/member,GET rtapi/system/log/item', '0', '2020-05-05 16:45:02', '2020-05-05 16:45:02', '0', '0');
INSERT INTO `system_perm` VALUES ('515', '/_global/system,/_global/system/roleFilter,/_global/system/userInfoModal,/dashboard,/start,/aew,/system,/system/user_list,/system/user_role,/system/user_log,/system/user_list/$page,/system/user_list/viewTree,/system/user_list/addItem,/system/user_list/editItem,/system/user_list/delItem,/system/user_role/$page,/system/user_role/editRole,/system/user_role/addRole,/system/user_role/editLimit,/system/user_role/listMember,/system/user_role/editMember,/system/user_role/delRole,/system/user_log/$page', 'GET rtapi/system/role/item,GET rtapi/system/role/filter?filter=$term,GET rtapi/system/user/item,POST rtapi/system/user/item,PUT rtapi/system/user/item/$id,DELETE rtapi/system/user/item/$id,GET rtapi/system/user/tree,POST rtapi/system/role/item/$id,PUT rtapi/system/role/item/$id,DELETE rtapi/system/role/item/$id,GET rtapi/system/role/item/$id/limit,PUT rtapi/system/role/item/$id/limit,PUT rtapi/system/role/member,GET rtapi/system/log/item', '0', '2020-05-10 15:42:18', '2020-05-10 15:42:18', '0', '0');
INSERT INTO `system_perm` VALUES ('516', '/_global/system,/_global/system/roleFilter,/_global/system/userInfoModal,/dashboard,/start,/aew,/system,/system/user_list,/system/user_role,/system/user_log,/system/user_list/$page,/system/user_list/viewTree,/system/user_list/addItem,/system/user_list/editItem,/system/user_list/delItem,/system/user_role/$page,/system/user_role/editRole,/system/user_role/addRole,/system/user_role/editLimit,/system/user_role/listMember,/system/user_role/editMember,/system/user_role/delRole,/system/user_log/$page', 'GET rtapi/system/role/item,GET rtapi/system/role/filter?filter=$term,GET rtapi/system/user/item,POST rtapi/system/user/item,PUT rtapi/system/user/item/$id,DELETE rtapi/system/user/item/$id,GET rtapi/system/user/tree,POST rtapi/system/role/item/$id,PUT rtapi/system/role/item/$id,DELETE rtapi/system/role/item/$id,GET rtapi/system/role/item/$id/limit,PUT rtapi/system/role/item/$id/limit,PUT rtapi/system/role/member,GET rtapi/system/log/item', '0', '2020-05-10 16:39:33', '2020-05-10 16:39:33', '0', '0');
INSERT INTO `system_perm` VALUES ('517', '/dashboard', 'GET rtapi/system/role/item,GET rtapi/system/role/filter?filter=$term,GET rtapi/system/user/item,POST rtapi/system/user/item,PUT rtapi/system/user/item/$id,DELETE rtapi/system/user/item/$id,GET rtapi/system/user/tree,POST rtapi/system/role/item/$id,PUT rtapi/system/role/item/$id,DELETE rtapi/system/role/item/$id,GET rtapi/system/role/item/$id/limit,PUT rtapi/system/role/item/$id/limit,PUT rtapi/system/role/member,GET rtapi/system/log/item', '0', '2020-05-10 17:32:26', '2020-05-10 17:32:26', '0', '0');
INSERT INTO `system_perm` VALUES ('518', '/_global/system,/_global/system/roleFilter,/_global/system/userInfoModal,/dashboard,/start,/aew,/system,/system/user_list,/system/user_role,/system/user_log,/system/user_list/$page,/system/user_list/viewTree,/system/user_list/addItem,/system/user_list/editItem,/system/user_list/delItem,/system/user_role/$page,/system/user_role/editRole,/system/user_role/addRole,/system/user_role/editLimit,/system/user_role/listMember,/system/user_role/editMember,/system/user_role/delRole,/system/user_log/$page', 'GET rtapi/system/role/item,GET rtapi/system/role/filter?filter=$term,GET rtapi/system/user/item,POST rtapi/system/user/item,PUT rtapi/system/user/item/$id,DELETE rtapi/system/user/item/$id,GET rtapi/system/user/tree,POST rtapi/system/role/item/$id,PUT rtapi/system/role/item/$id,DELETE rtapi/system/role/item/$id,GET rtapi/system/role/item/$id/limit,PUT rtapi/system/role/item/$id/limit,PUT rtapi/system/role/member,GET rtapi/system/log/item', '0', '2020-05-10 17:54:39', '2020-05-10 17:54:39', '0', '0');

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
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

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES ('1', 'admin', 'admin', '0', '0', '0', '2020-04-12 20:43:53', '2020-05-02 21:28:38', '0', '0');
INSERT INTO `system_role` VALUES ('115', '总裁', '总裁办公室阿斯顿', '1', '0', '0-1', '2020-04-17 23:21:02', '2020-05-03 22:41:19', '1221', '1221');
INSERT INTO `system_role` VALUES ('116', '总裁', '总裁办公室', '0', '0', '0-1', '2020-04-17 23:41:24', '2020-05-03 22:41:22', '1221', '1221');
INSERT INTO `system_role` VALUES ('117', '角色1', '角色描述', '0', '1', '0-1', '2020-05-02 21:28:59', '2020-05-02 21:28:59', '1221', '1221');
INSERT INTO `system_role` VALUES ('118', '测试角色', '测试角色', '0', '115', '0-1-115', '2020-05-03 22:01:36', '2020-05-03 22:01:36', '1230', '1230');
INSERT INTO `system_role` VALUES ('119', '测试角色', '测试角色', '0', '115', '0-1-115', '2020-05-03 22:03:29', '2020-05-03 22:03:29', '1230', '1230');
INSERT INTO `system_role` VALUES ('120', '测试角色', '用于测试角色功能是否正常。', '0', '1', '0-1', '2020-05-05 16:44:54', '2020-06-06 17:12:29', '1221', '1221');
INSERT INTO `system_role` VALUES ('121', '角色1', '用于测试的角色', '0', '116', '0-1-116', '2020-05-06 22:25:51', '2020-05-06 22:25:51', '1233', '1233');
INSERT INTO `system_role` VALUES ('122', '开发者', '用于所有的开发者', '0', '1', '0-1', '2020-05-10 15:41:41', '2020-05-10 15:41:41', '1221', '1221');
INSERT INTO `system_role` VALUES ('123', '访客管理员', '用于所有的体验者', '0', '122', '0-1-122', '2020-05-10 16:31:51', '2020-05-10 16:31:51', '1235', '1235');
INSERT INTO `system_role` VALUES ('124', '访客', '所有注册生成的访客', '0', '123', '0-1-122-123', '2020-05-10 16:42:11', '2020-05-10 16:42:11', '1236', '1236');

-- ----------------------------
-- Table structure for system_role_perm
-- ----------------------------
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

-- ----------------------------
-- Records of system_role_perm
-- ----------------------------
INSERT INTO `system_role_perm` VALUES ('4213', '1', '511', '0', '2020-05-02 21:27:50', '2020-05-06 23:13:39', '0', '0');
INSERT INTO `system_role_perm` VALUES ('4214', '115', '1', '0', '2020-05-02 21:27:50', '2020-05-02 21:27:50', '0', '0');
INSERT INTO `system_role_perm` VALUES ('4216', '116', '513', '0', '2020-05-04 22:04:31', '2020-05-04 22:04:31', '0', '0');
INSERT INTO `system_role_perm` VALUES ('4217', '120', '514', '0', '2020-05-05 16:45:02', '2020-05-05 16:45:02', '0', '0');
INSERT INTO `system_role_perm` VALUES ('4218', '122', '515', '0', '2020-05-10 15:42:18', '2020-05-10 15:42:18', '0', '0');
INSERT INTO `system_role_perm` VALUES ('4219', '123', '516', '0', '2020-05-10 16:39:33', '2020-05-10 16:39:33', '0', '0');
INSERT INTO `system_role_perm` VALUES ('4220', '117', '517', '0', '2020-05-10 17:32:26', '2020-05-10 17:32:26', '0', '0');
INSERT INTO `system_role_perm` VALUES ('4221', '124', '518', '0', '2020-05-10 17:54:39', '2020-05-10 17:54:39', '0', '0');

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
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

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES ('1221', 'admin', 'QY5pIHeEvYJhdmEAhu79tg==', '超级管理员', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1588434416218&di=f5ea10aaec473fa1ec4147c7eb84f0c1&imgtype=0&src=http%3A%2F%2Ft7.baidu.com%2Fit%2Fu%3D3616242789%2C1098670747%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D900%26h%3D1350', 'WuBBxoGi', '0', '0', '我的签名帅吗', 'admin', '0', '2020-04-06 15:17:34', '2020-04-19 00:05:59', '0', '0');
INSERT INTO `system_user` VALUES ('1222', 'admin111', '4S8Uj0QZcGe95k+J/7Dacw==', '王大浪2', null, 'v9R4W6bb', '1221', '1', null, '沈德符沈德符', '0-1221', '2020-04-13 22:35:37', '2020-04-19 00:06:06', '0', '0');
INSERT INTO `system_user` VALUES ('1224', 'admin1114', '8HxG1a5wov/cSrzHWq9P0Q==', '你好123', null, 'kHVXnTKf', '1221', '1', null, '史蒂夫叔的阿斯顿', '0-1221', '2020-04-13 23:30:04', '2020-05-04 20:10:23', '0', '0');
INSERT INTO `system_user` VALUES ('1225', 'admin222', 'RH7e5JTFsfgyilq2JoZtKQ==', '你哈', null, 'R0J1PJKW', '1221', '0', null, '史蒂夫史蒂夫', '0-1221', '2020-04-13 23:30:50', '2020-04-19 00:06:14', '0', '0');
INSERT INTO `system_user` VALUES ('1226', 'admin333', '5s1i5bdRt8xM/6lAjf3XZg==', '你好', null, 'DxGkc4C9', '1221', '1', null, '阿斯顿', '0-1221', '2020-04-14 23:41:03', '2020-04-19 00:06:16', '0', '0');
INSERT INTO `system_user` VALUES ('1227', '我是新用户1', 'RyhK4+rZGacui2Czsk853Q==', '我是新用户', 'xxxx', 'Sfo8gTMp', '1230', '0', null, '测试421', '0-1221-1230', '2020-05-02 21:08:47', '2020-05-05 16:25:36', '0', '0');
INSERT INTO `system_user` VALUES ('1229', '我是新用户', 'eHDTiZtyomawlhvDcpRHsw==', '我是新用户', 'xxxx', 'JMoYNOWW', '1230', '0', null, '测试', '0-1221-1230', '2020-05-02 21:22:21', '2020-05-05 16:25:42', '0', '0');
INSERT INTO `system_user` VALUES ('1230', 'admin123', 'QG7NGViNeIu/jf17AMIclA==', '测试用户', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1588434416218&di=f5ea10aaec473fa1ec4147c7eb84f0c1&imgtype=0&src=http%3A%2F%2Ft7.baidu.com%2Fit%2Fu%3D3616242789%2C1098670747%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D900%26h%3D1350', 'CNZFNBnu', '1221', '0', '我的签名帅吗', '用于测试', '0-1221', '2020-05-03 20:20:25', '2020-05-03 20:20:25', '0', '0');
INSERT INTO `system_user` VALUES ('1231', 'admin333', 'P7rrQAY5WMktkEz4joYZQw==', '你好么么', null, 'V9wIgvfn', '1221', '0', null, '牛逼大佬,hello wolrd 1', '0-1221', '2020-05-04 21:30:05', '2020-05-04 21:30:05', '0', '0');
INSERT INTO `system_user` VALUES ('1232', 'admin666', 'yC/UCYrBNhhaBt9AMiv/CQ==', '牛哥', null, 'u1JHhEJn', '1221', '0', null, '荷兰牛', '0-1221', '2020-05-04 21:40:59', '2020-05-04 21:40:59', '0', '0');
INSERT INTO `system_user` VALUES ('1233', 'test01', 'DLt1Iq9+5PkyWAT8Db4B5g==', '测试01', 'http://xtr46z.natappfree.cc/rtapi/file/image/14476025004032', 'vDGHDHfd', '1221', '0', 'xixi', '用于测试01，niubi3422', '0-1221', '2020-05-05 15:54:40', '2020-05-05 15:54:40', '0', '0');
INSERT INTO `system_user` VALUES ('1234', 'test001', 'ta6IaMhfuNr0fWngHMDqqA==', '测试001', null, 'LorTcuP8', '1233', '0', null, '测试01的子账号', '0-1221-1233', '2020-05-06 22:25:23', '2020-05-06 22:25:23', '0', '0');
INSERT INTO `system_user` VALUES ('1235', 'carey1', 'q2rKEFXqx/cUuGKvzUBpQg==', 'Carey', 'http://static.igroupes.com/default_avatar.jpg', 'dGe5jVrX', '1221', '0', null, '开发者carey', '0-1221', '2020-05-10 15:41:12', '2020-05-10 16:13:26', '0', '0');
INSERT INTO `system_user` VALUES ('1236', 'visitor', 'YEJy58SF0Vy14Z0Y4f1Czw==', '访客管理员', 'http://static.igroupes.com/default_avatar.jpg', 'U8HTHgkn', '1235', '0', null, '用于管理所有的体验者账号', '0-1221-1235', '2020-05-10 16:31:10', '2020-05-10 17:49:08', '0', '0');
INSERT INTO `system_user` VALUES ('1237', 'zhangsan', 'Ko06WwBRtdAbtHMeeSSxCA==', 'zhangsan', 'http://static.igroupes.com/default_avatar.jpg', 'RsBGCrXx', '1236', '0', null, null, '0-1221-1235-1236', '2020-05-10 17:48:51', '2020-05-10 17:50:23', '0', '0');
INSERT INTO `system_user` VALUES ('1238', 'user01', '4cMcQ30bV6GYtE2QW+VrhQ==', '测试好', 'http://static.igroupes.com/default_avatar.jpg', 'Ix7WoGTu', '1236', '0', null, null, '0-1221-1235-1236', '2020-05-10 17:49:34', '2020-05-10 17:50:19', '0', '0');

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=1147 DEFAULT CHARSET=utf8mb4 COMMENT='用户和角色的映射表';

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
INSERT INTO `system_user_role` VALUES ('1111', '1221', '1', '0', '2020-04-12 20:41:07', '2020-04-12 20:41:07', '1221', '1221');
INSERT INTO `system_user_role` VALUES ('1112', '1230', '115', '0', '2020-04-12 20:41:07', '2020-04-12 20:41:07', '1221', '1221');
INSERT INTO `system_user_role` VALUES ('1118', '1225', '117', '20200504211955', '2020-05-04 20:54:04', '2020-05-04 21:19:55', '1221', '1221');
INSERT INTO `system_user_role` VALUES ('1121', '1229', '119', '20200504210818', '2020-05-04 21:07:24', '2020-05-04 21:08:18', '1230', '1230');
INSERT INTO `system_user_role` VALUES ('1122', '1229', '119', '20200504211639', '2020-05-04 21:08:18', '2020-05-04 21:16:39', '1230', '1230');
INSERT INTO `system_user_role` VALUES ('1126', '1229', '117', '20200504212924', '2020-05-04 21:16:39', '2020-05-04 21:29:24', '1230', '1230');
INSERT INTO `system_user_role` VALUES ('1130', '1229', '119', '0', '2020-05-04 21:29:24', '2020-05-04 21:29:24', '1230', '1230');
INSERT INTO `system_user_role` VALUES ('1132', '1231', '116', '20200504214934', '2020-05-04 21:48:45', '2020-05-04 21:49:34', '1221', '1221');
INSERT INTO `system_user_role` VALUES ('1133', '1232', '116', '20200504214934', '2020-05-04 21:48:45', '2020-05-04 21:49:34', '1221', '1221');
INSERT INTO `system_user_role` VALUES ('1134', '1231', '117', '20200504221720', '2020-05-04 21:49:34', '2020-05-04 22:17:20', '1221', '1221');
INSERT INTO `system_user_role` VALUES ('1135', '1232', '117', '20200504221720', '2020-05-04 21:49:34', '2020-05-04 22:17:20', '1221', '1221');
INSERT INTO `system_user_role` VALUES ('1136', '1231', '116', '0', '2020-05-04 22:17:20', '2020-05-04 22:17:20', '1221', '1221');
INSERT INTO `system_user_role` VALUES ('1137', '1232', '116', '0', '2020-05-04 22:17:20', '2020-05-04 22:17:20', '1221', '1221');
INSERT INTO `system_user_role` VALUES ('1138', '1233', '116', '20200505164958', '2020-05-05 16:38:56', '2020-05-05 16:49:58', '1221', '1221');
INSERT INTO `system_user_role` VALUES ('1139', '1233', '120', '20200506221837', '2020-05-05 16:49:58', '2020-05-06 22:18:37', '1221', '1221');
INSERT INTO `system_user_role` VALUES ('1140', '1233', '116', '20200524163526', '2020-05-06 22:18:37', '2020-05-24 16:35:26', '1221', '1221');
INSERT INTO `system_user_role` VALUES ('1141', '1234', '121', '0', '2020-05-06 22:26:10', '2020-05-06 22:26:10', '1233', '1233');
INSERT INTO `system_user_role` VALUES ('1142', '1235', '122', '0', '2020-05-10 15:42:03', '2020-05-10 15:42:03', '1221', '1221');
INSERT INTO `system_user_role` VALUES ('1143', '1236', '123', '0', '2020-05-10 16:37:30', '2020-05-10 16:37:30', '1235', '1235');
INSERT INTO `system_user_role` VALUES ('1144', '1237', '124', '0', '2020-05-10 17:48:51', '2020-05-10 17:48:51', '0', '0');
INSERT INTO `system_user_role` VALUES ('1145', '1238', '124', '0', '2020-05-10 17:49:34', '2020-05-10 17:49:34', '0', '0');
INSERT INTO `system_user_role` VALUES ('1146', '1233', '120', '0', '2020-05-24 16:35:26', '2020-05-24 16:35:26', '1221', '1221');

-- ----------------------------
-- Table structure for system_user_stat
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户行为统计';

-- ----------------------------
-- Records of system_user_stat
-- ----------------------------
INSERT INTO `system_user_stat` VALUES ('1', '1221', '127.0.0.1', '100001', null, '2020-06-05 20:42:46', '2020-06-05 20:42:46', '0', '0');
