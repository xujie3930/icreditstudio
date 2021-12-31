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
import org.apache.dolphinscheduler.common.enums.ExecutionStatus;
import org.apache.dolphinscheduler.common.enums.Flag;
import org.apache.dolphinscheduler.common.enums.Priority;
import org.apache.dolphinscheduler.common.enums.TaskType;
import org.apache.dolphinscheduler.common.model.TaskNode;
import org.apache.dolphinscheduler.common.utils.JSONUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * task instance
 */
@Data
@TableName("t_ds_task_instance")
public class TaskInstance implements Serializable {

    private Integer version;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * taskCode
     */
    private long taskCode;

    /**
     * task name
     */
    private String name;


    /**
     * task type
     */
    private String taskType;

    /**
     * process definition id
     */
    private String processDefinitionId;

    /**
     * process instance id
     */
    private String processInstanceId;

    /**
     * process instance name
     */
    @TableField(exist = false)
    private String processInstanceName;

    /**
     * task json
     */
    private String taskJson;

    /**
     * state
     */
    private ExecutionStatus state;

    /**
     * task submit time
     */
    private Date submitTime;

    /**
     * task start time
     */
    private Date startTime;

    /**
     * task end time
     */
    private Date endTime;

    /**
     * task host
     */
    private String host;

    /**
     * task shell execute path and the resource down from hdfs
     * default path: $base_run_dir/processInstanceId/taskInstanceId/retryTimes
     */
    private String executePath;

    /**
     * task log path
     * default path: $base_run_dir/processInstanceId/taskInstanceId/retryTimes
     */
    private String logPath;

    /**
     * retry times
     */
    private int retryTimes;

    /**
     * alert flag
     */
    private Flag alertFlag;

    /**
     * process instance
     */
    @TableField(exist = false)
    private ProcessInstance processInstance;

    /**
     * process definition
     */
    @TableField(exist = false)
    private ProcessDefinition processDefine;

    /**
     * process id
     */
    private int pid;

    /**
     * appLink
     */
    private String appLink;

    /**
     * flag
     */
    private Flag flag;

    /**
     * dependency
     */
    @TableField(exist = false)
    private String dependency;

    /**
     * duration
     */
    @TableField(exist = false)
    private String duration;

    /**
     * max retry times
     */
    private int maxRetryTimes;

    /**
     * task retry interval, unit: minute
     */
    private int retryInterval;

    /**
     * task intance priority
     */
    private Priority taskInstancePriority;

    /**
     * process intance priority
     */
    @TableField(exist = false)
    private Priority processInstancePriority;

    /**
     * dependent state
     */
    @TableField(exist = false)
    private String dependentResult;


    /**
     * workerGroup
     */
    private String workerGroup;

    private String workspaceId;

    /**
     * 扫描状态：0-未被扫描，1-已被扫描
     */
    private Integer scanState;

    /**
     * executor id
     */
    private String executorId;
    //每次同步总记录数
    private String totalRecords;
    //每次同步总字节数
    private String totalBytes;

    /**
     * executor name
     */
    @TableField(exist = false)
    private String executorName;


    @TableField(exist = false)
    private Map<String, String> resources;


    public void init(String host, Date startTime, String executePath) {
        this.host = host;
        this.startTime = startTime;
        this.executePath = executePath;
    }

    public Boolean isTaskSuccess() {
        return this.state == ExecutionStatus.SUCCESS;
    }


    public String getDependency() {

        if (this.dependency != null) {
            return this.dependency;
        }
        TaskNode taskNode = JSONUtils.parseObject(taskJson, TaskNode.class);

        return taskNode.getDependence();
    }

    public boolean isTaskComplete() {

        return this.getState().typeIsPause()
                || this.getState().typeIsSuccess()
                || this.getState().typeIsCancel()
                || (this.getState().typeIsFailure() && !taskCanRetry());
    }

    public boolean isSubProcess() {
        return TaskType.SUB_PROCESS.equals(TaskType.valueOf(this.taskType));
    }

    public boolean isDependTask() {
        return TaskType.DEPENDENT.equals(TaskType.valueOf(this.taskType));
    }

    public boolean isConditionsTask() {
        return TaskType.CONDITIONS.equals(TaskType.valueOf(this.taskType));
    }


    /**
     * determine if you can try again
     *
     * @return can try result
     */
    public boolean taskCanRetry() {
        if (this.isSubProcess()) {
            return false;
        }
        if (this.getState() == ExecutionStatus.NEED_FAULT_TOLERANCE) {
            return true;
        } else {
            return (this.getState().typeIsFailure()
                    && this.getRetryTimes() < this.getMaxRetryTimes());
        }
    }
}
