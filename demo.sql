/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-06-15 11:25:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_resources
-- ----------------------------
DROP TABLE IF EXISTS `sys_resources`;
CREATE TABLE `sys_resources` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `resource_name` varchar(100) NOT NULL,
  `resource_type` varchar(100) NOT NULL,
  `resource_content` varchar(200) NOT NULL,
  `resource_desc` varchar(200) NOT NULL,
  `enabled` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `resource_name` (`resource_name`),
  KEY `resource_name_2` (`resource_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resources
-- ----------------------------
INSERT INTO `sys_resources` VALUES ('1', '首页', 'requesturl', '/index', '首页', '1');
INSERT INTO `sys_resources` VALUES ('2', 'getList', 'requesturl', '/user/getList', '用户', '1');
INSERT INTO `sys_resources` VALUES ('3', 'getUser', 'getUser', '/user/getUser', 'getUser', '1');
INSERT INTO `sys_resources` VALUES ('4', 'test', 'test', '/user/test', 'test', '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT 'id',
  `role_name` varchar(50) DEFAULT NULL COMMENT 'name',
  `descn` varchar(50) DEFAULT NULL COMMENT 'descn',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'ROLE_ADMIN', '管理员角色');
INSERT INTO `sys_role` VALUES ('2', 'ROLE_USER', '用户角色');

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES ('2', '2', '2');
INSERT INTO `sys_role_resource` VALUES ('3', '3', '2');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` varchar(64) NOT NULL DEFAULT '0' COMMENT 'id',
  `user_name` varchar(64) DEFAULT NULL COMMENT 'username',
  `password` varchar(64) DEFAULT NULL COMMENT 'password',
  `status` int(1) DEFAULT NULL COMMENT 'status',
  `descn` varchar(128) DEFAULT NULL COMMENT 'descd',
  PRIMARY KEY (`user_id`),
  KEY `AK_Key_1` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'fc1709d0a95a6be30bc5926fdb7f22f4', '1', '管理\r\n员');
INSERT INTO `sys_user` VALUES ('2', 'wangwei', 'fc1709d0a95a6be30bc5926fdb7f22f4', '1', '用户\r\n');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) DEFAULT NULL COMMENT '用户表_id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色表_id',
  KEY `FK_FK_USER_ROLE_ROLE` (`role_id`),
  KEY `FK_FK_USER_ROLE_USER` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2');
