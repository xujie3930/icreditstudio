/*
Navicat MySQL Data Transfer

Source Server         : 0.17
Source Server Version : 50734
Source Host           : 192.168.0.17:3306
Source Database       : icreditdolphinscheduler

Target Server Type    : MYSQL
Target Server Version : 50734
File Encoding         : 65001

Date: 2022-02-09 15:46:04
*/

SET FOREIGN_KEY_CHECKS=0;
create database icreditdolphinscheduler;
-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `entry_id` varchar(200) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`entry_id`) USING BTREE,
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE,
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`) USING BTREE,
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`) USING BTREE,
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE,
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for statistics_definition
-- ----------------------------
DROP TABLE IF EXISTS `statistics_definition`;
CREATE TABLE `statistics_definition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `workspace_id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `platform_task_id` varchar(32) NOT NULL,
  `date` datetime NOT NULL,
  `name` varchar(32) NOT NULL,
  `time` int(11) NOT NULL,
  `error_time` int(11) NOT NULL DEFAULT '0',
  `speed_time` decimal(11,4) NOT NULL DEFAULT '0.0000',
  `schedule_type` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2418 DEFAULT CHARSET=utf8 COMMENT='definition统计表';

-- ----------------------------
-- Table structure for statistics_instance
-- ----------------------------
DROP TABLE IF EXISTS `statistics_instance`;
CREATE TABLE `statistics_instance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `workspace_id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `state` tinyint(4) NOT NULL,
  `date` datetime NOT NULL,
  `count` int(11) NOT NULL,
  `total_records` bigint(20) NOT NULL DEFAULT '0',
  `total_byte` bigint(20) NOT NULL DEFAULT '0',
  `schedule_type` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=787 DEFAULT CHARSET=utf8 COMMENT='instance统计表';

-- ----------------------------
-- Table structure for t_ds_alert
-- ----------------------------
DROP TABLE IF EXISTS `t_ds_alert`;
CREATE TABLE `t_ds_alert` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `title` varchar(64) DEFAULT NULL COMMENT 'title',
  `show_type` tinyint(4) DEFAULT NULL COMMENT 'send email type,0:TABLE,1:TEXT',
  `content` text COMMENT 'Message content (can be email, can be SMS. Mail is stored in JSON map, and SMS is string)',
  `alert_type` tinyint(4) DEFAULT NULL COMMENT '0:email,1:sms',
  `alert_status` tinyint(4) DEFAULT '0' COMMENT '0:wait running,1:success,2:failed',
  `log` text COMMENT 'log',
  `alertgroup_id` int(11) DEFAULT NULL COMMENT 'alert group id',
  `receivers` text COMMENT 'receivers',
  `receivers_cc` text COMMENT 'cc',
  `create_time` datetime DEFAULT NULL COMMENT 'create time',
  `update_time` datetime DEFAULT NULL COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_ds_command
-- ----------------------------
DROP TABLE IF EXISTS `t_ds_command`;
CREATE TABLE `t_ds_command` (
  `id` varchar(30) NOT NULL COMMENT 'key',
  `command_type` tinyint(4) DEFAULT NULL COMMENT 'Command type: 0 start workflow, 1 start execution from current node, 2 resume fault-tolerant workflow, 3 resume pause process, 4 start execution from failed node, 5 complement, 6 schedule, 7 rerun, 8 pause, 9 stop, 10 resume waiting thread',
  `process_definition_id` varchar(30) DEFAULT NULL COMMENT 'process definition id',
  `command_param` text COMMENT 'json command parameters',
  `task_depend_type` tinyint(4) DEFAULT NULL COMMENT 'Node dependency type: 0 current node, 1 forward, 2 backward',
  `failure_strategy` tinyint(4) DEFAULT '0' COMMENT 'Failed policy: 0 end, 1 continue',
  `warning_type` tinyint(4) DEFAULT '0' COMMENT 'Alarm type: 0 is not sent, 1 process is sent successfully, 2 process is sent failed, 3 process is sent successfully and all failures are sent',
  `warning_group_id` varchar(30) DEFAULT NULL COMMENT 'warning group',
  `schedule_time` datetime DEFAULT NULL COMMENT 'schedule time',
  `start_time` datetime DEFAULT NULL COMMENT 'start time',
  `executor_id` varchar(30) DEFAULT NULL COMMENT 'executor id',
  `dependence` varchar(255) DEFAULT NULL COMMENT 'dependence',
  `update_time` datetime DEFAULT NULL COMMENT 'update time',
  `process_instance_priority` int(11) DEFAULT NULL COMMENT 'process instance priority: 0 Highest,1 High,2 Medium,3 Low,4 Lowest',
  `worker_group` varchar(64) DEFAULT '' COMMENT 'worker group',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_ds_error_command
-- ----------------------------
DROP TABLE IF EXISTS `t_ds_error_command`;
CREATE TABLE `t_ds_error_command` (
  `id` varchar(30) NOT NULL COMMENT 'key',
  `command_type` tinyint(4) DEFAULT NULL COMMENT 'command type',
  `executor_id` varchar(30) DEFAULT NULL COMMENT 'executor id',
  `process_definition_id` varchar(30) DEFAULT NULL COMMENT 'process definition id',
  `command_param` text COMMENT 'json command parameters',
  `task_depend_type` tinyint(4) DEFAULT NULL COMMENT 'task depend type',
  `failure_strategy` tinyint(4) DEFAULT '0' COMMENT 'failure strategy',
  `warning_type` tinyint(4) DEFAULT '0' COMMENT 'warning type',
  `warning_group_id` int(11) DEFAULT NULL COMMENT 'warning group id',
  `schedule_time` datetime DEFAULT NULL COMMENT 'scheduler time',
  `start_time` datetime DEFAULT NULL COMMENT 'start time',
  `update_time` datetime DEFAULT NULL COMMENT 'update time',
  `dependence` text COMMENT 'dependence',
  `process_instance_priority` int(11) DEFAULT NULL COMMENT 'process instance priority, 0 Highest,1 High,2 Medium,3 Low,4 Lowest',
  `worker_group` varchar(64) DEFAULT '' COMMENT 'worker group',
  `message` text COMMENT 'message',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_ds_process_definition
-- ----------------------------
DROP TABLE IF EXISTS `t_ds_process_definition`;
CREATE TABLE `t_ds_process_definition` (
  `id` varchar(30) NOT NULL COMMENT 'key',
  `code` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT 'process definition name',
  `version` int(11) DEFAULT NULL COMMENT 'process definition version',
  `release_state` tinyint(4) DEFAULT NULL COMMENT 'process definition release state：0:offline,1:online',
  `project_code` varchar(50) DEFAULT NULL COMMENT '项目编码',
  `user_id` varchar(30) DEFAULT NULL COMMENT 'process definition creator id',
  `partition_param` varchar(200) DEFAULT NULL,
  `process_definition_json` longtext COMMENT 'process definition json content',
  `description` text,
  `global_params` text COMMENT 'global parameters',
  `flag` tinyint(4) DEFAULT NULL COMMENT '0 not available, 1 available',
  `locations` text COMMENT 'Node location information',
  `connects` text COMMENT 'Node connection information',
  `receivers` text COMMENT 'receivers',
  `receivers_cc` text COMMENT 'cc',
  `create_time` datetime DEFAULT NULL COMMENT 'create time',
  `timeout` int(11) DEFAULT '0' COMMENT 'time out',
  `tenant_code` varchar(50) NOT NULL DEFAULT '-1' COMMENT 'tenant id',
  `update_time` datetime DEFAULT NULL COMMENT 'update time',
  `modify_by` varchar(36) DEFAULT '' COMMENT 'modify user',
  `resource_ids` varchar(255) DEFAULT NULL COMMENT 'resource ids',
  `workspace_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '工作空间id',
  `schedule_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-同步任务，1-开发任务，2-治理任务',
  `platform_task_id` varchar(30) CHARACTER SET utf16 DEFAULT NULL,
  `cron` varchar(50) DEFAULT NULL,
  `target_table` varchar(100) DEFAULT NULL,
  `source_table` varchar(255) DEFAULT NULL,
  `task_type` tinyint(4) DEFAULT NULL COMMENT '1-周期，0-手动',
  PRIMARY KEY (`id`),
  KEY `process_definition_index` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_ds_process_instance
-- ----------------------------
DROP TABLE IF EXISTS `t_ds_process_instance`;
CREATE TABLE `t_ds_process_instance` (
  `id` varchar(30) NOT NULL COMMENT 'key',
  `name` varchar(255) DEFAULT NULL COMMENT 'process instance name',
  `process_definition_id` varchar(30) DEFAULT NULL COMMENT 'process definition id',
  `state` tinyint(4) DEFAULT NULL COMMENT 'process instance Status: 0 commit succeeded, 1 running, 2 prepare to pause, 3 pause, 4 prepare to stop, 5 stop, 6 fail, 7 succeed, 8 need fault tolerance, 9 kill, 10 wait for thread, 11 wait for dependency to complete',
  `recovery` tinyint(4) DEFAULT NULL COMMENT 'process instance failover flag：0:normal,1:failover instance',
  `start_time` datetime DEFAULT NULL COMMENT 'process instance start time',
  `end_time` datetime DEFAULT NULL COMMENT 'process instance end time',
  `run_times` int(11) DEFAULT NULL COMMENT 'process instance run times',
  `host` varchar(135) DEFAULT NULL,
  `command_type` tinyint(4) DEFAULT NULL COMMENT 'command type',
  `command_param` text COMMENT 'json command parameters',
  `task_depend_type` tinyint(4) DEFAULT NULL COMMENT 'task depend type. 0: only current node,1:before the node,2:later nodes',
  `max_try_times` tinyint(4) DEFAULT '0' COMMENT 'max try times',
  `failure_strategy` tinyint(4) DEFAULT '0' COMMENT 'failure strategy. 0:end the process when node failed,1:continue running the other nodes when node failed',
  `warning_type` tinyint(4) DEFAULT '0' COMMENT 'warning type. 0:no warning,1:warning if process success,2:warning if process failed,3:warning if success',
  `warning_group_id` varchar(30) DEFAULT NULL COMMENT 'warning group id',
  `schedule_time` datetime DEFAULT NULL COMMENT 'schedule time',
  `command_start_time` datetime DEFAULT NULL COMMENT 'command start time',
  `global_params` text COMMENT 'global parameters',
  `process_instance_json` longtext COMMENT 'process instance json(copy的process definition 的json)',
  `flag` tinyint(4) DEFAULT '1' COMMENT 'flag',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_sub_process` int(11) DEFAULT '0' COMMENT 'flag, whether the process is sub process',
  `executor_id` varchar(30) NOT NULL COMMENT 'executor id',
  `locations` text COMMENT 'Node location information',
  `connects` text COMMENT 'Node connection information',
  `history_cmd` text COMMENT 'history commands of process instance operation',
  `dependence_schedule_times` text COMMENT 'depend schedule fire time',
  `process_instance_priority` int(11) DEFAULT NULL COMMENT 'process instance priority. 0 Highest,1 High,2 Medium,3 Low,4 Lowest',
  `worker_group` varchar(64) DEFAULT '' COMMENT 'worker group',
  `timeout` int(11) DEFAULT '0' COMMENT 'time out',
  `tenant_code` varchar(50) NOT NULL DEFAULT '-1' COMMENT 'tenant id',
  `workspace_id` varchar(30) DEFAULT NULL,
  `file_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `process_instance_index` (`process_definition_id`,`id`),
  KEY `start_time_index` (`start_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_ds_schedules
-- ----------------------------
DROP TABLE IF EXISTS `t_ds_schedules`;
CREATE TABLE `t_ds_schedules` (
  `id` varchar(30) NOT NULL COMMENT 'key',
  `process_definition_id` varchar(30) NOT NULL COMMENT 'process definition id',
  `start_time` datetime NOT NULL COMMENT 'start time',
  `end_time` datetime NOT NULL COMMENT 'end time',
  `timezone_id` varchar(40) DEFAULT NULL COMMENT 'schedule timezone id',
  `crontab` varchar(255) NOT NULL COMMENT 'crontab description',
  `failure_strategy` tinyint(4) NOT NULL COMMENT 'failure strategy. 0:end,1:continue',
  `user_id` varchar(30) NOT NULL COMMENT 'user id',
  `release_state` tinyint(4) NOT NULL COMMENT 'release state. 0:offline,1:online ',
  `warning_type` tinyint(4) NOT NULL COMMENT 'Alarm type: 0 is not sent, 1 process is sent successfully, 2 process is sent failed, 3 process is sent successfully and all failures are sent',
  `warning_group_id` varchar(30) DEFAULT NULL COMMENT 'alert group id',
  `process_instance_priority` int(11) DEFAULT NULL COMMENT 'process instance priority：0 Highest,1 High,2 Medium,3 Low,4 Lowest',
  `worker_group` varchar(64) DEFAULT '' COMMENT 'worker group id',
  `create_time` datetime NOT NULL COMMENT 'create time',
  `update_time` datetime NOT NULL COMMENT 'update time',
  `workspace_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '工作空间id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_ds_task_instance
-- ----------------------------
DROP TABLE IF EXISTS `t_ds_task_instance`;
CREATE TABLE `t_ds_task_instance` (
  `id` varchar(30) NOT NULL COMMENT 'key',
  `task_code` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT 'task name',
  `task_type` varchar(64) DEFAULT NULL COMMENT 'task type',
  `process_definition_id` varchar(30) DEFAULT NULL COMMENT 'process definition id',
  `process_instance_id` varchar(30) DEFAULT NULL COMMENT 'process instance id',
  `task_json` longtext COMMENT 'task content json',
  `state` tinyint(4) DEFAULT NULL COMMENT 'Status: 0 commit succeeded, 1 running, 2 prepare to pause, 3 pause, 4 prepare to stop, 5 stop, 6 fail, 7 succeed, 8 need fault tolerance, 9 kill, 10 wait for thread, 11 wait for dependency to complete',
  `submit_time` datetime DEFAULT NULL COMMENT 'task submit time',
  `start_time` datetime DEFAULT NULL COMMENT 'task start time',
  `end_time` datetime DEFAULT NULL COMMENT 'task end time',
  `host` varchar(135) DEFAULT NULL,
  `execute_path` varchar(200) DEFAULT NULL COMMENT 'task execute path in the host',
  `log_path` varchar(200) DEFAULT NULL COMMENT 'task log path',
  `alert_flag` tinyint(4) DEFAULT NULL COMMENT 'whether alert',
  `retry_times` int(4) DEFAULT '0' COMMENT 'task retry times',
  `pid` int(4) DEFAULT NULL COMMENT 'pid of task',
  `app_link` text COMMENT 'yarn app id',
  `flag` tinyint(4) DEFAULT '1' COMMENT '0 not available, 1 available',
  `retry_interval` int(4) DEFAULT NULL COMMENT 'retry interval when task failed ',
  `max_retry_times` int(2) DEFAULT NULL COMMENT 'max retry times',
  `task_instance_priority` int(11) DEFAULT NULL COMMENT 'task instance priority:0 Highest,1 High,2 Medium,3 Low,4 Lowest',
  `worker_group` varchar(64) DEFAULT '' COMMENT 'worker group',
  `executor_id` varchar(30) DEFAULT NULL COMMENT 'executor id',
  `total_records` bigint(20) DEFAULT NULL,
  `total_bytes` bigint(20) DEFAULT NULL,
  `workspace_id` varchar(30) DEFAULT NULL,
  `scan_state` tinyint(4) DEFAULT '0' COMMENT '扫描状态：0-未扫描，1-已扫描',
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `process_instance_id` (`process_instance_id`),
  KEY `task_instance_index` (`process_definition_id`,`process_instance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_ds_version
-- ----------------------------
DROP TABLE IF EXISTS `t_ds_version`;
CREATE TABLE `t_ds_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` varchar(200) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `version_UNIQUE` (`version`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='version';
