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

package org.apache.dolphinscheduler.api.controller;

import io.swagger.annotations.*;
import org.apache.dolphinscheduler.api.aspect.AccessLogAnnotation;
import org.apache.dolphinscheduler.api.enums.ExecuteType;
import org.apache.dolphinscheduler.api.exceptions.ApiException;
import org.apache.dolphinscheduler.api.service.ExecutorService;
import org.apache.dolphinscheduler.api.utils.Result;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.common.enums.*;
import org.apache.dolphinscheduler.dao.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.text.ParseException;
import java.util.Map;

import static org.apache.dolphinscheduler.api.enums.Status.EXECUTE_PROCESS_INSTANCE_ERROR;
import static org.apache.dolphinscheduler.api.enums.Status.START_PROCESS_INSTANCE_ERROR;

/**
 * executor controller
 */
@Api(tags = "EXECUTOR_TAG")
@RestController
@RequestMapping("projects/{projectName}/executors")
public class ExecutorController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorController.class);

    @Autowired
    private ExecutorService execService;

    /**
     * execute process instance
     *
     * @param loginUser               login user
     * @param projectName             project name
     * @param processDefinitionId     process definition id
     * @param scheduleTime            schedule time
     * @param failureStrategy         failure strategy
     * @param startNodeList           start nodes list
     * @param taskDependType          task depend type
     * @param execType                execute type
     * @param warningType             warning type
     * @param warningGroupId          warning group id
     * @param receivers               receivers
     * @param receiversCc             receivers cc
     * @param runMode                 run mode
     * @param processInstancePriority process instance priority
     * @param workerGroup             worker group
     * @param timeout                 timeout
     * @return start process result code
     */
    @ApiOperation(value = "startProcessInstance", notes = "RUN_PROCESS_INSTANCE_NOTES")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processDefinitionId", value = "PROCESS_DEFINITION_ID", required = true, dataType = "Int", example = "100"),
            @ApiImplicitParam(name = "scheduleTime", value = "SCHEDULE_TIME", required = true, dataType = "String"),
            @ApiImplicitParam(name = "failureStrategy", value = "FAILURE_STRATEGY", required = true, dataType = "FailureStrategy"),
            @ApiImplicitParam(name = "startNodeList", value = "START_NODE_LIST", dataType = "String"),
            @ApiImplicitParam(name = "taskDependType", value = "TASK_DEPEND_TYPE", dataType = "TaskDependType"),
            @ApiImplicitParam(name = "execType", value = "COMMAND_TYPE", dataType = "CommandType"),
            @ApiImplicitParam(name = "warningType", value = "WARNING_TYPE", required = true, dataType = "WarningType"),
            @ApiImplicitParam(name = "warningGroupId", value = "WARNING_GROUP_ID", required = true, dataType = "Int", example = "100"),
            @ApiImplicitParam(name = "receivers", value = "RECEIVERS", dataType = "String"),
            @ApiImplicitParam(name = "receiversCc", value = "RECEIVERS_CC", dataType = "String"),
            @ApiImplicitParam(name = "runMode", value = "RUN_MODE", dataType = "RunMode"),
            @ApiImplicitParam(name = "processInstancePriority", value = "PROCESS_INSTANCE_PRIORITY", required = true, dataType = "Priority"),
            @ApiImplicitParam(name = "workerGroup", value = "WORKER_GROUP", dataType = "String", example = "default"),
            @ApiImplicitParam(name = "timeout", value = "TIMEOUT", dataType = "Int", example = "100"),
    })
    @PostMapping(value = "start-process-instance")
    @ResponseStatus(HttpStatus.OK)
    @ApiException(START_PROCESS_INSTANCE_ERROR)
    public Result startProcessInstance(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                       @ApiParam(name = "projectName", value = "PROJECT_NAME", required = true) @PathVariable String projectName,
                                       @RequestParam(value = "processDefinitionId") String processDefinitionId,
                                       @RequestParam(value = "scheduleTime", required = false) String scheduleTime,
                                       @RequestParam(value = "failureStrategy", required = true) FailureStrategy failureStrategy,
                                       @RequestParam(value = "startNodeList", required = false) String startNodeList,
                                       @RequestParam(value = "taskDependType", required = false) TaskDependType taskDependType,
                                       @RequestParam(value = "execType", required = false) CommandType execType,
                                       @RequestParam(value = "warningType", required = true) WarningType warningType,
                                       @RequestParam(value = "warningGroupId", required = false) String warningGroupId,
                                       @RequestParam(value = "receivers", required = false) String receivers,
                                       @RequestParam(value = "receiversCc", required = false) String receiversCc,
                                       @RequestParam(value = "runMode", required = false) RunMode runMode,
                                       @RequestParam(value = "processInstancePriority", required = false) Priority processInstancePriority,
                                       @RequestParam(value = "workerGroup", required = false, defaultValue = "default") String workerGroup,
                                       @RequestParam(value = "timeout", required = false) Integer timeout) throws ParseException {
        logger.info("login user {}, start process instance, project name: {}, process definition id: {}, schedule time: {}, "
                        + "failure policy: {}, node name: {}, node dep: {}, notify type: {}, "
                        + "notify group id: {},receivers:{},receiversCc:{}, run mode: {},process instance priority:{}, workerGroup: {}, timeout: {}",
                loginUser.getTenantCode(), projectName, processDefinitionId, scheduleTime,
                failureStrategy, startNodeList, taskDependType, warningType, workerGroup, receivers, receiversCc, runMode, processInstancePriority,
                workerGroup, timeout);

        if (timeout == null) {
            timeout = Constants.MAX_TASK_TIMEOUT;
        }

        Map<String, Object> result = execService.execProcessInstance(loginUser, projectName, processDefinitionId, scheduleTime, execType, failureStrategy,
                startNodeList, taskDependType, warningType,
                warningGroupId, receivers, receiversCc, runMode, processInstancePriority, workerGroup, timeout);
        return returnDataList(result);
    }

    /**
     * do action to process instance：pause, stop, repeat, recover from pause, recover from stop
     *
     * @param loginUser         login user
     * @param projectName       project name
     * @param processInstanceId process instance id
     * @param executeType       execute type
     * @return execute result code
     */
    @ApiOperation(value = "execute", notes = "EXECUTE_ACTION_TO_PROCESS_INSTANCE_NOTES")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInstanceId", value = "PROCESS_INSTANCE_ID", required = true, dataType = "Int", example = "100"),
            @ApiImplicitParam(name = "executeType", value = "EXECUTE_TYPE", required = true, dataType = "ExecuteType")
    })
    @PostMapping(value = "/execute")
    @ResponseStatus(HttpStatus.OK)
    @ApiException(EXECUTE_PROCESS_INSTANCE_ERROR)
    @AccessLogAnnotation(ignoreRequestArgs = "loginUser")
    //TODO:工作流执行,这里插入Command
    public Result execute(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                          @ApiParam(name = "projectName", value = "PROJECT_NAME", required = true) @PathVariable String projectName,
                          @RequestParam("processInstanceId") String processInstanceId,
                          @RequestParam("executeType") ExecuteType executeType
    ) {
        Map<String, Object> result = execService.execute(loginUser, projectName, processInstanceId, executeType);
        return returnDataList(result);
    }

//    /**
//     * check process definition and all of the son process definitions is on line.
//     *
//     * @param loginUser login user
//     * @param processDefinitionId process definition id
//     * @return check result code
//     */
//    @ApiOperation(value = "startCheckProcessDefinition", notes = "START_CHECK_PROCESS_DEFINITION_NOTES")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "processDefinitionId", value = "PROCESS_DEFINITION_ID", required = true, dataType = "Int", example = "100")
//    })
//    @PostMapping(value = "/start-check")
//    @ResponseStatus(HttpStatus.OK)
//    @ApiException(CHECK_PROCESS_DEFINITION_ERROR)
//    @AccessLogAnnotation(ignoreRequestArgs = "loginUser")
//    public Result startCheckProcessDefinition(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
//                                              @RequestParam(value = "processDefinitionId") String processDefinitionId) {
//        Map<String, Object> result = execService.startCheckByProcessDefinedId(processDefinitionId);
//        return returnDataList(result);
//    }
}
