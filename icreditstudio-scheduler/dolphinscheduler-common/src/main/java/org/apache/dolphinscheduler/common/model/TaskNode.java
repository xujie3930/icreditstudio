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
package org.apache.dolphinscheduler.common.model;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.common.enums.Priority;
import org.apache.dolphinscheduler.common.enums.TaskTimeoutStrategy;
import org.apache.dolphinscheduler.common.enums.TaskType;
import org.apache.dolphinscheduler.common.task.TaskTimeoutParameter;
import org.apache.dolphinscheduler.common.utils.CollectionUtils;
import org.apache.dolphinscheduler.common.utils.JSONUtils;
import org.apache.dolphinscheduler.common.utils.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Data
public class TaskNode {

    /**
     * task node id
     */
    private String id;

    /**
     * task node name
     */
    private String name;

    /**
     * task node description
     */
    private String desc;

    /**
     * task node type
     */
    private String type;

    /**
     * the run flag has two states, NORMAL or FORBIDDEN
     */
    private String runFlag;

    /**
     * the front field
     */
    private String loc;

    /**
     * maximum number of retries
     */
    private int maxRetryTimes;

    /**
     * Unit of retry interval: points
     */
    private int retryInterval;

    /**
     * params information
     */
    @JsonDeserialize(using = JSONUtils.JsonDataDeserializer.class)
    @JsonSerialize(using = JSONUtils.JsonDataSerializer.class)
    private String params;

    /**
     * inner dependency information
     */
    @JsonDeserialize(using = JSONUtils.JsonDataDeserializer.class)
    @JsonSerialize(using = JSONUtils.JsonDataSerializer.class)
    private String preTasks;

    /**
     * users store additional information
     */
    @JsonDeserialize(using = JSONUtils.JsonDataDeserializer.class)
    @JsonSerialize(using = JSONUtils.JsonDataSerializer.class)
    private String extras;

    /**
     * node dependency list
     */
    private List<String> depList;

    /**
     * outer dependency information
     */
    @JsonDeserialize(using = JSONUtils.JsonDataDeserializer.class)
    @JsonSerialize(using = JSONUtils.JsonDataSerializer.class)
    private String dependence;


    @JsonDeserialize(using = JSONUtils.JsonDataDeserializer.class)
    @JsonSerialize(using = JSONUtils.JsonDataSerializer.class)
    private String conditionResult;

    /**
     * task instance priority
     */
    private Priority taskInstancePriority;

    /**
     * worker group
     */
    private String workerGroup;

    /**
     * worker group id
     */
    private Integer workerGroupId;


    /**
     * task time out
     */
    @JsonDeserialize(using = JSONUtils.JsonDataDeserializer.class)
    @JsonSerialize(using = JSONUtils.JsonDataSerializer.class)
    private String timeout;

    public void setPreTasks(String preTasks) throws IOException {
        this.preTasks = preTasks;
        this.depList = JSONUtils.toList(preTasks, String.class);
    }

    public void setDepList(List<String> depList) throws JsonProcessingException {
        this.depList = depList;
        this.preTasks = JSONUtils.toJson(depList);
    }

    public Boolean isForbidden() {
        return (StringUtils.isNotEmpty(this.runFlag) &&
                this.runFlag.equals(Constants.FLOWNODE_RUN_FLAG_FORBIDDEN));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskNode taskNode = (TaskNode) o;
        return Objects.equals(name, taskNode.name) &&
                Objects.equals(desc, taskNode.desc) &&
                Objects.equals(type, taskNode.type) &&
                Objects.equals(params, taskNode.params) &&
                Objects.equals(preTasks, taskNode.preTasks) &&
                Objects.equals(extras, taskNode.extras) &&
                Objects.equals(runFlag, taskNode.runFlag) &&
                Objects.equals(dependence, taskNode.dependence) &&
                Objects.equals(workerGroup, taskNode.workerGroup) &&
                Objects.equals(conditionResult, taskNode.conditionResult) &&

                CollectionUtils.equalLists(depList, taskNode.depList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, desc, type, params, preTasks, extras, depList, runFlag);
    }

    /**
     * get task time out parameter
     *
     * @return task time out parameter
     */
    public TaskTimeoutParameter getTaskTimeoutParameter() {
        if (StringUtils.isNotEmpty(this.getTimeout())) {
            String formatStr = String.format("%s,%s", TaskTimeoutStrategy.WARN.name(), TaskTimeoutStrategy.FAILED.name());
            String taskTimeout = this.getTimeout().replace(formatStr, TaskTimeoutStrategy.WARNFAILED.name());
            return JSON.parseObject(taskTimeout, TaskTimeoutParameter.class);
        }
        return new TaskTimeoutParameter(false);
    }

    public boolean isConditionsTask() {
        return TaskType.CONDITIONS.toString().equalsIgnoreCase(this.getType());
    }
}
