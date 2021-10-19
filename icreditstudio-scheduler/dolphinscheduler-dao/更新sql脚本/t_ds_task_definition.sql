DROP TABLE IF EXISTS `t_ds_task_definition`;
CREATE TABLE `t_ds_task_definition` (
                                        `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'self-increasing id',
                                        `code` bigint(20) NOT NULL COMMENT 'encoding',
                                        `name` varchar(200) DEFAULT NULL COMMENT 'task definition name',
                                        `version` int(11) DEFAULT NULL COMMENT 'task definition version',
                                        `description` text COMMENT 'description',
                                        `project_code` bigint(20) NOT NULL COMMENT 'project code',
                                        `user_id` int(11) DEFAULT NULL COMMENT 'task definition creator id',
                                        `task_type` varchar(50) NOT NULL COMMENT 'task type',
                                        `task_params` longtext COMMENT 'job custom parameters',
                                        `flag` tinyint(2) DEFAULT NULL COMMENT '0 not available, 1 available',
                                        `task_priority` tinyint(4) DEFAULT NULL COMMENT 'job priority',
                                        `worker_group` varchar(200) DEFAULT NULL COMMENT 'worker grouping',
                                        `fail_retry_times` int(11) DEFAULT NULL COMMENT 'number of failed retries',
                                        `fail_retry_interval` int(11) DEFAULT NULL COMMENT 'failed retry interval',
                                        `timeout_flag` tinyint(2) DEFAULT '0' COMMENT 'timeout flag:0 close, 1 open',
                                        `timeout_notify_strategy` tinyint(4) DEFAULT NULL COMMENT 'timeout notification policy: 0 warning, 1 fail',
                                        `timeout` int(11) DEFAULT '0' COMMENT 'timeout length,unit: minute',
                                        `delay_time` int(11) DEFAULT '0' COMMENT 'delay execution time,unit: minute',
                                        `resource_ids` varchar(255) DEFAULT NULL COMMENT 'resource id, separated by comma',
                                        `create_time` datetime NOT NULL COMMENT 'create time',
                                        `update_time` datetime DEFAULT NULL COMMENT 'update time',
                                        `workspace_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '工作空间id',
                                        `scheduleType` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0-周期实例，1-手动实例',
                                        PRIMARY KEY (`id`,`code`),
                                        UNIQUE KEY `task_unique` (`name`,`project_code`) USING BTREE,
                                        KEY `task_definition_index` (`project_code`,`id`)
) ENGINE=InnoDB AUTO_INCREMENT=318 DEFAULT CHARSET=utf8;


