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
package org.apache.dolphinscheduler.service.process;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.common.enums.*;
import org.apache.dolphinscheduler.common.model.DateInterval;
import org.apache.dolphinscheduler.common.process.Property;
import org.apache.dolphinscheduler.common.utils.DateUtils;
import org.apache.dolphinscheduler.common.utils.JSONUtils;
import org.apache.dolphinscheduler.common.utils.ParameterUtils;
import org.apache.dolphinscheduler.common.utils.StringUtils;
import org.apache.dolphinscheduler.dao.entity.*;
import org.apache.dolphinscheduler.dao.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

import static org.apache.dolphinscheduler.common.Constants.*;

/**
 * process relative dao that some mappers in this.
 */
@Component
public class ProcessService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final int[] stateArray = new int[]{ExecutionStatus.SUBMITTED_SUCCESS.ordinal(),
            ExecutionStatus.RUNNING_EXECUTION.ordinal(),
            ExecutionStatus.READY_PAUSE.ordinal(),
            ExecutionStatus.READY_STOP.ordinal()};

    private String driverStr;
    private String mysqlUrl;
    private String pwd;
    private String userName;
    private String sql = "update icredit_sync_task set exec_status = ?,last_scheduling_time = ? where schedule_id = ?";

    {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("task.properties");
        try {
            Properties properties = new Properties();
            properties.load(in);
            this.driverStr = properties.getProperty("task.datasource.driver-class-name");
            this.mysqlUrl = properties.getProperty("task.datasource.url");
            this.pwd = properties.getProperty("task.datasource.password");
            this.userName = properties.getProperty("task.datasource.username");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Resource
    private ProcessDefinitionMapper processDefineMapper;

    @Resource
    private ProcessInstanceMapper processInstanceMapper;

    @Resource
    private ProcessInstanceMapMapper processInstanceMapMapper;

    @Resource
    private TaskInstanceMapper taskInstanceMapper;

    @Resource
    private CommandMapper commandMapper;

    @Resource
    private ScheduleMapper scheduleMapper;

    @Resource
    private ErrorCommandMapper errorCommandMapper;

    /**
     * handle Command (construct ProcessInstance from Command) , wrapped in transaction
     *
     * @param logger         logger
     * @param host           host
     * @param validThreadNum validThreadNum
     * @param command        found command
     * @return process instance
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessInstance handleCommand(Logger logger, String host, int validThreadNum, Command command) {
        ProcessInstance processInstance = constructProcessInstance(command, host);
        //cannot construct process instance, return null;
        if (processInstance == null) {
            logger.error("scan command, command parameter is error: {}", command);
            moveToErrorCommand(command, "process instance is null");
            return null;
        }
        if (!checkThreadNum(command, validThreadNum)) {
            logger.info("there is not enough thread for this command: {}", command);
            return setWaitingThreadProcess(command, processInstance);
        }
        processInstance.setCommandType(command.getCommandType());
        processInstance.addHistoryCmd(command.getCommandType());
        saveProcessInstance(processInstance);
        delCommandByid(command.getId());
        return processInstance;
    }

    /**
     * save error command, and delete original command
     *
     * @param command command
     * @param message message
     */
    @Transactional(rollbackFor = Exception.class)
    public void moveToErrorCommand(Command command, String message) {
        ErrorCommand errorCommand = new ErrorCommand(command, message);
        this.errorCommandMapper.insert(errorCommand);
        delCommandByid(command.getId());
    }

    /**
     * set process waiting thread
     *
     * @param command         command
     * @param processInstance processInstance
     * @return process instance
     */
    private ProcessInstance setWaitingThreadProcess(Command command, ProcessInstance processInstance) {
        processInstance.setState(ExecutionStatus.WAITTING_THREAD);
        if (command.getCommandType() != CommandType.RECOVER_WAITTING_THREAD) {
            processInstance.addHistoryCmd(command.getCommandType());
        }
        saveProcessInstance(processInstance);
        createRecoveryWaitingThreadCommand(command, processInstance);
        return null;
    }

    /**
     * check thread num
     *
     * @param command        command
     * @param validThreadNum validThreadNum
     * @return if thread is enough
     */
    private boolean checkThreadNum(Command command, int validThreadNum) {
        int commandThreadCount = this.workProcessThreadNumCount(command.getProcessDefinitionId());
        return validThreadNum >= commandThreadCount;
    }

    /**
     * insert one command
     *
     * @param command command
     * @return create result
     */
    public int createCommand(Command command) {
        int result = 0;
        if (command != null) {
            result = commandMapper.insert(command);
        }
        return result;
    }

    /**
     * find one command from queue list
     *
     * @return command
     */
    public Command findOneCommand() {
        return commandMapper.getOneToRun();
    }

    /**
     * check the input command exists in queue list
     *
     * @param command command
     * @return create command result
     */
    public Boolean verifyIsNeedCreateCommand(Command command) {
        Boolean isNeedCreate = true;
        Map<CommandType, Integer> cmdTypeMap = new HashMap<CommandType, Integer>();
        cmdTypeMap.put(CommandType.REPEAT_RUNNING, 1);
        cmdTypeMap.put(CommandType.RECOVER_SUSPENDED_PROCESS, 1);
        cmdTypeMap.put(CommandType.START_FAILURE_TASK_PROCESS, 1);
        CommandType commandType = command.getCommandType();

        if (cmdTypeMap.containsKey(commandType)) {
            JSONObject cmdParamObj = (JSONObject) JSON.parse(command.getCommandParam());
            JSONObject tempObj;
            int processInstanceId = cmdParamObj.getInteger(CMDPARAM_RECOVER_PROCESS_ID_STRING);

            List<Command> commands = commandMapper.selectList(null);
            // for all commands
            for (Command tmpCommand : commands) {
                if (cmdTypeMap.containsKey(tmpCommand.getCommandType())) {
                    tempObj = (JSONObject) JSON.parse(tmpCommand.getCommandParam());
                    if (tempObj != null && processInstanceId == tempObj.getInteger(CMDPARAM_RECOVER_PROCESS_ID_STRING)) {
                        isNeedCreate = false;
                        break;
                    }
                }
            }
        }
        return isNeedCreate;
    }

    /**
     * find process instance detail by id
     *
     * @param processId processId
     * @return process instance
     */
    public ProcessInstance findProcessInstanceDetailById(String processId) {
        return processInstanceMapper.queryDetailById(processId);
    }

    /**
     * find process instance by id
     *
     * @param processId processId
     * @return process instance
     */
    public ProcessInstance findProcessInstanceById(String processId) {
        return processInstanceMapper.selectById(processId);
    }

    /**
     * find process define by id.
     *
     * @param processDefinitionId processDefinitionId
     * @return process definition
     */
    public ProcessDefinition findProcessDefineById(String processDefinitionId) {
        return processDefineMapper.selectById(processDefinitionId);
    }


    /**
     * calculate sub process number in the process define.
     *
     * @param processDefinitionId processDefinitionId
     * @return process thread num count
     */
    private Integer workProcessThreadNumCount(String processDefinitionId) {
        List<String> ids = new ArrayList<>();
        return ids.size() + 1;
    }

    /**
     * create recovery waiting thread command when thread pool is not enough for the process instance.
     * sub work process instance need not to create recovery command.
     * create recovery waiting thread  command and delete origin command at the same time.
     * if the recovery command is exists, only update the field update_time
     *
     * @param originCommand   originCommand
     * @param processInstance processInstance
     */
    public void createRecoveryWaitingThreadCommand(Command originCommand, ProcessInstance processInstance) {

        // sub process doesnot need to create wait command
        if (processInstance.getIsSubProcess() == Flag.YES) {
            if (originCommand != null) {
                commandMapper.deleteById(originCommand.getId());
            }
            return;
        }
        Map<String, String> cmdParam = new HashMap<>();
        cmdParam.put(Constants.CMDPARAM_RECOVERY_WAITTING_THREAD, String.valueOf(processInstance.getId()));
        // process instance quit by "waiting thread" state
        if (originCommand == null) {
            Command command = new Command(
                    CommandType.RECOVER_WAITTING_THREAD,
                    processInstance.getTaskDependType(),
                    processInstance.getFailureStrategy(),
                    processInstance.getExecutorId(),
                    processInstance.getProcessDefinitionId(),
                    JSONUtils.toJson(cmdParam),
                    processInstance.getWarningType(),
                    processInstance.getWarningGroupId(),
                    processInstance.getScheduleTime(),
                    processInstance.getWorkerGroup(),
                    processInstance.getProcessInstancePriority()
            );
            saveCommand(command);
            return;
        }

        // update the command time if current command if recover from waiting
        if (originCommand.getCommandType() == CommandType.RECOVER_WAITTING_THREAD) {
            originCommand.setUpdateTime(new Date());
            saveCommand(originCommand);
        } else {
            // delete old command and create new waiting thread command
            commandMapper.deleteById(originCommand.getId());
            originCommand.setId("0");
            originCommand.setCommandType(CommandType.RECOVER_WAITTING_THREAD);
            originCommand.setUpdateTime(new Date());
            originCommand.setCommandParam(JSONUtils.toJson(cmdParam));
            originCommand.setProcessInstancePriority(processInstance.getProcessInstancePriority());
            saveCommand(originCommand);
        }
    }

    /**
     * get schedule time from command
     *
     * @param command  command
     * @param cmdParam cmdParam map
     * @return date
     */
    private Date getScheduleTime(Command command, Map<String, String> cmdParam) {
        Date scheduleTime = command.getScheduleTime();
        if (scheduleTime == null) {
            if (cmdParam != null && cmdParam.containsKey(CMDPARAM_COMPLEMENT_DATA_START_DATE)) {
                scheduleTime = DateUtils.stringToDate(cmdParam.get(CMDPARAM_COMPLEMENT_DATA_START_DATE));
            }
        }
        return scheduleTime;
    }

    /**
     * generate a new work process instance from command.
     *
     * @param processDefinition processDefinition
     * @param command           command
     * @param cmdParam          cmdParam map
     * @return process instance
     */
    private ProcessInstance generateNewProcessInstance(ProcessDefinition processDefinition,
                                                       Command command,
                                                       Map<String, String> cmdParam) {
        ProcessInstance processInstance = new ProcessInstance(processDefinition);
        processInstance.setState(ExecutionStatus.RUNNING_EXECUTION);
        processInstance.setRecovery(Flag.NO);
        processInstance.setStartTime(new Date());
        processInstance.setRunTimes(1);
        processInstance.setMaxTryTimes(0);
        processInstance.setProcessDefinitionId(command.getProcessDefinitionId());
        processInstance.setCommandParam(command.getCommandParam());
        processInstance.setCommandType(command.getCommandType());
        processInstance.setIsSubProcess(Flag.NO);
        processInstance.setTaskDependType(command.getTaskDependType());
        processInstance.setFailureStrategy(command.getFailureStrategy());
        processInstance.setExecutorId(command.getExecutorId());
        WarningType warningType = command.getWarningType() == null ? WarningType.NONE : command.getWarningType();
        processInstance.setWarningType(warningType);
        String warningGroupId = command.getWarningGroupId() == null ? "0" : command.getWarningGroupId();
        processInstance.setWarningGroupId(warningGroupId);

        // schedule time
        Date scheduleTime = getScheduleTime(command, cmdParam);
        if (scheduleTime != null) {
            processInstance.setScheduleTime(scheduleTime);
        }
        processInstance.setCommandStartTime(command.getStartTime());
        processInstance.setLocations(processDefinition.getLocations());
        processInstance.setConnects(processDefinition.getConnects());
        // curing global params
        processInstance.setGlobalParams(ParameterUtils.curingGlobalParams(
                processDefinition.getGlobalParamMap(),
                processDefinition.getGlobalParamList(),
                getCommandTypeIfComplement(processInstance, command),
                processInstance.getScheduleTime()));

        //copy process define json to process instance
        processInstance.setProcessInstanceJson(processDefinition.getProcessDefinitionJson());
        // set process instance priority
        processInstance.setProcessInstancePriority(command.getProcessInstancePriority());
        String workerGroup = StringUtils.isBlank(command.getWorkerGroup()) ? Constants.DEFAULT_WORKER_GROUP : command.getWorkerGroup();
        processInstance.setWorkerGroup(workerGroup);
        processInstance.setTimeout(processDefinition.getTimeout());
        processInstance.setTenantCode(processDefinition.getTenantCode());
        return processInstance;
    }

    /**
     * check command parameters is valid
     *
     * @param command  command
     * @param cmdParam cmdParam map
     * @return whether command param is valid
     */
    private Boolean checkCmdParam(Command command, Map<String, String> cmdParam) {
        if (command.getTaskDependType() == TaskDependType.TASK_ONLY || command.getTaskDependType() == TaskDependType.TASK_PRE) {
            if (cmdParam == null
                    || !cmdParam.containsKey(Constants.CMDPARAM_START_NODE_NAMES)
                    || cmdParam.get(Constants.CMDPARAM_START_NODE_NAMES).isEmpty()) {
                logger.error("command node depend type is {}, but start nodes is null ", command.getTaskDependType());
                return false;
            }
        }
        return true;
    }

    /**
     * construct process instance according to one command.
     *
     * @param command command
     * @param host    host
     * @return process instance
     */
    private ProcessInstance constructProcessInstance(Command command, String host) {

        ProcessInstance processInstance = null;
        CommandType commandType = command.getCommandType();
        Map<String, String> cmdParam = JSONUtils.toMap(command.getCommandParam());

        ProcessDefinition processDefinition = null;
        if (StringUtils.isNotBlank(command.getProcessDefinitionId())) {
            processDefinition = processDefineMapper.selectById(command.getProcessDefinitionId());
            if (processDefinition == null) {
                logger.error("cannot find the work process define! define id : {}", command.getProcessDefinitionId());
                return null;
            }
        }

        if (cmdParam != null) {
            String processInstanceId = null;
            // recover from failure or pause tasks
            if (cmdParam.containsKey(Constants.CMDPARAM_RECOVER_PROCESS_ID_STRING)) {
                processInstanceId = cmdParam.get(Constants.CMDPARAM_RECOVER_PROCESS_ID_STRING);
                if (StringUtils.isBlank(processInstanceId)) {
                    logger.error("command parameter is error, [ ProcessInstanceId ] is null");
                    return null;
                }
            } else if (cmdParam.containsKey(Constants.CMDPARAM_SUB_PROCESS)) {
                // sub process map
                processInstanceId = cmdParam.get(Constants.CMDPARAM_SUB_PROCESS);
            } else if (cmdParam.containsKey(Constants.CMDPARAM_RECOVERY_WAITTING_THREAD)) {
                // waiting thread command
                processInstanceId = cmdParam.get(Constants.CMDPARAM_RECOVERY_WAITTING_THREAD);
            }
            if (StringUtils.isBlank(processInstanceId)) {
                processInstance = generateNewProcessInstance(processDefinition, command, cmdParam);
            } else {
                processInstance = this.findProcessInstanceDetailById(processInstanceId);
                // Recalculate global parameters after rerun.
                processInstance.setGlobalParams(ParameterUtils.curingGlobalParams(
                        processDefinition.getGlobalParamMap(),
                        processDefinition.getGlobalParamList(),
                        getCommandTypeIfComplement(processInstance, command),
                        processInstance.getScheduleTime()));
            }
            processDefinition = processDefineMapper.selectById(processInstance.getProcessDefinitionId());
            processInstance.setProcessDefinition(processDefinition);

            //reset command parameter
            if (processInstance.getCommandParam() != null) {
                Map<String, String> processCmdParam = JSONUtils.toMap(processInstance.getCommandParam());
                for (Map.Entry<String, String> entry : processCmdParam.entrySet()) {
                    if (!cmdParam.containsKey(entry.getKey())) {
                        cmdParam.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            // reset command parameter if sub process
            if (cmdParam.containsKey(Constants.CMDPARAM_SUB_PROCESS)) {
                processInstance.setCommandParam(command.getCommandParam());
            }
        } else {
            // generate one new process instance
            processInstance = generateNewProcessInstance(processDefinition, command, cmdParam);
        }
        if (!checkCmdParam(command, cmdParam)) {
            logger.error("command parameter check failed!");
            return null;
        }

        if (command.getScheduleTime() != null) {
            processInstance.setScheduleTime(command.getScheduleTime());
        }
        processInstance.setHost(host);

        ExecutionStatus runStatus = ExecutionStatus.RUNNING_EXECUTION;
        int runTime = processInstance.getRunTimes();
        switch (commandType) {
            case START_PROCESS:
                break;
            case START_FAILURE_TASK_PROCESS:
                // find failed tasks and init these tasks
                List<String> failedList = this.findTaskIdByInstanceState(processInstance.getId(), ExecutionStatus.FAILURE);
                List<String> toleranceList = this.findTaskIdByInstanceState(processInstance.getId(), ExecutionStatus.NEED_FAULT_TOLERANCE);
                List<String> killedList = this.findTaskIdByInstanceState(processInstance.getId(), ExecutionStatus.KILL);
                cmdParam.remove(Constants.CMDPARAM_RECOVERY_START_NODE_STRING);

                failedList.addAll(killedList);
                failedList.addAll(toleranceList);
                for (String taskId : failedList) {
                    initTaskInstance(this.findTaskInstanceById(taskId));
                }
                cmdParam.put(Constants.CMDPARAM_RECOVERY_START_NODE_STRING,
                        String.join(Constants.COMMA, failedList));
                processInstance.setCommandParam(JSONUtils.toJson(cmdParam));
                processInstance.setRunTimes(runTime + 1);
                break;
            case START_CURRENT_TASK_PROCESS:
                break;
            case RECOVER_WAITTING_THREAD:
                break;
            case RECOVER_SUSPENDED_PROCESS:
                // find pause tasks and init task's state
                cmdParam.remove(Constants.CMDPARAM_RECOVERY_START_NODE_STRING);
                List<String> suspendedNodeList = this.findTaskIdByInstanceState(processInstance.getId(), ExecutionStatus.PAUSE);
                List<String> stopNodeList = findTaskIdByInstanceState(processInstance.getId(), ExecutionStatus.KILL);
                suspendedNodeList.addAll(stopNodeList);
                for (String taskId : suspendedNodeList) {
                    // initialize the pause state
                    initTaskInstance(this.findTaskInstanceById(taskId));
                }
                cmdParam.put(Constants.CMDPARAM_RECOVERY_START_NODE_STRING, String.join(",", suspendedNodeList));
                processInstance.setCommandParam(JSONUtils.toJson(cmdParam));
                processInstance.setRunTimes(runTime + 1);
                break;
            case RECOVER_TOLERANCE_FAULT_PROCESS:
                // recover tolerance fault process
                processInstance.setRecovery(Flag.YES);
                runStatus = processInstance.getState();
                break;
            case COMPLEMENT_DATA:
                // delete all the valid tasks when complement data
                List<TaskInstance> taskInstanceList = this.findValidTaskListByProcessId(processInstance.getId());
                for (TaskInstance taskInstance : taskInstanceList) {
                    taskInstance.setFlag(Flag.NO);
                    this.updateTaskInstance(taskInstance);
                }
                initComplementDataParam(processDefinition, processInstance, cmdParam);
                break;
            case REPEAT_RUNNING:
                // delete the recover task names from command parameter
                if (cmdParam.containsKey(Constants.CMDPARAM_RECOVERY_START_NODE_STRING)) {
                    cmdParam.remove(Constants.CMDPARAM_RECOVERY_START_NODE_STRING);
                    processInstance.setCommandParam(JSONUtils.toJson(cmdParam));
                }
                // delete all the valid tasks when repeat running
                List<TaskInstance> validTaskList = findValidTaskListByProcessId(processInstance.getId());
                for (TaskInstance taskInstance : validTaskList) {
                    taskInstance.setFlag(Flag.NO);
                    updateTaskInstance(taskInstance);
                }
                processInstance.setStartTime(new Date());
                processInstance.setEndTime(null);
                processInstance.setRunTimes(runTime + 1);
                initComplementDataParam(processDefinition, processInstance, cmdParam);
                break;
            case SCHEDULER:
                break;
            default:
                break;
        }
        processInstance.setState(runStatus);
        return processInstance;
    }

    /**
     * return complement data if the process start with complement data
     *
     * @param processInstance processInstance
     * @param command         command
     * @return command type
     */
    private CommandType getCommandTypeIfComplement(ProcessInstance processInstance, Command command) {
        if (CommandType.COMPLEMENT_DATA == processInstance.getCmdTypeIfComplement()) {
            return CommandType.COMPLEMENT_DATA;
        } else {
            return command.getCommandType();
        }
    }

    /**
     * initialize complement data parameters
     *
     * @param processDefinition processDefinition
     * @param processInstance   processInstance
     * @param cmdParam          cmdParam
     */
    private void initComplementDataParam(ProcessDefinition processDefinition,
                                         ProcessInstance processInstance,
                                         Map<String, String> cmdParam) {
        if (!processInstance.isComplementData()) {
            return;
        }

        Date startComplementTime = DateUtils.parse(cmdParam.get(CMDPARAM_COMPLEMENT_DATA_START_DATE),
                YYYY_MM_DD_HH_MM_SS);
        if (Flag.NO == processInstance.getIsSubProcess()) {
            processInstance.setScheduleTime(startComplementTime);
        }
        processInstance.setGlobalParams(ParameterUtils.curingGlobalParams(
                processDefinition.getGlobalParamMap(),
                processDefinition.getGlobalParamList(),
                CommandType.COMPLEMENT_DATA, processInstance.getScheduleTime()));

    }

    /**
     * join parent global params into sub process.
     * only the keys doesn't in sub process global would be joined.
     *
     * @param parentGlobalParams parentGlobalParams
     * @param subGlobalParams    subGlobalParams
     * @return global params join
     */
    private String joinGlobalParams(String parentGlobalParams, String subGlobalParams) {
        List<Property> parentPropertyList = JSONUtils.toList(parentGlobalParams, Property.class);
        List<Property> subPropertyList = JSONUtils.toList(subGlobalParams, Property.class);
        Map<String, String> subMap = subPropertyList.stream().collect(Collectors.toMap(Property::getProp, Property::getValue));

        for (Property parent : parentPropertyList) {
            if (!subMap.containsKey(parent.getProp())) {
                subPropertyList.add(parent);
            }
        }
        return JSONUtils.toJson(subPropertyList);
    }

    /**
     * initialize task instance
     *
     * @param taskInstance taskInstance
     */
    private void initTaskInstance(TaskInstance taskInstance) {

        if (!taskInstance.isSubProcess()) {
            if (taskInstance.getState().typeIsCancel() || taskInstance.getState().typeIsFailure()) {
                taskInstance.setFlag(Flag.NO);
                updateTaskInstance(taskInstance);
                return;
            }
        }
        taskInstance.setState(ExecutionStatus.SUBMITTED_SUCCESS);
        updateTaskInstance(taskInstance);
    }

    /**
     * submit task to db
     * submit sub process to command
     *
     * @param taskInstance taskInstance
     * @return task instance
     */
    @Transactional(rollbackFor = Exception.class)
    public TaskInstance submitTask(TaskInstance taskInstance) {
        ProcessInstance processInstance = this.findProcessInstanceDetailById(taskInstance.getProcessInstanceId());
        logger.info("start submit task : {}, instance id:{}, state: {}",
                taskInstance.getName(), taskInstance.getProcessInstanceId(), processInstance.getState());
        //submit to db
        TaskInstance task = submitTaskInstanceToDB(taskInstance, processInstance);
        if (task == null) {
            logger.error("end submit task to db error, task name:{}, process id:{} state: {} ",
                    taskInstance.getName(), taskInstance.getProcessInstance(), processInstance.getState());
            return task;
        }

        logger.info("end submit task to db successfully:{} state:{} complete, instance id:{} state: {}  ",
                taskInstance.getName(), task.getState(), processInstance.getId(), processInstance.getState());
        return task;
    }

    /**
     * submit task to mysql
     *
     * @param taskInstance    taskInstance
     * @param processInstance processInstance
     * @return task instance
     */
    public TaskInstance submitTaskInstanceToDB(TaskInstance taskInstance, ProcessInstance processInstance) {
        ExecutionStatus processInstanceState = processInstance.getState();

        if (taskInstance.getState().typeIsFailure()) {
            if (taskInstance.isSubProcess()) {
                taskInstance.setRetryTimes(taskInstance.getRetryTimes() + 1);
            } else {

                if (processInstanceState != ExecutionStatus.READY_STOP
                        && processInstanceState != ExecutionStatus.READY_PAUSE) {
                    // failure task set invalid
                    taskInstance.setFlag(Flag.NO);
                    updateTaskInstance(taskInstance);
                    // crate new task instance
                    if (taskInstance.getState() != ExecutionStatus.NEED_FAULT_TOLERANCE) {
                        taskInstance.setRetryTimes(taskInstance.getRetryTimes() + 1);
                    }
                    taskInstance.setEndTime(null);
                    taskInstance.setStartTime(null);
                    taskInstance.setFlag(Flag.YES);
                    taskInstance.setHost(null);
                    taskInstance.setId("0");
                }
            }
        }
        taskInstance.setExecutorId(processInstance.getExecutorId());
        taskInstance.setProcessInstancePriority(processInstance.getProcessInstancePriority());
        taskInstance.setState(getSubmitTaskState(taskInstance, processInstanceState));
        taskInstance.setSubmitTime(new Date());
        boolean saveResult = saveTaskInstance(taskInstance);
        if (!saveResult) {
            return null;
        }
        return taskInstance;
    }


    /**
     * ${processInstancePriority}_${processInstanceId}_${taskInstancePriority}_${taskInstanceId}_${task executed by ip1},${ip2}...
     * The tasks with the highest priority are selected by comparing the priorities of the above four levels from high to low.
     *
     * @param taskInstance taskInstance
     * @return task zk queue str
     */
    public String taskZkInfo(TaskInstance taskInstance) {

        String taskWorkerGroup = getTaskWorkerGroup(taskInstance);
        ProcessInstance processInstance = this.findProcessInstanceById(taskInstance.getProcessInstanceId());
        if (processInstance == null) {
            logger.error("process instance is null. please check the task info, task id: " + taskInstance.getId());
            return "";
        }

        StringBuilder sb = new StringBuilder(100);

        sb.append(processInstance.getProcessInstancePriority().ordinal()).append(Constants.UNDERLINE)
                .append(taskInstance.getProcessInstanceId()).append(Constants.UNDERLINE)
                .append(taskInstance.getTaskInstancePriority().ordinal()).append(Constants.UNDERLINE)
                .append(taskInstance.getId()).append(Constants.UNDERLINE)
                .append(taskInstance.getWorkerGroup());

        return sb.toString();
    }

    /**
     * get submit task instance state by the work process state
     * cannot modify the task state when running/kill/submit success, or this
     * task instance is already exists in task queue .
     * return pause if work process state is ready pause
     * return stop if work process state is ready stop
     * if all of above are not satisfied, return submit success
     *
     * @param taskInstance         taskInstance
     * @param processInstanceState processInstanceState
     * @return process instance state
     */
    public ExecutionStatus getSubmitTaskState(TaskInstance taskInstance, ExecutionStatus processInstanceState) {
        ExecutionStatus state = taskInstance.getState();
        if (
            // running or killed
            // the task already exists in task queue
            // return state
                state == ExecutionStatus.RUNNING_EXECUTION
                        || state == ExecutionStatus.KILL
                        || checkTaskExistsInTaskQueue(taskInstance)
        ) {
            return state;
        }
        //return pasue /stop if process instance state is ready pause / stop
        // or return submit success
        if (processInstanceState == ExecutionStatus.READY_PAUSE) {
            state = ExecutionStatus.PAUSE;
        } else if (processInstanceState == ExecutionStatus.READY_STOP
                || !checkProcessStrategy(taskInstance)) {
            state = ExecutionStatus.KILL;
        } else {
            state = ExecutionStatus.SUBMITTED_SUCCESS;
        }
        return state;
    }

    /**
     * check process instance strategy
     *
     * @param taskInstance taskInstance
     * @return check strategy result
     */
    private boolean checkProcessStrategy(TaskInstance taskInstance) {
        ProcessInstance processInstance = this.findProcessInstanceById(taskInstance.getProcessInstanceId());
        FailureStrategy failureStrategy = processInstance.getFailureStrategy();
        if (failureStrategy == FailureStrategy.CONTINUE) {
            return true;
        }
        List<TaskInstance> taskInstances = this.findValidTaskListByProcessId(taskInstance.getProcessInstanceId());

        for (TaskInstance task : taskInstances) {
            if (task.getState() == ExecutionStatus.FAILURE && task.getRetryTimes() >= task.getMaxRetryTimes()) {
                return false;
            }
        }
        return true;
    }

    /**
     * check the task instance existing in queue
     *
     * @param taskInstance taskInstance
     * @return whether taskinstance exists queue
     */
    public boolean checkTaskExistsInTaskQueue(TaskInstance taskInstance) {
        if (taskInstance.isSubProcess()) {
            return false;
        }

        String taskZkInfo = taskZkInfo(taskInstance);

        return false;
    }

    /**
     * create a new process instance
     *
     * @param processInstance processInstance
     */
    public void createProcessInstance(ProcessInstance processInstance) {

        if (processInstance != null) {
            processInstanceMapper.insert(processInstance);
        }
    }

    /**
     * insert or update work process instance to data base
     *
     * @param processInstance processInstance
     */
    public void saveProcessInstance(ProcessInstance processInstance) {

        if (processInstance == null) {
            logger.error("save error, process instance is null!");
            return;
        }
        if (StringUtils.isBlank(processInstance.getId())) {
            createProcessInstance(processInstance);
        } else {
            processInstanceMapper.updateById(processInstance);
        }
    }

    /**
     * insert or update command
     *
     * @param command command
     * @return save command result
     */
    public int saveCommand(Command command) {
        if (StringUtils.isEmpty(command.getId())) {
            return commandMapper.insert(command);
        } else {
            return commandMapper.updateById(command);
        }
    }

    /**
     * insert or update task instance
     *
     * @param taskInstance taskInstance
     * @return save task instance result
     */
    public boolean saveTaskInstance(TaskInstance taskInstance) {
        if (StringUtils.isEmpty(taskInstance.getId())) {
            return createTaskInstance(taskInstance);
        } else {
            return updateTaskInstance(taskInstance);
        }
    }

    /**
     * insert task instance
     *
     * @param taskInstance taskInstance
     * @return create task instance result
     */
    public boolean createTaskInstance(TaskInstance taskInstance) {
        int count = taskInstanceMapper.insert(taskInstance);
        return count > 0;
    }

    /**
     * update task instance
     *
     * @param taskInstance taskInstance
     * @return update task instance result
     */
    public boolean updateTaskInstance(TaskInstance taskInstance) {
        int count = taskInstanceMapper.updateById(taskInstance);
        return count > 0;
    }

    /**
     * delete a command by id
     *
     * @param id id
     */
    public void delCommandByid(String id) {
        commandMapper.deleteById(id);
    }

    /**
     * find task instance by id
     *
     * @param taskId task id
     * @return task intance
     */
    public TaskInstance findTaskInstanceById(String taskId) {
        return taskInstanceMapper.selectById(taskId);
    }


    /**
     * package task instance，associate processInstance and processDefine
     *
     * @param taskInstId taskInstId
     * @return task instance
     */
    public TaskInstance getTaskInstanceDetailByTaskId(String taskInstId) {
        // get task instance
        TaskInstance taskInstance = findTaskInstanceById(taskInstId);
        if (taskInstance == null) {
            return taskInstance;
        }
        // get process instance
        ProcessInstance processInstance = findProcessInstanceDetailById(taskInstance.getProcessInstanceId());
        // get process define
        ProcessDefinition processDefine = findProcessDefineById(taskInstance.getProcessDefinitionId());

        taskInstance.setProcessInstance(processInstance);
        taskInstance.setProcessDefine(processDefine);
        return taskInstance;
    }


    /**
     * get id list by task state
     *
     * @param instanceId instanceId
     * @param state      state
     * @return task instance states
     */
    public List<String> findTaskIdByInstanceState(String instanceId, ExecutionStatus state) {
        return taskInstanceMapper.queryTaskByProcessIdAndState(instanceId, state.ordinal());
    }

    /**
     * find valid task list by process definition id
     *
     * @param processInstanceId processInstanceId
     * @return task instance list
     */
    public List<TaskInstance> findValidTaskListByProcessId(String processInstanceId) {
        return taskInstanceMapper.findValidTaskListByProcessId(processInstanceId, Flag.YES);
    }

    /**
     * change task state
     *
     * @param state       state
     * @param startTime   startTime
     * @param host        host
     * @param executePath executePath
     * @param logPath     logPath
     * @param taskInstId  taskInstId
     */
    public void changeTaskState(ExecutionStatus state, Date startTime, String host,
                                String executePath,
                                String logPath,
                                String taskInstId) {
        TaskInstance taskInstance = taskInstanceMapper.selectById(taskInstId);
        taskInstance.setState(state);
        taskInstance.setStartTime(startTime);
        taskInstance.setHost(host);
        taskInstance.setExecutePath(executePath);
        taskInstance.setLogPath(logPath);
        saveTaskInstance(taskInstance);
    }

    /**
     * update process instance
     *
     * @param processInstance processInstance
     * @return update process instance result
     */
    public int updateProcessInstance(ProcessInstance processInstance) {
        return processInstanceMapper.updateById(processInstance);
    }

    /**
     * update the process instance
     *
     * @param processInstanceId processInstanceId
     * @param processJson       processJson
     * @param globalParams      globalParams
     * @param scheduleTime      scheduleTime
     * @param flag              flag
     * @param locations         locations
     * @param connects          connects
     * @return update process instance result
     */
    public int updateProcessInstance(String processInstanceId, String processJson,
                                     String globalParams, Date scheduleTime, Flag flag,
                                     String locations, String connects) {
        ProcessInstance processInstance = processInstanceMapper.queryDetailById(processInstanceId);
        if (processInstance != null) {
            processInstance.setProcessInstanceJson(processJson);
            processInstance.setGlobalParams(globalParams);
            processInstance.setScheduleTime(scheduleTime);
            processInstance.setLocations(locations);
            processInstance.setConnects(connects);
            return processInstanceMapper.updateById(processInstance);
        }
        return 0;
    }

    /**
     * change task state
     *
     * @param state      state
     * @param endTime    endTime
     * @param taskInstId taskInstId
     */
    public void changeTaskState(ExecutionStatus state,
                                Date endTime,
                                int processId,
                                String appIds,
                                String taskInstId) {
        TaskInstance taskInstance = taskInstanceMapper.selectById(taskInstId);
        taskInstance.setPid(processId);
        taskInstance.setAppLink(appIds);
        taskInstance.setState(state);
        taskInstance.setEndTime(endTime);
        saveTaskInstance(taskInstance);
    }

    /**
     * query schedule by id
     *
     * @param id id
     * @return schedule
     */
    public Schedule querySchedule(String id) {
        return scheduleMapper.selectById(id);
    }

    /**
     * query Schedule by processDefinitionId
     *
     * @param processDefinitionId processDefinitionId
     * @see Schedule
     */
    public List<Schedule> queryReleaseSchedulerListByProcessDefinitionId(String processDefinitionId) {
        return scheduleMapper.queryReleaseSchedulerListByProcessDefinitionId(processDefinitionId);
    }

    /**
     * query need failover process instance
     *
     * @param host host
     * @return process instance list
     */
    public List<ProcessInstance> queryNeedFailoverProcessInstances(String host) {

        return processInstanceMapper.queryByHostAndStatus(host, stateArray);
    }

    /**
     * process need failover process instance
     *
     * @param processInstance processInstance
     */
    @Transactional(rollbackFor = Exception.class)
    public void processNeedFailoverProcessInstances(ProcessInstance processInstance) {
        logger.info("set null host to process instance:{}", processInstance.getId());
        //1 update processInstance host is null
        processInstance.setHost(Constants.NULL);
        processInstanceMapper.updateById(processInstance);

        logger.info("create failover command for process instance:{}", processInstance.getId());

        //2 insert into recover command
        Command cmd = new Command();
        cmd.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        cmd.setCommandParam(String.format("{\"%s\":%s}", Constants.CMDPARAM_RECOVER_PROCESS_ID_STRING, processInstance.getId()));
        cmd.setExecutorId(processInstance.getExecutorId());
        cmd.setCommandType(CommandType.RECOVER_TOLERANCE_FAULT_PROCESS);
        createCommand(cmd);
    }

    /**
     * query all need failover task instances by host
     *
     * @param host host
     * @return task instance list
     */
    public List<TaskInstance> queryNeedFailoverTaskInstances(String host) {
        return taskInstanceMapper.queryByHostAndStatus(host,
                stateArray);
    }

    /**
     * find last scheduler process instance in the date interval
     *
     * @param definitionId definitionId
     * @param dateInterval dateInterval
     * @return process instance
     */
    public ProcessInstance findLastSchedulerProcessInterval(String definitionId, DateInterval dateInterval) {
        return processInstanceMapper.queryLastSchedulerProcess(definitionId,
                dateInterval.getStartTime(),
                dateInterval.getEndTime());
    }

    /**
     * find last manual process instance interval
     *
     * @param definitionId process definition id
     * @param dateInterval dateInterval
     * @return process instance
     */
    public ProcessInstance findLastManualProcessInterval(String definitionId, DateInterval dateInterval) {
        return processInstanceMapper.queryLastManualProcess(definitionId,
                dateInterval.getStartTime(),
                dateInterval.getEndTime());
    }

    /**
     * find last running process instance
     *
     * @param definitionId process definition id
     * @param startTime    start time
     * @param endTime      end time
     * @return process instance
     */
    public ProcessInstance findLastRunningProcess(String definitionId, Date startTime, Date endTime) {
        return processInstanceMapper.queryLastRunningProcess(definitionId,
                startTime,
                endTime,
                stateArray);
    }

    /**
     * get task worker group
     *
     * @param taskInstance taskInstance
     * @return workerGroupId
     */
    public String getTaskWorkerGroup(TaskInstance taskInstance) {
        String workerGroup = taskInstance.getWorkerGroup();

        if (StringUtils.isNotBlank(workerGroup)) {
            return workerGroup;
        }
        String processInstanceId = taskInstance.getProcessInstanceId();
        ProcessInstance processInstance = findProcessInstanceById(processInstanceId);

        if (processInstance != null) {
            return processInstance.getWorkerGroup();
        }
        logger.info("task : {} will use default worker group", taskInstance.getId());
        return Constants.DEFAULT_WORKER_GROUP;
    }

    public void updateTaskByScheduleId(String processDefinitionId, int state, Date nowDate) {
        try {
            Class.forName(this.driverStr);
            Connection con = DriverManager.getConnection(this.mysqlUrl, this.userName, this.pwd);

            PreparedStatement pstmt = con.prepareStatement(this.sql) ;
            pstmt.setInt(1, 7 == state ? 0 : 1);
            pstmt.setTimestamp(2, new Timestamp(nowDate.getTime()));
            pstmt.setString(3, processDefinitionId);
            pstmt.execute();
            if(null != con){
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
