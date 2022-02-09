/*
Navicat MySQL Data Transfer

Source Server         : 0.17
Source Server Version : 50734
Source Host           : 192.168.0.17:3306
Source Database       : datasync

Target Server Type    : MYSQL
Target Server Version : 50734
File Encoding         : 65001

Date: 2022-02-09 15:42:40
*/

SET FOREIGN_KEY_CHECKS=0;
create database datasync;
-- ----------------------------
-- Table structure for icredit_dict
-- ----------------------------
DROP TABLE IF EXISTS `icredit_dict`;
CREATE TABLE `icredit_dict` (
  `id` varchar(32) NOT NULL,
  `workspace_id` varchar(40) NOT NULL COMMENT '工作空间ID',
  `english_name` varchar(255) DEFAULT NULL COMMENT '字典英文名称',
  `chinese_name` varchar(255) DEFAULT NULL COMMENT '字典中文名称',
  `create_user_id` varchar(40) NOT NULL COMMENT '字典创建人ID',
  `create_user_name` varchar(100) NOT NULL COMMENT '字典创建人名称',
  `create_time` datetime NOT NULL COMMENT '字典创建时间',
  `dict_desc` varchar(255) DEFAULT NULL COMMENT '字典描述',
  `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标识（0 -- 未删除，1 -- 已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for icredit_dict_column
-- ----------------------------
DROP TABLE IF EXISTS `icredit_dict_column`;
CREATE TABLE `icredit_dict_column` (
  `id` varchar(32) NOT NULL,
  `dict_id` varchar(32) NOT NULL COMMENT '字典ID',
  `column_key` varchar(50) NOT NULL COMMENT '字典列key',
  `column_value` varchar(50) DEFAULT NULL COMMENT '字典列值',
  `remark` varchar(255) DEFAULT NULL COMMENT '字典列备注',
  `del_flag` tinyint(4) DEFAULT NULL COMMENT '字典列删除标识（0 -- 未删除，1 -- 已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for icredit_sync_task
-- ----------------------------
DROP TABLE IF EXISTS `icredit_sync_task`;
CREATE TABLE `icredit_sync_task` (
  `id` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `workspace_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '工作空间ID',
  `task_name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '同步任务名称',
  `enable` tinyint(4) DEFAULT NULL COMMENT '0 启用  1未启用',
  `task_status` tinyint(1) DEFAULT NULL COMMENT '任务状态【0：启用，1：停用，2：草稿】',
  `create_mode` tinyint(1) DEFAULT NULL COMMENT '创建方式【0：可视化，1：SQL】',
  `collect_mode` tinyint(1) DEFAULT NULL COMMENT '采集方式',
  `sync_mode` tinyint(1) DEFAULT NULL COMMENT '同步方式',
  `exec_status` tinyint(1) DEFAULT NULL COMMENT '执行状态',
  `last_scheduling_time` datetime DEFAULT NULL,
  `task_describe` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务描述',
  `cron_param` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL,
  `task_param_json` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务参数',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `schedule_id` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '调度任务ID',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `last_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `last_update_user_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
  `delete_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识【0：未删除，1：已删除】',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='数据同步任务表';

-- ----------------------------
-- Table structure for icredit_sync_task_copy
-- ----------------------------
DROP TABLE IF EXISTS `icredit_sync_task_copy`;
CREATE TABLE `icredit_sync_task_copy` (
  `id` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `workspace_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '工作空间ID',
  `task_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '同步任务名称',
  `task_status` tinyint(1) DEFAULT NULL COMMENT '任务状态【0：启用，1：停用，2：草稿】',
  `create_mode` tinyint(1) DEFAULT NULL COMMENT '创建方式【0：可视化，1：SQL】',
  `collect_mode` tinyint(1) DEFAULT NULL COMMENT '采集方式',
  `sync_mode` tinyint(1) DEFAULT NULL COMMENT '同步方式',
  `exec_status` tinyint(1) DEFAULT NULL COMMENT '执行状态',
  `last_scheduling_time` datetime DEFAULT NULL,
  `task_describe` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务描述',
  `task_param_json` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务参数',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `schedule_id` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '调度任务ID',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `last_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `last_update_user_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
  `delete_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识【0：未删除，1：已删除】',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='数据同步任务表';

-- ----------------------------
-- Table structure for icredit_sync_task_hi
-- ----------------------------
DROP TABLE IF EXISTS `icredit_sync_task_hi`;
CREATE TABLE `icredit_sync_task_hi` (
  `id` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `task_id` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '对应的任务ID',
  `workspace_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '工作空间ID',
  `task_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '同步任务名称',
  `enable` tinyint(4) DEFAULT NULL COMMENT '0 启用  1 未启用',
  `task_status` tinyint(1) DEFAULT NULL COMMENT '任务状态【0：启用，1：停用，2：草稿】',
  `create_mode` tinyint(1) DEFAULT NULL COMMENT '创建方式【0：可视化，1：SQL】',
  `collect_mode` tinyint(1) DEFAULT NULL COMMENT '采集方式【0：手动执行，1：周期执行】',
  `sync_mode` tinyint(1) DEFAULT NULL COMMENT '同步方式【0:增量同步，1：全量同步】',
  `exec_status` tinyint(1) DEFAULT NULL COMMENT '执行状态',
  `last_scheduling_time` datetime DEFAULT NULL,
  `task_describe` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务描述',
  `cron_param` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL,
  `task_param_json` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务参数',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `schedule_id` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '调度任务ID',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `last_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `last_update_user_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
  `delete_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识【0：未删除，1：已删除】',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='数据同步任务表';

-- ----------------------------
-- Table structure for icredit_sync_widetable
-- ----------------------------
DROP TABLE IF EXISTS `icredit_sync_widetable`;
CREATE TABLE `icredit_sync_widetable` (
  `id` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `sync_task_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务ID',
  `sql_str` text COLLATE utf8mb4_bin COMMENT '查询sql',
  `view_json` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL,
  `dialect` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `datasource_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '数据源ID',
  `target_url` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '目标源地址',
  `target_source` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `partition_field` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分区字段',
  `source_type` tinyint(1) DEFAULT NULL,
  `source_tables` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL,
  `sync_condition` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分区、增量同步信息',
  `name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `delete_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识【0：未删除，1：已删除】',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='数据同步目标表结构信息表';

-- ----------------------------
-- Table structure for icredit_sync_widetable_field
-- ----------------------------
DROP TABLE IF EXISTS `icredit_sync_widetable_field`;
CREATE TABLE `icredit_sync_widetable_field` (
  `id` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `wide_table_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '字段名称',
  `type` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `source` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `database_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `chinese` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `dict_key` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `delete_flag` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='宽表字段表';

-- ----------------------------
-- Table structure for icredit_sync_widetable_field_hi
-- ----------------------------
DROP TABLE IF EXISTS `icredit_sync_widetable_field_hi`;
CREATE TABLE `icredit_sync_widetable_field_hi` (
  `id` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `widetable_field_id` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '宽表字段记录',
  `wide_table_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '字段名称',
  `type` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `database_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `source` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `chinese` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `dict_key` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `delete_flag` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='宽表字段表';

-- ----------------------------
-- Table structure for icredit_sync_widetable_hi
-- ----------------------------
DROP TABLE IF EXISTS `icredit_sync_widetable_hi`;
CREATE TABLE `icredit_sync_widetable_hi` (
  `id` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `widetable_id` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '宽表记录ID',
  `sync_task_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务ID',
  `dialect` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `sql_str` text COLLATE utf8mb4_bin COMMENT '查询sql',
  `view_json` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '可视化关联关系json',
  `datasource_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '数据源ID',
  `target_source` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '目标库',
  `sync_condition` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分区字段',
  `source_type` tinyint(1) DEFAULT NULL COMMENT '数据源类型',
  `source_tables` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '可视化创建表信息',
  `name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '宽表名称',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `delete_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识【0：未删除，1：已删除】',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='数据同步目标表结构信息表';
