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

 Date: 08/12/2021 09:21:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tool_codegen_column
-- ----------------------------
DROP TABLE IF EXISTS `tool_codegen_column`;
CREATE TABLE `tool_codegen_column` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `table_id` bigint(20) NOT NULL COMMENT '表编号',
  `column_name` varchar(200) NOT NULL COMMENT '字段名',
  `column_type` varchar(100) NOT NULL COMMENT '字段类型',
  `column_comment` varchar(500) NOT NULL COMMENT '字段描述',
  `nullable` bit(1) NOT NULL COMMENT '是否允许为空',
  `primary_key` bit(1) NOT NULL COMMENT '是否主键',
  `auto_Increment` char(1) NOT NULL COMMENT '是否自增',
  `ordinal_position` int(11) NOT NULL COMMENT '排序',
  `java_type` varchar(32) NOT NULL COMMENT 'Java 属性类型',
  `java_field` varchar(64) NOT NULL COMMENT 'Java 属性名',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `example` varchar(64) DEFAULT NULL COMMENT '数据示例',
  `create_operation` bit(1) NOT NULL COMMENT '是否为 Create 创建操作的字段',
  `update_operation` bit(1) NOT NULL COMMENT '是否为 Update 更新操作的字段',
  `list_operation` bit(1) NOT NULL COMMENT '是否为 List 查询操作的字段',
  `list_operation_condition` varchar(32) NOT NULL DEFAULT '=' COMMENT 'List 查询操作的条件类型',
  `list_operation_result` bit(1) NOT NULL COMMENT '是否为 List 查询操作的返回字段',
  `html_type` varchar(32) NOT NULL COMMENT '显示类型',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代码生成表字段定义';

-- ----------------------------
-- Records of tool_codegen_column
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
