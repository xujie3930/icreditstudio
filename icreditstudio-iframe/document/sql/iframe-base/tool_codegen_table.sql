/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.210
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 192.168.0.210:3306
 Source Schema         : iframe

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 08/12/2021 09:21:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tool_codegen_table
-- ----------------------------
DROP TABLE IF EXISTS `tool_codegen_table`;
CREATE TABLE `tool_codegen_table` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `import_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '导入类型',
  `table_name` varchar(200) NOT NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) NOT NULL DEFAULT '' COMMENT '表描述',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `module_name` varchar(30) NOT NULL COMMENT '模块名',
  `business_name` varchar(30) NOT NULL COMMENT '业务名',
  `class_name` varchar(100) NOT NULL DEFAULT '' COMMENT '类名称',
  `class_comment` varchar(50) NOT NULL COMMENT '类描述',
  `author` varchar(50) NOT NULL COMMENT '作者',
  `template_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '模板类型',
  `parent_menu_id` bigint(20) DEFAULT NULL COMMENT '父菜单编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代码生成表定义';

-- ----------------------------
-- Records of tool_codegen_table
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
