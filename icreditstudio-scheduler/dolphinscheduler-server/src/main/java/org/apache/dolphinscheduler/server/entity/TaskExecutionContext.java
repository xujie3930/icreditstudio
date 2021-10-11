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

package org.apache.dolphinscheduler.server.entity;

import lombok.Data;
import org.apache.dolphinscheduler.remote.command.Command;
import org.apache.dolphinscheduler.remote.command.TaskExecuteRequestCommand;
import org.apache.dolphinscheduler.remote.utils.FastJsonSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * master/worker task transport
 */
@Data
public class TaskExecutionContext implements Serializable {

    /**
     * task id
     */
    private String taskInstanceId;


    /**
     * taks name
     */
    private String taskName;

    /**
     * task start time
     */
    private Date startTime;

    /**
     * task type
     */
    private String taskType;

    /**
     * host
     */
    private String host;

    /**
     * task execute path
     */
    private String executePath;

    /**
     * log path
     */
    private String logPath;

    /**
     * task json
     */
    private String taskJson;

    /**
     * processId
     */
    private int processId;

    /**
     * appIds
     */
    private String appIds;

    /**
     * process instance id
     */
    private String processInstanceId;


    /**
     * process instance schedule time
     */
    private Date scheduleTime;

    /**
     * process instance global parameters
     */
    private String globalParams;


    /**
     * execute user id
     */
    private String executorId;


    /**
     * command type if complement
     */
    private int cmdTypeIfComplement;


    /**
     * tenant code
     */
    private String tenantCode;

    /**
     * task queue
     */
    private String queue;


    /**
     * process define id
     */
    private String processDefineId;

    /**
     * project id
     */
    private String projectId;

    /**
     * taskParams
     */
    private String taskParams;

    /**
     * envFile
     */
    private String envFile;

    /**
     * definedParams
     */
    private Map<String, String> definedParams;

    /**
     * task AppId
     */
    private String taskAppId;

    /**
     * task timeout strategy
     */
    private int taskTimeoutStrategy;

    /**
     * task timeout
     */
    private int taskTimeout;

    /**
     * worker group
     */
    private String workerGroup;

    /**
     * resources full name and tenant code
     */
    private Map<String, String> resources;

//    /**
//     * sql TaskExecutionContext
//     */
//    private SQLTaskExecutionContext sqlTaskExecutionContext;

    /**
     * datax TaskExecutionContext
     */
    private DataxTaskExecutionContext dataxTaskExecutionContext;

//    /**
//     * dependence TaskExecutionContext
//     */
//    private DependenceTaskExecutionContext dependenceTaskExecutionContext;

//    /**
//     * sqoop TaskExecutionContext
//     */
//    private SqoopTaskExecutionContext sqoopTaskExecutionContext;

//    /**
//     * procedure TaskExecutionContext
//     */
//    private ProcedureTaskExecutionContext procedureTaskExecutionContext;

    public Command toCommand() {
        TaskExecuteRequestCommand requestCommand = new TaskExecuteRequestCommand();
        requestCommand.setTaskExecutionContext(FastJsonSerializer.serializeToString(this));
        return requestCommand.convert2Command();
    }
}
