/*
Navicat MySQL Data Transfer

Source Server         : 0.17
Source Server Version : 50734
Source Host           : 192.168.0.17:3306
Source Database       : datasource

Target Server Type    : MYSQL
Target Server Version : 50734
File Encoding         : 65001

Date: 2021-11-18 18:01:33
*/

SET FOREIGN_KEY_CHECKS=0;

CREATE DATABASE IF NOT EXISTS datasource DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

-- ----------------------------
-- Table structure for icredit_datasource
-- ----------------------------
DROP TABLE IF EXISTS `datasource`.`icredit_datasource`;
CREATE TABLE `datasource`.`icredit_datasource` (
                                                   `id` varchar(32) NOT NULL COMMENT '主键id',
                                                   `space_id` varchar(32) DEFAULT NULL COMMENT '工作空间id',
                                                   `category` tinyint(4) NOT NULL DEFAULT '1',
                                                   `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '数据源类型',
                                                   `name` varchar(15) DEFAULT NULL COMMENT '数据源自定义名称',
                                                   `uri` varchar(255) DEFAULT NULL COMMENT '连接信息',
                                                   `status` tinyint(4) DEFAULT '0' COMMENT '是否启用：0-启用，1-非启用',
                                                   `del_flag` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除:N-否，Y-删除',
                                                   `last_sync_time` datetime DEFAULT NULL COMMENT '最后一次同步时间',
                                                   `last_sync_status` tinyint(4) DEFAULT '2' COMMENT '最后一次同步状态：0-成功，1-失败,2未执行',
                                                   `descriptor` varchar(255) DEFAULT NULL COMMENT '描述信息',
                                                   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                                   `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
                                                   `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                                   `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
                                                   `show_password` tinyint(4) DEFAULT '0',
                                                   `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
                                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for icredit_ddl_sync
-- ----------------------------
DROP TABLE IF EXISTS `datasource`.`icredit_ddl_sync`;
CREATE TABLE `datasource`.`icredit_ddl_sync` (
                                                 `id` varchar(32) NOT NULL COMMENT '主键id',
                                                 `space_id` varchar(32) DEFAULT NULL COMMENT '工作空间id',
                                                 `datasource_id` varchar(32) DEFAULT NULL COMMENT 'datasource表的id',
                                                 `uri` varchar(255) DEFAULT NULL COMMENT '连接信息',
                                                 `columns_info` varchar(8000) DEFAULT NULL COMMENT '表所有信息',
                                                 `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                                 `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
                                                 `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                                 `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
                                                 `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
                                                 `del_flag` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除:N-否，Y-删除',
                                                 `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
                                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*
Navicat MySQL Data Transfer

Source Server         : 0.17
Source Server Version : 50734
Source Host           : 192.168.0.17:3306
Source Database       : datasync

Target Server Type    : MYSQL
Target Server Version : 50734
File Encoding         : 65001

Date: 2021-11-18 18:03:25
*/

SET FOREIGN_KEY_CHECKS=0;
CREATE DATABASE IF NOT EXISTS datasync DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

-- ----------------------------
-- Table structure for icredit_sync_task
-- ----------------------------
DROP TABLE IF EXISTS `datasync`.`icredit_sync_task`;
CREATE TABLE `datasync`.`icredit_sync_task` (
                                                `id` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                                                `workspace_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '工作空间ID',
                                                `task_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '同步任务名称',
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
DROP TABLE IF EXISTS `datasync`.`icredit_sync_task_copy`;
CREATE TABLE `datasync`.`icredit_sync_task_copy` (
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
DROP TABLE IF EXISTS `datasync`.`icredit_sync_task_hi`;
CREATE TABLE `datasync`.`icredit_sync_task_hi` (
                                                   `id` varchar(30) COLLATE utf8mb4_bin NOT NULL,
                                                   `task_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
                                                   `workspace_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属工作空间ID',
                                                   `task_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务名称',
                                                   `task_status` tinyint(1) DEFAULT NULL COMMENT '任务状态【0：启用，1：停用，2：草稿】',
                                                   `create_mode` tinyint(1) DEFAULT NULL COMMENT '创建方式【0：可视化，1：SQL】',
                                                   `collect_mode` tinyint(1) DEFAULT NULL,
                                                   `sync_mode` tinyint(1) DEFAULT NULL,
                                                   `exec_status` tinyint(1) DEFAULT NULL,
                                                   `last_scheduling_time` datetime DEFAULT NULL,
                                                   `task_describe` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                                                   `task_param_json` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL,
                                                   `version` int(11) DEFAULT NULL,
                                                   `schedule_id` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                                                   `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                                                   `create_time` datetime DEFAULT NULL,
                                                   `create_by` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
                                                   `update_time` datetime DEFAULT NULL,
                                                   `update_by` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
                                                   `del_flag` tinyint(1) DEFAULT NULL,
                                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='历史数据同步任务表';

-- ----------------------------
-- Table structure for icredit_sync_widetable
-- ----------------------------
DROP TABLE IF EXISTS `datasync`.`icredit_sync_widetable`;
CREATE TABLE `datasync`.`icredit_sync_widetable` (
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
DROP TABLE IF EXISTS `datasync`.`icredit_sync_widetable_field`;
CREATE TABLE `datasync`.`icredit_sync_widetable_field` (
                                                           `id` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                                                           `wide_table_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
                                                           `sort` int(11) DEFAULT NULL,
                                                           `name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '字段名称',
                                                           `type` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
                                                           `source` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                                                           `chinese` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                                                           `dict_key` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                                                           `database_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
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
DROP TABLE IF EXISTS `datasync`.`icredit_sync_widetable_field_hi`;
CREATE TABLE `datasync`.`icredit_sync_widetable_field_hi` (
                                                              `id` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                                                              `wide_table_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
                                                              `name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '字段名称',
                                                              `type` tinyint(1) DEFAULT NULL,
                                                              `source` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                                                              `chinese` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                                                              `dict_key` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                                                              `version` int(11) DEFAULT NULL,
                                                              `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                                                              `create_time` datetime DEFAULT NULL,
                                                              `create_by` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
                                                              `update_time` datetime DEFAULT NULL,
                                                              `update_by` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
                                                              `del_flag` tinyint(1) DEFAULT NULL,
                                                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='宽表字段表';

-- ----------------------------
-- Table structure for icredit_sync_widetable_hi
-- ----------------------------
DROP TABLE IF EXISTS `datasync`.`icredit_sync_widetable_hi`;
CREATE TABLE `datasync`.`icredit_sync_widetable_hi` (
                                                        `id` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                                                        `sync_task_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务ID',
                                                        `sql_str` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '查询sql',
                                                        `datasource_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '数据源ID',
                                                        `target_url` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '目标源地址',
                                                        `partition_str` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分区字段',
                                                        `name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
                                                        `version` int(11) DEFAULT NULL COMMENT '版本号',
                                                        `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                                                        `create_time` datetime DEFAULT NULL,
                                                        `create_by` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
                                                        `update_time` datetime DEFAULT NULL,
                                                        `update_by` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
                                                        `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识【0：未删除，1：已删除】',
                                                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='数据同步目标表结构信息表';


/*
Navicat MySQL Data Transfer

Source Server         : 0.17
Source Server Version : 50734
Source Host           : 192.168.0.17:3306
Source Database       : workspace

Target Server Type    : MYSQL
Target Server Version : 50734
File Encoding         : 65001

Date: 2021-11-18 18:09:53
*/

SET FOREIGN_KEY_CHECKS=0;

CREATE DATABASE IF NOT EXISTS workspace DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

-- ----------------------------
-- Table structure for icredit_workspace
-- ----------------------------
DROP TABLE IF EXISTS `workspace`.`icredit_workspace`;
CREATE TABLE `workspace`.`icredit_workspace` (
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
                                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for icredit_workspace_user
-- ----------------------------
DROP TABLE IF EXISTS `workspace`.`icredit_workspace_user`;
CREATE TABLE `workspace`.`icredit_workspace_user` (
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
                                                      PRIMARY KEY (`id`),
                                                      UNIQUE KEY `space_id` (`space_id`,`user_id`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


/*
Navicat MySQL Data Transfer

Source Server         : 0.17
Source Server Version : 50734
Source Host           : 192.168.0.17:3306
Source Database       : icdstuiframe

Target Server Type    : MYSQL
Target Server Version : 50734
File Encoding         : 65001

Date: 2021-11-18 18:09:15
*/

SET FOREIGN_KEY_CHECKS=0;

CREATE DATABASE IF NOT EXISTS icdstuiframe DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

-- ----------------------------
-- Table structure for ge_audit_log
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_audit_log`;
CREATE TABLE `icdstuiframe`.`ge_audit_log` (
                                               `id` varchar(32) NOT NULL,
                                               `oprate_type` varchar(100) DEFAULT NULL COMMENT '增、删、改、查--C、R、U、D',
                                               `user_id` varchar(32) DEFAULT NULL,
                                               `user_name` varchar(100) DEFAULT NULL,
                                               `oprate_time` varchar(100) DEFAULT NULL,
                                               `oprate_result` varchar(10) DEFAULT NULL COMMENT 'S....SUCCESS\n            F....FAILURE',
                                               `oprate_info` varchar(2000) DEFAULT NULL,
                                               `create_time` bigint(20) NOT NULL,
                                               `create_user_id` varchar(32) NOT NULL,
                                               `last_update_time` bigint(20) DEFAULT NULL,
                                               `last_update_user_id` varchar(32) DEFAULT NULL,
                                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ge_code_info
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_code_info`;
CREATE TABLE `icdstuiframe`.`ge_code_info` (
                                               `ID` varchar(32) NOT NULL COMMENT '主键',
                                               `CODE_NAME` varchar(100) DEFAULT NULL COMMENT '名称',
                                               `CODE_TYPE` varchar(100) DEFAULT NULL COMMENT '类型',
                                               `CODE_VALUE` varchar(100) DEFAULT NULL COMMENT '值',
                                               `CODE_REMARK` varchar(500) DEFAULT NULL COMMENT '说明',
                                               `CODE_SORT` varchar(10) DEFAULT NULL COMMENT '排序字段',
                                               `CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间',
                                               `CREATE_USER_ID` varchar(32) NOT NULL COMMENT '创建人',
                                               `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL COMMENT '更新时间',
                                               `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '更新人',
                                               `DELETE_FLAG` varchar(10) DEFAULT NULL COMMENT 'Y.....已删除\n            N.....未删除',
                                               PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ge_form_data
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_form_data`;
CREATE TABLE `icdstuiframe`.`ge_form_data` (
                                               `ID` varchar(32) NOT NULL,
                                               `ELEMENT_ID` varchar(32) DEFAULT NULL,
                                               `FORM_INSTANCE_ID` varchar(32) DEFAULT NULL,
                                               `PARAM_KEY` varchar(100) DEFAULT NULL,
                                               `PARAM_VALUE` text,
                                               `DEF_JSON` text,
                                               `CREATE_TIME` bigint(20) NOT NULL,
                                               `CREATE_USER_ID` varchar(32) NOT NULL,
                                               `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL,
                                               `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL,
                                               PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单实例数据';

-- ----------------------------
-- Table structure for ge_form_definition
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_form_definition`;
CREATE TABLE `icdstuiframe`.`ge_form_definition` (
                                                     `ID` varchar(32) NOT NULL,
                                                     `FORM_NAME` varchar(100) DEFAULT NULL COMMENT '表单名称',
                                                     `FORM_PUBLISH_TIME` bigint(20) DEFAULT NULL,
                                                     `FORM_UPDATE_TIME` bigint(20) DEFAULT NULL,
                                                     `FORM_STATUS` varchar(10) DEFAULT NULL COMMENT '(0 未发布1已发布)',
                                                     `FORM_VERSION` varchar(20) DEFAULT '1.0',
                                                     `FORM_DESC` varchar(500) DEFAULT NULL,
                                                     `USER_ID` varchar(32) DEFAULT NULL,
                                                     `DEF_JSON` text,
                                                     `CREATE_TIME` bigint(20) DEFAULT NULL,
                                                     `CREATE_USER_ID` varchar(32) DEFAULT NULL,
                                                     `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL,
                                                     `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL,
                                                     PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单定义模板表';

-- ----------------------------
-- Table structure for ge_form_element
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_form_element`;
CREATE TABLE `icdstuiframe`.`ge_form_element` (
                                                  `ID` varchar(32) NOT NULL,
                                                  `FORM_ID` varchar(32) DEFAULT NULL,
                                                  `ELE_FLAG` varchar(100) DEFAULT NULL,
                                                  `ELE_LABEL` varchar(100) DEFAULT NULL COMMENT '(0字符串,1字符串数组,2普通对象,3普通对象数组,4控件,5控件数组)',
                                                  `PARAM_KEY` varchar(100) DEFAULT NULL,
                                                  `ELE_JSON` text,
                                                  `FORM_VERSION` varchar(10) DEFAULT NULL,
                                                  `CREATE_TIME` bigint(20) NOT NULL,
                                                  `CREATE_USER_ID` varchar(32) NOT NULL,
                                                  `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL,
                                                  `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL,
                                                  `DEL_FLAG` varchar(10) DEFAULT NULL,
                                                  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ge_form_hi_definition
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_form_hi_definition`;
CREATE TABLE `icdstuiframe`.`ge_form_hi_definition` (
                                                        `ID` varchar(32) NOT NULL,
                                                        `FORM_ID` varchar(32) DEFAULT NULL,
                                                        `FORM_NAME` varchar(100) DEFAULT NULL,
                                                        `FORM_PUBLISH_TIME` bigint(20) DEFAULT NULL,
                                                        `FORM_UPDATE_TIME` bigint(20) DEFAULT NULL,
                                                        `FORM_STATUS` varchar(10) DEFAULT NULL,
                                                        `FORM_VERSION` varchar(10) DEFAULT NULL,
                                                        `FORM_DESC` varchar(500) DEFAULT NULL,
                                                        `USER_ID` varchar(32) DEFAULT NULL,
                                                        `DEF_JSON` text,
                                                        `CREATE_TIME` bigint(20) DEFAULT NULL,
                                                        `CREATE_USER_ID` varchar(32) DEFAULT NULL,
                                                        `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL,
                                                        `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL,
                                                        PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ge_form_hi_element
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_form_hi_element`;
CREATE TABLE `icdstuiframe`.`ge_form_hi_element` (
                                                     `ID` varchar(32) NOT NULL,
                                                     `FORM_ID` varchar(32) DEFAULT NULL,
                                                     `FORM_VERSION` varchar(10) DEFAULT NULL,
                                                     `ELE_FLAG` varchar(32) DEFAULT NULL,
                                                     `ELE_LABEL` varchar(100) DEFAULT NULL,
                                                     `PARAM_KEY` varchar(100) DEFAULT NULL,
                                                     `ELE_JSON` text,
                                                     `CREATE_TIME` bigint(20) DEFAULT NULL,
                                                     `CREATE_USER_ID` varchar(32) DEFAULT NULL,
                                                     `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL,
                                                     `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL,
                                                     `DEL_FLAG` varchar(10) DEFAULT '',
                                                     PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ge_form_hi_perm
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_form_hi_perm`;
CREATE TABLE `icdstuiframe`.`ge_form_hi_perm` (
                                                  `ID` varchar(32) NOT NULL,
                                                  `FORM_ID` varchar(32) DEFAULT NULL,
                                                  `ELE_ID` varchar(32) DEFAULT NULL,
                                                  `PERM_FLAG` varchar(20) DEFAULT NULL,
                                                  `USER_IDS` text,
                                                  `FORM_VERSION` varchar(10) DEFAULT NULL,
                                                  `CREATE_TIME` bigint(20) DEFAULT NULL,
                                                  `CREATE_USER_ID` varchar(32) DEFAULT NULL,
                                                  `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL,
                                                  `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL,
                                                  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ge_form_instance
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_form_instance`;
CREATE TABLE `icdstuiframe`.`ge_form_instance` (
                                                   `ID` varchar(32) NOT NULL,
                                                   `FORM_ID` varchar(32) NOT NULL,
                                                   `FORM_NAME` varchar(100) DEFAULT NULL,
                                                   `FORM_DESC` varchar(500) DEFAULT NULL,
                                                   `FORM_VERSION` varchar(10) DEFAULT NULL,
                                                   `CREATE_TIME` bigint(20) NOT NULL,
                                                   `INSTANCE_STATUS` varchar(10) DEFAULT NULL,
                                                   `USER_ID` varchar(32) DEFAULT NULL,
                                                   `DEF_JSON` text,
                                                   `CREATE_USER_ID` varchar(32) NOT NULL,
                                                   `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL,
                                                   `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL,
                                                   PRIMARY KEY (`ID`),
                                                   KEY `FK_Reference_8` (`FORM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单实例';

-- ----------------------------
-- Table structure for ge_form_perm
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_form_perm`;
CREATE TABLE `icdstuiframe`.`ge_form_perm` (
                                               `ID` varchar(32) NOT NULL,
                                               `ELE_ID` varchar(32) DEFAULT NULL,
                                               `FORM_ID` varchar(32) DEFAULT NULL,
                                               `PERM_FLAG` varchar(20) DEFAULT NULL,
                                               `USER_IDS` text,
                                               `FORM_VERSION` varchar(10) DEFAULT NULL,
                                               `CREATE_TIME` bigint(20) DEFAULT NULL,
                                               `CREATE_USER_ID` varchar(32) DEFAULT NULL,
                                               `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL,
                                               `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL,
                                               PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ge_information
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_information`;
CREATE TABLE `icdstuiframe`.`ge_information` (
                                                 `ID` varchar(32) NOT NULL COMMENT '主键',
                                                 `INFO_TITLE` varchar(200) DEFAULT NULL COMMENT '消息标题',
                                                 `INFO_CONTENT` varchar(2000) DEFAULT NULL COMMENT '消息内容',
                                                 `SEND_TIME` bigint(20) DEFAULT NULL COMMENT '消息发送时间',
                                                 `INFO_TYPE` varchar(10) DEFAULT NULL COMMENT '系统......S (system)\n            通知......N(notice）\n            预警......W(warning)',
                                                 `SENDER_ID` varchar(32) DEFAULT NULL COMMENT '发送人',
                                                 `CREATE_TIME` bigint(20) NOT NULL,
                                                 `CREATE_USER_ID` varchar(32) NOT NULL,
                                                 `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL,
                                                 `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL,
                                                 `DELETE_FLAG` varchar(10) DEFAULT NULL COMMENT 'Y....yes\n            N...no',
                                                 `BROADCAST_FLAG` varchar(10) DEFAULT NULL COMMENT 'S.....单记录\n            B.....广播',
                                                 PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ge_info_receiver
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_info_receiver`;
CREATE TABLE `icdstuiframe`.`ge_info_receiver` (
                                                   `ID` varchar(32) NOT NULL COMMENT '主键',
                                                   `INFO_ID` varchar(200) DEFAULT NULL COMMENT '消息ID',
                                                   `RECEIVER_ID` varchar(32) DEFAULT NULL COMMENT '接收者ID',
                                                   `READ_TIME` varchar(50) DEFAULT NULL COMMENT '阅读时间',
                                                   `READ_STATUS` varchar(10) DEFAULT NULL COMMENT 'Y...YES  N...NO',
                                                   `DELETE_FLAG` varchar(32) DEFAULT NULL COMMENT 'Y...YES\n            N...NO',
                                                   `CREATE_TIME` bigint(20) DEFAULT NULL,
                                                   `CREATE_USER_ID` varchar(32) DEFAULT NULL,
                                                   `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL,
                                                   `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL,
                                                   PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ge_interfaces
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_interfaces`;
CREATE TABLE `icdstuiframe`.`ge_interfaces` (
                                                `interface_id` varchar(32) NOT NULL,
                                                `uri` varchar(100) NOT NULL COMMENT '接口URI',
                                                `method` varchar(30) NOT NULL COMMENT 'GET，POST，PUT，DELETE',
                                                `module` varchar(30) NOT NULL COMMENT '接口归属系统模块',
                                                `name` varchar(100) NOT NULL COMMENT '名称',
                                                `remark` varchar(200) NOT NULL COMMENT '说明',
                                                `need_auth` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否需要鉴权，0不需要鉴权，1需要鉴权',
                                                `support_auth_type` varchar(20) NOT NULL DEFAULT 'cert' COMMENT '支持的鉴权方式，字符串，以下划线分隔。如：cert,token,cert_token',
                                                `uri_type` varchar(5) NOT NULL COMMENT '接口地址, 通配符 0:接口地址，1：通配符',
                                                `CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间',
                                                `CREATE_USER_ID` varchar(32) NOT NULL COMMENT '创建者id',
                                                `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL COMMENT '更新时间',
                                                `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '更新者id',
                                                PRIMARY KEY (`interface_id`),
                                                KEY `IDX_interface_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for ge_login_log
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_login_log`;
CREATE TABLE `icdstuiframe`.`ge_login_log` (
                                               `id` varchar(32) NOT NULL,
                                               `user_account` varchar(100) DEFAULT NULL,
                                               `user_name` varchar(100) DEFAULT NULL,
                                               `login_time` varchar(100) DEFAULT NULL,
                                               `login_status` varchar(10) DEFAULT NULL COMMENT 'S....SUCCESS\n            F....FAILURE',
                                               `error_info` varchar(200) DEFAULT NULL,
                                               `user_ip` varchar(30) DEFAULT NULL,
                                               `user_token` varchar(100) DEFAULT NULL,
                                               `logout_time` varchar(100) DEFAULT NULL COMMENT 'Y.....已删除\n            N.....未删除',
                                               `create_time` bigint(20) NOT NULL,
                                               `create_user_id` varchar(32) NOT NULL,
                                               `last_update_time` bigint(20) DEFAULT NULL,
                                               `last_update_user_id` varchar(32) DEFAULT NULL,
                                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ge_organization
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_organization`;
CREATE TABLE `icdstuiframe`.`ge_organization` (
                                                  `ID` varchar(32) NOT NULL COMMENT 'id',
                                                  `ORG_CODE` varchar(32) DEFAULT NULL COMMENT '编号',
                                                  `ORG_NAME` varchar(255) DEFAULT NULL COMMENT '部门名称',
                                                  `PARENT_ID` varchar(32) DEFAULT NULL COMMENT '父部门id',
                                                  `ORG_ADDRESS` varchar(255) DEFAULT NULL COMMENT '部门地址',
                                                  `ICON_PATH` varchar(200) DEFAULT NULL COMMENT '图标地址',
                                                  `SORT_NUMBER` int(11) DEFAULT NULL COMMENT '排序字段',
                                                  `LINK_MAN_NAME` varchar(255) DEFAULT NULL COMMENT '联系人名称',
                                                  `LINK_MAN_TEL` varchar(64) DEFAULT NULL COMMENT '联系人电话',
                                                  `ORG_REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
                                                  `DELETE_FLAG` varchar(1) DEFAULT NULL COMMENT '删除标志Y.已删除 N.未删除',
                                                  `CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间',
                                                  `CREATE_USER_ID` varchar(32) NOT NULL COMMENT '创建者id',
                                                  `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL COMMENT '更新时间',
                                                  `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '更新者id',
                                                  `EXTEND_ONE` varchar(200) DEFAULT NULL COMMENT '扩展字段1',
                                                  `EXTEND_TWO` varchar(200) DEFAULT NULL COMMENT '扩展字段2',
                                                  `EXTEND_FOUR` varchar(200) DEFAULT NULL COMMENT '扩展字段3',
                                                  `EXTEND_THREE` varchar(200) DEFAULT NULL COMMENT '扩展字段4',
                                                  PRIMARY KEY (`ID`),
                                                  KEY `CODE` (`ORG_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ge_resources
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_resources`;
CREATE TABLE `icdstuiframe`.`ge_resources` (
                                               `ID` varchar(32) NOT NULL COMMENT 'id',
                                               `NAME` varchar(255) DEFAULT NULL COMMENT '菜单名称',
                                               `CODE` varchar(32) DEFAULT NULL COMMENT '编号',
                                               `PARENT_ID` varchar(32) DEFAULT '0' COMMENT '父菜单id',
                                               `URL` varchar(255) DEFAULT NULL COMMENT '路由路径(B 按钮类型URL为后端请求路径)',
                                               `AUTH_IDENTIFICATION` varchar(255) DEFAULT NULL COMMENT '权限标识',
                                               `REDIRECT_PATH` varchar(255) DEFAULT NULL COMMENT '重定向路径',
                                               `FILE_PATH` varchar(200) DEFAULT NULL COMMENT '文件路径',
                                               `IS_SHOW` varchar(32) DEFAULT NULL COMMENT '是否在左侧菜单显示 Y 是  N 否',
                                               `IS_CACHE` varchar(5) DEFAULT NULL COMMENT '是否缓存 Y...是 N...否',
                                               `KEEP_ALIVE` varchar(5) DEFAULT NULL COMMENT '是否保留 Y...是 N...否',
                                               `ICON_PATH` varchar(200) DEFAULT NULL COMMENT '图标',
                                               `SORT_NUMBER` int(11) DEFAULT NULL COMMENT '排序字段',
                                               `INTERNAL_OR_EXTERNAL` varchar(5) DEFAULT NULL COMMENT '内部或者外部',
                                               `REMARK` varchar(200) DEFAULT NULL COMMENT '备注信息',
                                               `TYPE` varchar(1) DEFAULT NULL COMMENT '菜单类型 M.菜单 D.顶部模块 B按钮',
                                               `DELETE_FLAG` varchar(1) DEFAULT NULL COMMENT '删除标志 Y.已删除 N.未删除',
                                               `CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间',
                                               `CREATE_USER_ID` varchar(32) NOT NULL COMMENT '创建者id',
                                               `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL COMMENT '更新时间',
                                               `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '更新者id',
                                               `NEED_AUTH` int(11) DEFAULT '1',
                                               PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for ge_role
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_role`;
CREATE TABLE `icdstuiframe`.`ge_role` (
                                          `ID` varchar(32) NOT NULL COMMENT 'id',
                                          `ROLE_NAME` varchar(255) DEFAULT NULL COMMENT '角色名称',
                                          `ROLE_CODE` varchar(64) DEFAULT NULL COMMENT '角色编码',
                                          `PARENT_ID` char(32) DEFAULT NULL COMMENT '父角色id',
                                          `SORT_NUMBER` int(11) DEFAULT NULL COMMENT '排序字段',
                                          `ROLE_REMARK` varchar(255) DEFAULT NULL COMMENT '角色备注',
                                          `DELETE_FLAG` varchar(1) DEFAULT NULL COMMENT '删除标志 Y.已删除 N.未删除',
                                          `CREATE_TIME` bigint(20) NOT NULL COMMENT '创建者id',
                                          `CREATE_USER_ID` varchar(32) NOT NULL COMMENT '创建时间',
                                          `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL COMMENT '更新时间',
                                          `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '更新者id',
                                          PRIMARY KEY (`ID`),
                                          KEY `CODE` (`ROLE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ge_role_resources_map
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_role_resources_map`;
CREATE TABLE `icdstuiframe`.`ge_role_resources_map` (
                                                        `ID` varchar(32) NOT NULL COMMENT 'id',
                                                        `ROLE_ID` varchar(32) DEFAULT NULL COMMENT '角色id',
                                                        `RESOURCES_ID` varchar(32) DEFAULT NULL COMMENT '菜单_id',
                                                        `CREATE_TIME` bigint(20) NOT NULL COMMENT '创建者id',
                                                        `CREATE_USER_ID` varchar(32) NOT NULL COMMENT '创建时间',
                                                        `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL COMMENT '更新时间',
                                                        `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '更新者id',
                                                        PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ge_shortcut_menu
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_shortcut_menu`;
CREATE TABLE `icdstuiframe`.`ge_shortcut_menu` (
                                                   `ID` varchar(32) NOT NULL,
                                                   `USER_ID` varchar(32) DEFAULT NULL,
                                                   `RESOURCE_ID` varchar(32) DEFAULT NULL,
                                                   `CREATE_TIME` bigint(20) NOT NULL,
                                                   `CREATE_USER_ID` varchar(32) NOT NULL,
                                                   `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL,
                                                   `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL,
                                                   PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='快捷菜单';

-- ----------------------------
-- Table structure for ge_system_settings
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_system_settings`;
CREATE TABLE `icdstuiframe`.`ge_system_settings` (
                                                     `ID` varchar(32) NOT NULL,
                                                     `LOGO_PATH` mediumblob,
                                                     `APP_NAME` varchar(100) DEFAULT NULL,
                                                     `COPY_RIGHT` varchar(200) DEFAULT NULL,
                                                     `DEFAULT_FONT_SIZE` varchar(100) DEFAULT NULL,
                                                     `DEFAULT_CSS_ID` varchar(100) DEFAULT NULL,
                                                     `PARAM_ONE` varchar(100) DEFAULT NULL,
                                                     `PARAM_TWO` varchar(100) DEFAULT NULL,
                                                     `PARAM_THREE` varchar(100) DEFAULT NULL COMMENT 'Y.....已删除\n            N.....未删除',
                                                     `PARAM_FOUR` varchar(100) DEFAULT NULL,
                                                     `PARAM_FIVE` varchar(100) DEFAULT NULL,
                                                     `PARAM_SIX` varchar(100) DEFAULT NULL,
                                                     `CREATE_TIME` bigint(20) NOT NULL,
                                                     `CREATE_USER_ID` varchar(32) NOT NULL,
                                                     `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL,
                                                     `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL,
                                                     `DEFAULT_LAYOUT` varchar(100) DEFAULT NULL,
                                                     PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ge_user
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_user`;
CREATE TABLE `icdstuiframe`.`ge_user` (
                                          `ID` varchar(32) NOT NULL COMMENT 'id',
                                          `USER_NAME` varchar(255) DEFAULT NULL COMMENT '用户名称',
                                          `USER_CODE` varchar(32) DEFAULT NULL COMMENT '工号',
                                          `USER_GENDER` varchar(2) DEFAULT NULL COMMENT '性别',
                                          `USER_BIRTH` varchar(32) DEFAULT NULL COMMENT '生日',
                                          `ID_CARD` varchar(255) DEFAULT NULL COMMENT '身份证号',
                                          `ENTRY_TIME` datetime DEFAULT NULL COMMENT '入职时间',
                                          `DEPARTURE_TIME` datetime DEFAULT NULL COMMENT '离职时间',
                                          `E_MAIL` varchar(255) DEFAULT NULL COMMENT '邮箱',
                                          `USER_POSITION` varchar(255) DEFAULT NULL COMMENT '职位',
                                          `TEL_PHONE` varchar(32) DEFAULT NULL COMMENT '联系方式',
                                          `SORT_NUMBER` int(11) DEFAULT NULL COMMENT '排序字段',
                                          `DELETE_FLAG` varchar(1) DEFAULT NULL COMMENT '删除标志Y.已删除 N.未删除',
                                          `PICTURE_PATH` mediumblob COMMENT '头像',
                                          `USER_REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
                                          `CREATE_TIME` bigint(20) DEFAULT NULL COMMENT '创建时间',
                                          `CREATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '创建者id',
                                          `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL COMMENT '更新时间',
                                          `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '更新者id',
                                          `EXTEND_ONE` varchar(200) DEFAULT NULL COMMENT '扩展字段1',
                                          `EXTEND_TWO` varchar(200) DEFAULT NULL COMMENT '扩展字段2',
                                          `EXTEND_THREE` varchar(200) DEFAULT NULL COMMENT '扩展字段3',
                                          `EXTEND_FOUR` varchar(200) DEFAULT NULL COMMENT '扩展字段4',
                                          `FONT_SIZE` varchar(20) DEFAULT NULL,
                                          `CSS_ID` varchar(20) DEFAULT NULL,
                                          `LAYOUT` varchar(100) DEFAULT NULL,
                                          `ENABLE_CUSTOM_MENU` varchar(10) DEFAULT NULL COMMENT 'Y 启用 N 禁用',
                                          PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ge_user_account
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_user_account`;
CREATE TABLE `icdstuiframe`.`ge_user_account` (
                                                  `ID` varchar(32) NOT NULL COMMENT 'id',
                                                  `ACCOUNT_EXPIRED` bigint(20) DEFAULT NULL COMMENT '账户是否已过期 Y.过期 N.未过期',
                                                  `ACCOUNT_LOCKED` varchar(1) NOT NULL COMMENT '是否锁定 Y.锁定 N.未锁定',
                                                  `ACCOUNT_CREDENTIAL` varchar(512) DEFAULT NULL COMMENT '证书',
                                                  `ACCOUNT_IDENTIFIER` varchar(32) NOT NULL COMMENT '账户标识',
                                                  `CREDENTIAL_EXPIRED` bigint(20) DEFAULT NULL COMMENT '证书过期时间',
                                                  `IDENTITY_TYPE` varchar(1) NOT NULL COMMENT '标识类型  1:username,2:email,3:phone,4:wechat,5:qq',
                                                  `LAST_LOGIN_IP` varchar(15) DEFAULT NULL COMMENT '最近登陆ip',
                                                  `LAST_LOGIN_TIME` varchar(32) DEFAULT NULL COMMENT '最近登陆时间',
                                                  `LOGIN_MODE` int(11) NOT NULL COMMENT '登陆模式',
                                                  `USER_ID` varchar(32) NOT NULL COMMENT '用户id',
                                                  `DELETE_FLAG` varchar(1) DEFAULT NULL COMMENT '删除标志 Y.已删除 N.未删除',
                                                  `CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间',
                                                  `CREATE_USER_ID` varchar(32) NOT NULL COMMENT '创建者id',
                                                  `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL COMMENT '更新时间',
                                                  `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '更新者id',
                                                  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ge_user_org_map
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_user_org_map`;
CREATE TABLE `icdstuiframe`.`ge_user_org_map` (
                                                  `ID` varchar(32) NOT NULL COMMENT 'id',
                                                  `USER_ID` varchar(32) DEFAULT NULL COMMENT '用户id',
                                                  `ORG_ID` varchar(32) DEFAULT NULL COMMENT '组织机构id',
                                                  `ORG_PATH` varchar(255) DEFAULT NULL COMMENT '组织机构的上下级全路径',
                                                  `CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间',
                                                  `CREATE_USER_ID` varchar(32) NOT NULL COMMENT '创建者id',
                                                  `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL COMMENT '更新时间',
                                                  `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '更新者id',
                                                  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for ge_user_role_map
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`ge_user_role_map`;
CREATE TABLE `icdstuiframe`.`ge_user_role_map` (
                                                   `ID` varchar(32) NOT NULL COMMENT 'id',
                                                   `USER_ID` varchar(32) DEFAULT NULL COMMENT '用户id',
                                                   `ROLE_ID` varchar(32) DEFAULT NULL COMMENT '角色id',
                                                   `CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间',
                                                   `CREATE_USER_ID` varchar(32) NOT NULL COMMENT '创建者id',
                                                   `LAST_UPDATE_TIME` bigint(20) DEFAULT NULL COMMENT '更新时间',
                                                   `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '更新者id',
                                                   PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for job_config
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`job_config`;
CREATE TABLE `icdstuiframe`.`job_config` (
                                             `ID` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                                             `JOB_NAME` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '任务名称',
                                             `CRON` varchar(16) COLLATE utf8mb4_bin NOT NULL COMMENT '周期表达式',
                                             `SHARDING_TOTAL_COUNT` smallint(6) DEFAULT '1' COMMENT '任务分片总数',
                                             `SHARDING_ITEM_PARAMETERS` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分片序列号和个性化参数对照表 如 0=a,1=b,2=c',
                                             `JOB_PARAMETER` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '个性化自定义参数',
                                             `FAILOVER` varchar(16) COLLATE utf8mb4_bin DEFAULT 'false' COMMENT '是否开启失效转移',
                                             `MISFIRE` varchar(16) COLLATE utf8mb4_bin DEFAULT 'true' COMMENT '是否开启misfire',
                                             `DESCRIPTION` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '描述',
                                             `JOB_CLASS` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '任务Class',
                                             `STATUS` varchar(16) COLLATE utf8mb4_bin DEFAULT 'RUNNING' COMMENT '任务状态，RUNNING、DISABLE、ENABLE、SHUTDOWN',
                                             `STREAMING_PROCESS` varchar(16) COLLATE utf8mb4_bin DEFAULT 'false' COMMENT '流处理',
                                             `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                                             `UPDATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                                             `sharding_strategy_class` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
                                             PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='任务配置表';

-- ----------------------------
-- Table structure for job_execution_log
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`job_execution_log`;
CREATE TABLE `icdstuiframe`.`job_execution_log` (
                                                    `ID` varchar(40) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                                                    `JOB_NAME` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '任务名称',
                                                    `TASK_ID` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '任务ID',
                                                    `HOSTNAME` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '机器名',
                                                    `IP` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '运行IP',
                                                    `SHARDING_ITEM` int(11) NOT NULL COMMENT '分片参数',
                                                    `EXECUTION_SOURCE` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '执行源',
                                                    `FAILURE_CAUSE` varchar(4000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '失败原因',
                                                    `IS_SUCCESS` int(11) NOT NULL COMMENT '是否成功',
                                                    `START_TIME` timestamp NULL DEFAULT NULL COMMENT '开始时间',
                                                    `COMPLETE_TIME` timestamp NULL DEFAULT NULL COMMENT '结束时间',
                                                    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='任务运行记录表';

-- ----------------------------
-- Table structure for job_status_trace_log
-- ----------------------------
DROP TABLE IF EXISTS `icdstuiframe`.`job_status_trace_log`;
CREATE TABLE `icdstuiframe`.`job_status_trace_log` (
                                                       `ID` varchar(40) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                                                       `JOB_NAME` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '任务名称',
                                                       `ORIGINAL_TASK_ID` varchar(255) COLLATE utf8mb4_bin NOT NULL,
                                                       `TASK_ID` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '任务ID',
                                                       `SLAVE_ID` varchar(50) COLLATE utf8mb4_bin NOT NULL,
                                                       `SOURCE` varchar(50) COLLATE utf8mb4_bin NOT NULL,
                                                       `EXECUTION_TYPE` varchar(20) COLLATE utf8mb4_bin NOT NULL,
                                                       `SHARDING_ITEM` varchar(100) COLLATE utf8mb4_bin NOT NULL,
                                                       `STATE` varchar(20) COLLATE utf8mb4_bin NOT NULL,
                                                       `MESSAGE` varchar(4000) COLLATE utf8mb4_bin DEFAULT NULL,
                                                       `CREATION_TIME` timestamp NULL DEFAULT NULL,
                                                       PRIMARY KEY (`ID`),
                                                       KEY `TASK_ID_STATE_INDEX` (`TASK_ID`,`STATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='任务状态变更表';
