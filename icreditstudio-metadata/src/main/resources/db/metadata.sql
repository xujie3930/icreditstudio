/*
Navicat MySQL Data Transfer

Source Server         : 0.17
Source Server Version : 50734
Source Host           : 192.168.0.17:3306
Source Database       : metadata

Target Server Type    : MYSQL
Target Server Version : 50734
File Encoding         : 65001

Date: 2022-02-09 15:44:31
*/

SET FOREIGN_KEY_CHECKS=0;
create database metadata;
-- ----------------------------
-- Table structure for icredit_workspace_table
-- ----------------------------
DROP TABLE IF EXISTS `icredit_workspace_table`;
CREATE TABLE `icredit_workspace_table` (
  `id` varchar(30) NOT NULL,
  `workspace_id` varchar(30) DEFAULT NULL COMMENT '工作空间ID',
  `database_name` varchar(50) DEFAULT NULL COMMENT 'hibe库名称',
  `table_name` varchar(200) DEFAULT NULL COMMENT 'hive表名称',
  `remark` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(30) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` varchar(30) DEFAULT NULL,
  `delete_flag` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工作空间和hive表映射';
