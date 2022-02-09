/*
Navicat MySQL Data Transfer

Source Server         : 0.17
Source Server Version : 50734
Source Host           : 192.168.0.17:3306
Source Database       : workspace

Target Server Type    : MYSQL
Target Server Version : 50734
File Encoding         : 65001

Date: 2022-02-09 15:30:42
*/

SET FOREIGN_KEY_CHECKS=0;
create database workspace;
-- ----------------------------
-- Table structure for icredit_workspace
-- ----------------------------
DROP TABLE IF EXISTS `icredit_workspace`;
CREATE TABLE `icredit_workspace` (
  `id` varchar(32) NOT NULL,
  `status` tinyint(4) DEFAULT NULL COMMENT '空间状态',
  `del_flag` char(1) NOT NULL DEFAULT 'N' COMMENT '删除标识',
  `descriptor` varchar(250) DEFAULT NULL COMMENT '空间描述',
  `director` varchar(100) DEFAULT NULL COMMENT '负责人',
  `name` varchar(100) DEFAULT NULL COMMENT '空间名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(100) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(100) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for icredit_workspace_user
-- ----------------------------
DROP TABLE IF EXISTS `icredit_workspace_user`;
CREATE TABLE `icredit_workspace_user` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `space_id` varchar(32) DEFAULT NULL COMMENT '工作空间id',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `username` varchar(64) DEFAULT NULL,
  `org_name` varchar(255) DEFAULT NULL,
  `user_role` varchar(255) DEFAULT NULL COMMENT '用户角色',
  `functional_authority` varchar(50) DEFAULT NULL COMMENT '功能权限',
  `data_authority` varchar(50) DEFAULT NULL COMMENT '数据权限',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(100) NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(100) NOT NULL DEFAULT '' COMMENT '创建人',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人',
  `sort` int(11) DEFAULT NULL,
  `tenant_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `space_user_id` (`space_id`,`user_id`,`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
