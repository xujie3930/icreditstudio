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
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.dolphinscheduler.common.enums.*;
import org.apache.dolphinscheduler.common.utils.DateUtils;

import java.util.Date;

/**
 * process instance
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_ds_process_instance")
public class ProcessInstance {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * process definition 表对应的id
     */
    private String processDefinitionId;

    /**
     * process definition code
     */
    private Long processDefinitionCode;

    /**
     * process definition version
     */
    private int processDefinitionVersion;

    /**
     * process state
     */
    private ExecutionStatus state;
    /**
     * recovery flag for failover
     */
    private Flag recovery;
    /**
     * start time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * end time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * run time
     */
    private int runTimes;

    /**
     * name
     */
    private String name;

    /**
     * host
     */
    private String host;

    /**
     * process definition structure
     */
    @TableField(exist = false)
    private ProcessDefinition processDefinition;
    /**
     * process command type
     */
    private CommandType commandType;

    /**
     * command parameters
     */
    private String commandParam;

    /**
     * node depend type
     */
    private TaskDependType taskDependType;

    /**
     * task max try times
     */
    private int maxTryTimes;

    /**
     * failure strategy when task failed.
     */
    private FailureStrategy failureStrategy;

    /**
     * warning type
     */
    private WarningType warningType;

    /**
     * warning group
     */
    private String warningGroupId;

    /**
     * schedule time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date scheduleTime;

    /**
     * command start time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date commandStartTime;

    /**
     * user define parameters string
     */
    private String globalParams;

    /**
     * process instance json
     * TODO delete
     */
    @TableField(exist = false)
    private String processInstanceJson;

    /**
     * executor id
     */
    private String executorId;

    /**
     * executor name
     */
    @TableField(exist = false)
    private String executorName;

    /**
     * tenant code
     */
    @TableField(exist = false)
    private String tenantCode;

    /**
     * queue
     */
    @TableField(exist = false)
    private String queue;

    /**
     * process is sub process
     */
    private Flag isSubProcess;

    /**
     * task locations for web
     */
    @TableField(exist = false)
    private String locations;

    /**
     * task connects for web
     */
    @TableField(exist = false)
    private String connects;

    /**
     * history command
     */
    private String historyCmd;

    /**
     * depend processes schedule time
     */
    @TableField(exist = false)
    private String dependenceScheduleTimes;

    /**
     * process duration
     *
     * @return
     */
    @TableField(exist = false)
    private String duration;

    /**
     * process instance priority
     */
    private Priority processInstancePriority;

    /**
     * worker group
     */
    private String workerGroup;

    /**
     * process timeout for warning
     */
    private int timeout;

    /**
     * tenant id
     */
    private int tenantId;

    /**
     * varPool string
     */
    private String varPool;

    /**
     * set the process name with process define version and timestamp
     *
     * @param processDefinition processDefinition
     */
    public ProcessInstance(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
        this.name = processDefinition.getName()
                + "-"
                +
                processDefinition.getVersion()
                + "-"
                +
                DateUtils.getCurrentTimeStamp();
    }

    public boolean isProcessInstanceStop() {
        return this.state.typeIsFinished();
    }

    /**
     * add command to history
     *
     * @param cmd cmd
     */
    public void addHistoryCmd(CommandType cmd) {
        if (StringUtils.isNotEmpty(this.historyCmd)) {
            this.historyCmd = String.format("%s,%s", this.historyCmd, cmd.toString());
        } else {
            this.historyCmd = cmd.toString();
        }
    }

    /**
     * check this process is start complement data
     *
     * @return whether complement data
     */
    public boolean isComplementData() {
        if (StringUtils.isEmpty(this.historyCmd)) {
            return false;
        }
        return historyCmd.startsWith(CommandType.COMPLEMENT_DATA.toString());
    }

    /**
     * get current command type,
     * if start with complement data,return complement
     *
     * @return CommandType
     */
    public CommandType getCmdTypeIfComplement() {
        if (isComplementData()) {
            return CommandType.COMPLEMENT_DATA;
        }
        return commandType;
    }
}
