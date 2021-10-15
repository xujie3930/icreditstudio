/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dolphinscheduler.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.dolphinscheduler.common.enums.*;

import java.util.Date;

/**
 * command
 */
@Data
@TableName("t_ds_command")
public class Command {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * command type
     */
    @TableField("command_type")
    private CommandType commandType;

    /**
     * process definition id
     */
    @TableField("process_definition_id")
    private String processDefinitionId;

    /**
     * executor id
     */
    @TableField("executor_id")
    private String executorId;

    /**
     * command parameter, format json
     */
    @TableField("command_param")
    private String commandParam;

    /**
     * task depend type
     */
    @TableField("task_depend_type")
    private TaskDependType taskDependType;

    /**
     * failure strategy
     */
    @TableField("failure_strategy")
    private FailureStrategy failureStrategy;

    /**
     * warning type
     */
    @TableField("warning_type")
    private WarningType warningType;

    /**
     * warning group id
     */
    @TableField("warning_group_id")
    private String warningGroupId;

    /**
     * schedule time
     */
    @TableField("schedule_time")
    private Date scheduleTime;

    /**
     * start time
     */
    @TableField("start_time")
    private Date startTime;

    /**
     * process instance priority
     */
    @TableField("process_instance_priority")
    private Priority processInstancePriority;

    /**
     * update time
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * worker group
     */
    @TableField("worker_group")
    private String workerGroup;

    public Command() {
        this.taskDependType = TaskDependType.TASK_POST;
        this.failureStrategy = FailureStrategy.CONTINUE;
        this.startTime = new Date();
        this.updateTime = new Date();
    }

    public Command(
            CommandType commandType,
            TaskDependType taskDependType,
            FailureStrategy failureStrategy,
            String executorId,
            String processDefinitionId,
            String commandParam,
            WarningType warningType,
            String warningGroupId,
            Date scheduleTime,
            String workerGroup,
            Priority processInstancePriority) {
        this.commandType = commandType;
        this.executorId = executorId;
        this.processDefinitionId = processDefinitionId;
        this.commandParam = commandParam;
        this.warningType = warningType;
        this.warningGroupId = warningGroupId;
        this.scheduleTime = scheduleTime;
        this.taskDependType = taskDependType;
        this.failureStrategy = failureStrategy;
        this.startTime = new Date();
        this.updateTime = new Date();
        this.workerGroup = workerGroup;
        this.processInstancePriority = processInstancePriority;
    }
}

