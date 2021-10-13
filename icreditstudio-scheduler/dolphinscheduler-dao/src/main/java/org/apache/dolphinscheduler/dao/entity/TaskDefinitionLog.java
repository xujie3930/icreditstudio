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

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * task definition log
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_ds_task_definition_log")
public class TaskDefinitionLog extends TaskDefinition {

    /**
     * operator user id
     */
    private String operator;

    /**
     * operate time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date operateTime;

    public TaskDefinitionLog(TaskDefinition taskDefinition) {
        super();
        this.setId(taskDefinition.getId());
        this.setWorkspaceId(taskDefinition.getWorkspaceId());
        this.setCode(taskDefinition.getCode());
        this.setVersion(taskDefinition.getVersion());
        this.setName(taskDefinition.getName());
        this.setDescription(taskDefinition.getDescription());
        this.setUserId(taskDefinition.getUserId());
        this.setUserName(taskDefinition.getUserName());
        this.setWorkerGroup(taskDefinition.getWorkerGroup());
        this.setProjectCode(taskDefinition.getProjectCode());
        this.setProjectName(taskDefinition.getProjectName());
        this.setResourceIds(taskDefinition.getResourceIds());
        this.setTaskParams(taskDefinition.getTaskParams());
        this.setTaskParamList(taskDefinition.getTaskParamList());
        this.setTaskParamMap(taskDefinition.getTaskParamMap());
        this.setTaskPriority(taskDefinition.getTaskPriority());
        this.setTimeoutNotifyStrategy(taskDefinition.getTimeoutNotifyStrategy());
        this.setTaskType(taskDefinition.getTaskType());
        this.setTimeout(taskDefinition.getTimeout());
        this.setDelayTime(taskDefinition.getDelayTime());
        this.setTimeoutFlag(taskDefinition.getTimeoutFlag());
        this.setUpdateTime(taskDefinition.getUpdateTime());
        this.setCreateTime(taskDefinition.getCreateTime());
        this.setFailRetryInterval(taskDefinition.getFailRetryInterval());
        this.setFailRetryTimes(taskDefinition.getFailRetryTimes());
        this.setFlag(taskDefinition.getFlag());
    }
}
