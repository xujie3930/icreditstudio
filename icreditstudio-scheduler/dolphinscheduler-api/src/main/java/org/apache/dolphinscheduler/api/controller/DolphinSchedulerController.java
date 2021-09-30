package org.apache.dolphinscheduler.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dolphinscheduler.api.exceptions.ApiException;
import org.apache.dolphinscheduler.api.service.DolphinSchedulerService;
import org.apache.dolphinscheduler.api.utils.Result;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.common.enums.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.text.ParseException;
import java.util.Map;

import static org.apache.dolphinscheduler.api.enums.Status.*;

/**
 * @author Peng
 */
@RestController
@RequestMapping("/dolphinScheduler")
public class DolphinSchedulerController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(DolphinSchedulerController.class);
    @Autowired
    private DolphinSchedulerService dolphinSchedulerService;

    //TODO 默认一个项目icredit枚举
//    /**
//     * 创建项目
//     *
//     * @param loginUser
//     * @param projectName
//     * @param description
//     * @return
//     */
    /*@PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiException(CREATE_PROJECT_ERROR)
    @AccessLogAnnotation(ignoreRequestArgs = "loginUser")
    public Result createProject(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                @RequestParam("projectName") String projectName,
                                @RequestParam(value = "description", required = false) String description) {

        Map<String, Object> result = dolphinSchedulerService.createProject(loginUser, projectName, description);
        return returnDataList(result);
    }*/

    /**
     * create process definition
     *
     * @param userCode    login user
     * @param projectCode project name
     * @param name        process definition name
     * @param json        process definition json
     * @param description description
     * @param locations   locations for nodes
     * @param connects    connects for nodes
     * @return create result code
     */
    @ApiOperation(value = "save", notes = "CREATE_PROCESS_DEFINITION_NOTES")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "PROCESS_DEFINITION_NAME", required = true, type = "String"),
            @ApiImplicitParam(name = "processDefinitionJson", value = "PROCESS_DEFINITION_JSON", required = true, type = "String"),
            @ApiImplicitParam(name = "locations", value = "PROCESS_DEFINITION_LOCATIONS", required = true, type = "String"),
            @ApiImplicitParam(name = "connects", value = "PROCESS_DEFINITION_CONNECTS", required = true, type = "String"),
            @ApiImplicitParam(name = "description", value = "PROCESS_DEFINITION_DESC", required = false, type = "String"),
    })
    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiException(CREATE_PROCESS_DEFINITION)
    public Result createProcessDefinition(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) /*User loginUser*/String userCode,
                                          @ApiParam(name = "projectName", value = "PROJECT_NAME", required = true) @PathVariable Long projectCode,
                                          @RequestParam(value = "name", required = true) String name,
                                          @RequestParam(value = "processDefinitionJson", required = true) String json,
                                          @RequestParam(value = "locations", required = true) String locations,
                                          @RequestParam(value = "connects", required = true) String connects,
                                          @RequestParam(value = "description", required = false) String description) throws JsonProcessingException {

        logger.info("login user {}, create  process definition, project name: {}, process definition name: {}, " +
                        "process_definition_json: {}, desc: {} locations:{}, connects:{}",
                userCode, projectCode, name, json, description, locations, connects);
        Map<String, Object> result = dolphinSchedulerService.createProcessDefinition(userCode, projectCode, name, json,
                description, locations, connects);
        return returnDataList(result);
    }

    /**
     * release process definition
     *
     * @param userCode     login user
     * @param projectName  project name
     * @param processId    process definition id
     * @param releaseState release state
     * @return release result code
     */

    @ApiOperation(value = "releaseProcessDefinition", notes = "RELEASE_PROCESS_DEFINITION_NOTES")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "PROCESS_DEFINITION_NAME", required = true, type = "String"),
            @ApiImplicitParam(name = "processId", value = "PROCESS_DEFINITION_ID", required = true, dataType = "Int", example = "100"),
            @ApiImplicitParam(name = "releaseState", value = "PROCESS_DEFINITION_CONNECTS", required = true, dataType = "Int", example = "100"),
    })
    @PostMapping(value = "/release")
    @ResponseStatus(HttpStatus.OK)
    @ApiException(RELEASE_PROCESS_DEFINITION_ERROR)
    public Result releaseProcessDefinition(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) /*User loginUser*/String userCode,
                                           @ApiParam(name = "projectName", value = "PROJECT_NAME", required = true) @PathVariable String projectName,
                                           @RequestParam(value = "processId", required = true) int processId,
                                           @RequestParam(value = "releaseState", required = true) int releaseState) {

        logger.info("login user {}, release process definition, project name: {}, release state: {}",
                userCode, projectName, releaseState);
        Map<String, Object> result = dolphinSchedulerService.releaseProcessDefinition(userCode, projectName, processId, releaseState);
        return returnDataList(result);
    }

    /**
     * execute process instance
     *
     * @param userCode                login user
     * @param projectCode             project name
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
    public Result startProcessInstance(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) /*User loginUser*/String userCode,
                                       @ApiParam(name = "projectName", value = "PROJECT_NAME", required = true) @PathVariable Long projectCode,
                                       @RequestParam(value = "processDefinitionId") int processDefinitionId,
                                       @RequestParam(value = "scheduleTime", required = false) String scheduleTime,
                                       @RequestParam(value = "failureStrategy", required = true) FailureStrategy failureStrategy,
                                       @RequestParam(value = "startNodeList", required = false) String startNodeList,
                                       @RequestParam(value = "taskDependType", required = false) TaskDependType taskDependType,
                                       @RequestParam(value = "execType", required = false) CommandType execType,
                                       @RequestParam(value = "warningType", required = true) WarningType warningType,
                                       @RequestParam(value = "warningGroupId", required = false) int warningGroupId,
                                       @RequestParam(value = "receivers", required = false) String receivers,
                                       @RequestParam(value = "receiversCc", required = false) String receiversCc,
                                       @RequestParam(value = "runMode", required = false) RunMode runMode,
                                       @RequestParam(value = "processInstancePriority", required = false) Priority processInstancePriority,
                                       @RequestParam(value = "workerGroup", required = false, defaultValue = "default") String workerGroup,
                                       @RequestParam(value = "timeout", required = false) Integer timeout) throws ParseException {
        logger.info("login user {}, start process instance, project name: {}, process definition id: {}, schedule time: {}, "
                        + "failure policy: {}, node name: {}, node dep: {}, notify type: {}, "
                        + "notify group id: {},receivers:{},receiversCc:{}, run mode: {},process instance priority:{}, workerGroup: {}, timeout: {}",
                userCode, projectCode, processDefinitionId, scheduleTime,
                failureStrategy, startNodeList, taskDependType, warningType, workerGroup, receivers, receiversCc, runMode, processInstancePriority,
                workerGroup, timeout);

        if (timeout == null) {
            timeout = Constants.MAX_TASK_TIMEOUT;
        }

        Map<String, Object> result = dolphinSchedulerService.execProcessInstance(userCode, projectCode, processDefinitionId, scheduleTime, execType, failureStrategy,
                startNodeList, taskDependType, warningType,
                warningGroupId, receivers, receiversCc, runMode, processInstancePriority, workerGroup, timeout);
        return returnDataList(result);
    }

}
