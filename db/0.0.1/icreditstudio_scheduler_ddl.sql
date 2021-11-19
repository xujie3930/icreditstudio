/*
 Navicat MySQL Data Transfer

 Source Server         : 17环境
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 192.168.0.17:3306
 Source Schema         : icreditdolphinscheduler

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 19/11/2021 15:32:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE IF NOT EXISTS icreditdolphinscheduler DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`qrtz_blob_triggers`;
CREATE TABLE `icreditdolphinscheduler`.`qrtz_blob_triggers`  (
                                                                 `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                 `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                 `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                 `BLOB_DATA` blob NULL,
                                                                 PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
                                                                 INDEX `SCHED_NAME`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
                                                                 CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`qrtz_calendars`;
CREATE TABLE `icreditdolphinscheduler`.`qrtz_calendars`  (
                                                             `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                             `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                             `CALENDAR` blob NOT NULL,
                                                             PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`qrtz_cron_triggers`;
CREATE TABLE `icreditdolphinscheduler`.`qrtz_cron_triggers`  (
                                                                 `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                 `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                 `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                 `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                 `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                                 PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
                                                                 CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`qrtz_fired_triggers`;
CREATE TABLE `icreditdolphinscheduler`.`qrtz_fired_triggers`  (
                                                                  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                  `entry_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                  `FIRED_TIME` bigint(13) NOT NULL,
                                                                  `SCHED_TIME` bigint(13) NOT NULL,
                                                                  `PRIORITY` int(11) NOT NULL,
                                                                  `STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                                  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                                  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                                  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                                  PRIMARY KEY (`SCHED_NAME`, `entry_id`) USING BTREE,
                                                                  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE,
                                                                  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
                                                                  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
                                                                  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
                                                                  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
                                                                  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`qrtz_job_details`;
CREATE TABLE `icreditdolphinscheduler`.`qrtz_job_details`  (
                                                               `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                               `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                               `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                               `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                               `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                               `IS_DURABLE` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                               `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                               `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                               `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                               `JOB_DATA` blob NULL,
                                                               PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
                                                               INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
                                                               INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`qrtz_locks`;
CREATE TABLE `icreditdolphinscheduler`.`qrtz_locks`  (
                                                         `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                         `LOCK_NAME` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                         PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`qrtz_paused_trigger_grps`;
CREATE TABLE `icreditdolphinscheduler`.`qrtz_paused_trigger_grps`  (
                                                                       `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                       `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                       PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`qrtz_scheduler_state`;
CREATE TABLE `icreditdolphinscheduler`.`qrtz_scheduler_state`  (
                                                                   `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                   `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                   `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
                                                                   `CHECKIN_INTERVAL` bigint(13) NOT NULL,
                                                                   PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`qrtz_simple_triggers`;
CREATE TABLE `icreditdolphinscheduler`.`qrtz_simple_triggers`  (
                                                                   `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                   `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                   `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                   `REPEAT_COUNT` bigint(7) NOT NULL,
                                                                   `REPEAT_INTERVAL` bigint(12) NOT NULL,
                                                                   `TIMES_TRIGGERED` bigint(10) NOT NULL,
                                                                   PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
                                                                   CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`qrtz_simprop_triggers`;
CREATE TABLE `icreditdolphinscheduler`.`qrtz_simprop_triggers`  (
                                                                    `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                    `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                    `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                                    `STR_PROP_1` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                                    `STR_PROP_2` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                                    `STR_PROP_3` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                                    `INT_PROP_1` int(11) NULL DEFAULT NULL,
                                                                    `INT_PROP_2` int(11) NULL DEFAULT NULL,
                                                                    `LONG_PROP_1` bigint(20) NULL DEFAULT NULL,
                                                                    `LONG_PROP_2` bigint(20) NULL DEFAULT NULL,
                                                                    `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
                                                                    `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
                                                                    `BOOL_PROP_1` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                                    `BOOL_PROP_2` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                                    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
                                                                    CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`qrtz_triggers`;
CREATE TABLE `icreditdolphinscheduler`.`qrtz_triggers`  (
                                                            `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                            `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                            `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                            `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                            `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                            `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                            `NEXT_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
                                                            `PREV_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
                                                            `PRIORITY` int(11) NULL DEFAULT NULL,
                                                            `TRIGGER_STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                            `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                            `START_TIME` bigint(13) NOT NULL,
                                                            `END_TIME` bigint(13) NULL DEFAULT NULL,
                                                            `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                            `MISFIRE_INSTR` smallint(2) NULL DEFAULT NULL,
                                                            `JOB_DATA` blob NULL,
                                                            PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
                                                            INDEX `IDX_QRTZ_T_J`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
                                                            INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
                                                            INDEX `IDX_QRTZ_T_C`(`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE,
                                                            INDEX `IDX_QRTZ_T_G`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE,
                                                            INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME`, `TRIGGER_STATE`) USING BTREE,
                                                            INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
                                                            INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
                                                            INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME`, `NEXT_FIRE_TIME`) USING BTREE,
                                                            INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`) USING BTREE,
                                                            INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`) USING BTREE,
                                                            INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`) USING BTREE,
                                                            INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
                                                            CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_ds_alert
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`t_ds_alert`;
CREATE TABLE `icreditdolphinscheduler`.`t_ds_alert`  (
                                                         `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
                                                         `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'title',
                                                         `show_type` tinyint(4) NULL DEFAULT NULL COMMENT 'send email type,0:TABLE,1:TEXT',
                                                         `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Message content (can be email, can be SMS. Mail is stored in JSON map, and SMS is string)',
                                                         `alert_type` tinyint(4) NULL DEFAULT NULL COMMENT '0:email,1:sms',
                                                         `alert_status` tinyint(4) NULL DEFAULT 0 COMMENT '0:wait running,1:success,2:failed',
                                                         `log` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'log',
                                                         `alertgroup_id` int(11) NULL DEFAULT NULL COMMENT 'alert group id',
                                                         `receivers` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'receivers',
                                                         `receivers_cc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'cc',
                                                         `create_time` datetime NULL DEFAULT NULL COMMENT 'create time',
                                                         `update_time` datetime NULL DEFAULT NULL COMMENT 'update time',
                                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_ds_command
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`t_ds_command`;
CREATE TABLE `icreditdolphinscheduler`.`t_ds_command`  (
                                                           `id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'key',
                                                           `command_type` tinyint(4) NULL DEFAULT NULL COMMENT 'Command type: 0 start workflow, 1 start execution from current node, 2 resume fault-tolerant workflow, 3 resume pause process, 4 start execution from failed node, 5 complement, 6 schedule, 7 rerun, 8 pause, 9 stop, 10 resume waiting thread',
                                                           `process_definition_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'process definition id',
                                                           `command_param` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'json command parameters',
                                                           `task_depend_type` tinyint(4) NULL DEFAULT NULL COMMENT 'Node dependency type: 0 current node, 1 forward, 2 backward',
                                                           `failure_strategy` tinyint(4) NULL DEFAULT 0 COMMENT 'Failed policy: 0 end, 1 continue',
                                                           `warning_type` tinyint(4) NULL DEFAULT 0 COMMENT 'Alarm type: 0 is not sent, 1 process is sent successfully, 2 process is sent failed, 3 process is sent successfully and all failures are sent',
                                                           `warning_group_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'warning group',
                                                           `schedule_time` datetime NULL DEFAULT NULL COMMENT 'schedule time',
                                                           `start_time` datetime NULL DEFAULT NULL COMMENT 'start time',
                                                           `executor_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'executor id',
                                                           `dependence` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'dependence',
                                                           `update_time` datetime NULL DEFAULT NULL COMMENT 'update time',
                                                           `process_instance_priority` int(11) NULL DEFAULT NULL COMMENT 'process instance priority: 0 Highest,1 High,2 Medium,3 Low,4 Lowest',
                                                           `worker_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'worker group',
                                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_ds_error_command
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`t_ds_error_command`;
CREATE TABLE `icreditdolphinscheduler`.`t_ds_error_command`  (
                                                                 `id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'key',
                                                                 `command_type` tinyint(4) NULL DEFAULT NULL COMMENT 'command type',
                                                                 `executor_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'executor id',
                                                                 `process_definition_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'process definition id',
                                                                 `command_param` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'json command parameters',
                                                                 `task_depend_type` tinyint(4) NULL DEFAULT NULL COMMENT 'task depend type',
                                                                 `failure_strategy` tinyint(4) NULL DEFAULT 0 COMMENT 'failure strategy',
                                                                 `warning_type` tinyint(4) NULL DEFAULT 0 COMMENT 'warning type',
                                                                 `warning_group_id` int(11) NULL DEFAULT NULL COMMENT 'warning group id',
                                                                 `schedule_time` datetime NULL DEFAULT NULL COMMENT 'scheduler time',
                                                                 `start_time` datetime NULL DEFAULT NULL COMMENT 'start time',
                                                                 `update_time` datetime NULL DEFAULT NULL COMMENT 'update time',
                                                                 `dependence` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'dependence',
                                                                 `process_instance_priority` int(11) NULL DEFAULT NULL COMMENT 'process instance priority, 0 Highest,1 High,2 Medium,3 Low,4 Lowest',
                                                                 `worker_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'worker group',
                                                                 `message` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'message',
                                                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_ds_process_definition
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`t_ds_process_definition`;
CREATE TABLE `icreditdolphinscheduler`.`t_ds_process_definition`  (
                                                                      `id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'key',
                                                                      `code` bigint(20) NULL DEFAULT NULL,
                                                                      `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'process definition name',
                                                                      `version` int(11) NULL DEFAULT NULL COMMENT 'process definition version',
                                                                      `release_state` tinyint(4) NULL DEFAULT NULL COMMENT 'process definition release state：0:offline,1:online',
                                                                      `project_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目编码',
                                                                      `user_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'process definition creator id',
                                                                      `partition_param` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                                      `process_definition_json` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'process definition json content',
                                                                      `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                                                      `global_params` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'global parameters',
                                                                      `flag` tinyint(4) NULL DEFAULT NULL COMMENT '0 not available, 1 available',
                                                                      `locations` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Node location information',
                                                                      `connects` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Node connection information',
                                                                      `receivers` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'receivers',
                                                                      `receivers_cc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'cc',
                                                                      `create_time` datetime NULL DEFAULT NULL COMMENT 'create time',
                                                                      `timeout` int(11) NULL DEFAULT 0 COMMENT 'time out',
                                                                      `tenant_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '-1' COMMENT 'tenant id',
                                                                      `update_time` datetime NULL DEFAULT NULL COMMENT 'update time',
                                                                      `modify_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'modify user',
                                                                      `resource_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'resource ids',
                                                                      `workspace_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '工作空间id',
                                                                      `schedule_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0-同步任务，1-开发任务，2-治理任务',
                                                                      `platform_task_id` varchar(30) CHARACTER SET utf16 COLLATE utf16_general_ci NULL DEFAULT NULL,
                                                                      PRIMARY KEY (`id`) USING BTREE,
                                                                      INDEX `process_definition_index`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_ds_process_instance
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`t_ds_process_instance`;
CREATE TABLE `icreditdolphinscheduler`.`t_ds_process_instance`  (
                                                                    `id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'key',
                                                                    `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'process instance name',
                                                                    `process_definition_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'process definition id',
                                                                    `state` tinyint(4) NULL DEFAULT NULL COMMENT 'process instance Status: 0 commit succeeded, 1 running, 2 prepare to pause, 3 pause, 4 prepare to stop, 5 stop, 6 fail, 7 succeed, 8 need fault tolerance, 9 kill, 10 wait for thread, 11 wait for dependency to complete',
                                                                    `recovery` tinyint(4) NULL DEFAULT NULL COMMENT 'process instance failover flag：0:normal,1:failover instance',
                                                                    `start_time` datetime NULL DEFAULT NULL COMMENT 'process instance start time',
                                                                    `end_time` datetime NULL DEFAULT NULL COMMENT 'process instance end time',
                                                                    `run_times` int(11) NULL DEFAULT NULL COMMENT 'process instance run times',
                                                                    `host` varchar(135) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                                    `command_type` tinyint(4) NULL DEFAULT NULL COMMENT 'command type',
                                                                    `command_param` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'json command parameters',
                                                                    `task_depend_type` tinyint(4) NULL DEFAULT NULL COMMENT 'task depend type. 0: only current node,1:before the node,2:later nodes',
                                                                    `max_try_times` tinyint(4) NULL DEFAULT 0 COMMENT 'max try times',
                                                                    `failure_strategy` tinyint(4) NULL DEFAULT 0 COMMENT 'failure strategy. 0:end the process when node failed,1:continue running the other nodes when node failed',
                                                                    `warning_type` tinyint(4) NULL DEFAULT 0 COMMENT 'warning type. 0:no warning,1:warning if process success,2:warning if process failed,3:warning if success',
                                                                    `warning_group_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'warning group id',
                                                                    `schedule_time` datetime NULL DEFAULT NULL COMMENT 'schedule time',
                                                                    `command_start_time` datetime NULL DEFAULT NULL COMMENT 'command start time',
                                                                    `global_params` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'global parameters',
                                                                    `process_instance_json` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'process instance json(copy的process definition 的json)',
                                                                    `flag` tinyint(4) NULL DEFAULT 1 COMMENT 'flag',
                                                                    `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                                                    `is_sub_process` int(11) NULL DEFAULT 0 COMMENT 'flag, whether the process is sub process',
                                                                    `executor_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'executor id',
                                                                    `locations` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Node location information',
                                                                    `connects` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Node connection information',
                                                                    `history_cmd` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'history commands of process instance operation',
                                                                    `dependence_schedule_times` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'depend schedule fire time',
                                                                    `process_instance_priority` int(11) NULL DEFAULT NULL COMMENT 'process instance priority. 0 Highest,1 High,2 Medium,3 Low,4 Lowest',
                                                                    `worker_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'worker group',
                                                                    `timeout` int(11) NULL DEFAULT 0 COMMENT 'time out',
                                                                    `tenant_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '-1' COMMENT 'tenant id',
                                                                    `workspace_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                                    `file_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                                    PRIMARY KEY (`id`) USING BTREE,
                                                                    INDEX `process_instance_index`(`process_definition_id`, `id`) USING BTREE,
                                                                    INDEX `start_time_index`(`start_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_ds_schedules
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`t_ds_schedules`;
CREATE TABLE `icreditdolphinscheduler`.`t_ds_schedules`  (
                                                             `id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'key',
                                                             `process_definition_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'process definition id',
                                                             `start_time` datetime NOT NULL COMMENT 'start time',
                                                             `end_time` datetime NOT NULL COMMENT 'end time',
                                                             `timezone_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'schedule timezone id',
                                                             `crontab` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'crontab description',
                                                             `failure_strategy` tinyint(4) NOT NULL COMMENT 'failure strategy. 0:end,1:continue',
                                                             `user_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'user id',
                                                             `release_state` tinyint(4) NOT NULL COMMENT 'release state. 0:offline,1:online ',
                                                             `warning_type` tinyint(4) NOT NULL COMMENT 'Alarm type: 0 is not sent, 1 process is sent successfully, 2 process is sent failed, 3 process is sent successfully and all failures are sent',
                                                             `warning_group_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'alert group id',
                                                             `process_instance_priority` int(11) NULL DEFAULT NULL COMMENT 'process instance priority：0 Highest,1 High,2 Medium,3 Low,4 Lowest',
                                                             `worker_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'worker group id',
                                                             `create_time` datetime NOT NULL COMMENT 'create time',
                                                             `update_time` datetime NOT NULL COMMENT 'update time',
                                                             `workspace_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '工作空间id',
                                                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_ds_task_instance
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`t_ds_task_instance`;
CREATE TABLE `icreditdolphinscheduler`.`t_ds_task_instance`  (
                                                                 `id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'key',
                                                                 `task_code` bigint(20) NULL DEFAULT NULL,
                                                                 `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'task name',
                                                                 `task_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'task type',
                                                                 `process_definition_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'process definition id',
                                                                 `process_instance_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'process instance id',
                                                                 `task_json` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'task content json',
                                                                 `state` tinyint(4) NULL DEFAULT NULL COMMENT 'Status: 0 commit succeeded, 1 running, 2 prepare to pause, 3 pause, 4 prepare to stop, 5 stop, 6 fail, 7 succeed, 8 need fault tolerance, 9 kill, 10 wait for thread, 11 wait for dependency to complete',
                                                                 `submit_time` datetime NULL DEFAULT NULL COMMENT 'task submit time',
                                                                 `start_time` datetime NULL DEFAULT NULL COMMENT 'task start time',
                                                                 `end_time` datetime NULL DEFAULT NULL COMMENT 'task end time',
                                                                 `host` varchar(135) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                                 `execute_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'task execute path in the host',
                                                                 `log_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'task log path',
                                                                 `alert_flag` tinyint(4) NULL DEFAULT NULL COMMENT 'whether alert',
                                                                 `retry_times` int(4) NULL DEFAULT 0 COMMENT 'task retry times',
                                                                 `pid` int(4) NULL DEFAULT NULL COMMENT 'pid of task',
                                                                 `app_link` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'yarn app id',
                                                                 `flag` tinyint(4) NULL DEFAULT 1 COMMENT '0 not available, 1 available',
                                                                 `retry_interval` int(4) NULL DEFAULT NULL COMMENT 'retry interval when task failed ',
                                                                 `max_retry_times` int(2) NULL DEFAULT NULL COMMENT 'max retry times',
                                                                 `task_instance_priority` int(11) NULL DEFAULT NULL COMMENT 'task instance priority:0 Highest,1 High,2 Medium,3 Low,4 Lowest',
                                                                 `worker_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'worker group',
                                                                 `executor_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'executor id',
                                                                 `total_records` bigint(20) NULL DEFAULT NULL,
                                                                 `total_bytes` bigint(20) NULL DEFAULT NULL,
                                                                 `workspace_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                                 PRIMARY KEY (`id`) USING BTREE,
                                                                 INDEX `process_instance_id`(`process_instance_id`) USING BTREE,
                                                                 INDEX `task_instance_index`(`process_definition_id`, `process_instance_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_ds_version
-- ----------------------------
DROP TABLE IF EXISTS `icreditdolphinscheduler`.`t_ds_version`;
CREATE TABLE `icreditdolphinscheduler`.`t_ds_version`  (
                                                           `id` int(11) NOT NULL AUTO_INCREMENT,
                                                           `version` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                           PRIMARY KEY (`id`) USING BTREE,
                                                           UNIQUE INDEX `version_UNIQUE`(`version`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'version' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
