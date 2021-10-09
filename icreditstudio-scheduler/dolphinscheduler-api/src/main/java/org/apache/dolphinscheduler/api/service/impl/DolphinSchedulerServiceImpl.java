//package org.apache.dolphinscheduler.api.service.impl;
//
//import org.apache.dolphinscheduler.api.enums.Status;
//import org.apache.dolphinscheduler.api.service.DolphinSchedulerService;
//import org.apache.dolphinscheduler.api.service.MonitorService;
//import org.apache.dolphinscheduler.api.service.ProjectService;
//import org.apache.dolphinscheduler.api.service.SchedulerService;
//import org.apache.dolphinscheduler.api.utils.CheckUtils;
//import org.apache.dolphinscheduler.common.Constants;
//import org.apache.dolphinscheduler.common.enums.*;
//import org.apache.dolphinscheduler.common.graph.DAG;
//import org.apache.dolphinscheduler.common.model.Server;
//import org.apache.dolphinscheduler.common.model.TaskNode;
//import org.apache.dolphinscheduler.common.process.Property;
//import org.apache.dolphinscheduler.common.process.ResourceInfo;
//import org.apache.dolphinscheduler.common.task.AbstractParameters;
//import org.apache.dolphinscheduler.common.utils.*;
//import org.apache.dolphinscheduler.dao.entity.*;
//import org.apache.dolphinscheduler.dao.mapper.ProcessDefinitionMapper;
//import org.apache.dolphinscheduler.dao.mapper.ProjectMapper;
//import org.apache.dolphinscheduler.dao.mapper.ScheduleMapper;
//import org.apache.dolphinscheduler.service.process.ProcessService;
//import org.apache.dolphinscheduler.service.quartz.cron.CronUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.text.ParseException;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static org.apache.dolphinscheduler.common.Constants.*;
//
///**
// * @author Peng
// */
//@Service
//public class DolphinSchedulerServiceImpl extends BaseServiceImpl implements DolphinSchedulerService {
//
//    private static final Logger logger = LoggerFactory.getLogger(DolphinSchedulerServiceImpl.class);
//    private static final String PROCESSDEFINITIONID = "processDefinitionId";
//    @Resource
//    private ProjectService projectService;
//    @Resource
//    private ProjectMapper projectMapper;
//    @Resource
//    private ScheduleMapper scheduleMapper;
//    @Resource
//    private ProcessDefinitionMapper processDefinitionMapper;
//    @Autowired
//    private ProcessService processService;
//    @Autowired
//    private SchedulerService schedulerService;
//    @Autowired
//    private MonitorService monitorService;
//
//    //TODO 默认一个项目icredit枚举
///*    @Override
//    public Map<String, Object> createProject(*//*User loginUser*//*String userId, String name, String desc) {
//        Map<String, Object> result = new HashMap<>(5);
//        Map<String, Object> descCheck = checkDesc(desc);
//        if (descCheck.get(Constants.STATUS) != Status.SUCCESS) {
//            return descCheck;
//        }
//
//        Project project = projectMapper.queryByName(name);
//        if (project != null) {
//            putMsg(result, Status.PROJECT_ALREADY_EXISTS, name);
//            return result;
//        }
//        project = new Project();
//        Date now = new Date();
//
//        project.setName(name);
//        project.setDescription(desc);
//        project.setUserId(loginUser.getId());
//        project.setUserName(loginUser.getUserName());
//        project.setCreateTime(now);
//        project.setUpdateTime(now);
//
//        if (projectMapper.insert(project) > 0) {
//            result.put(Constants.DATA_LIST, project.getId());
//            putMsg(result, Status.SUCCESS);
//        } else {
//            putMsg(result, Status.CREATE_PROJECT_ERROR);
//        }
//        return result;
//    }*/
//
//    @Override
//    public Map<String, Object> createProcessDefinition(/*User loginUser*/String userCode,
//                                                                         Long projectCode,
//                                                                         String name,
//                                                                         String processDefinitionJson,
//                                                                         String desc,
//                                                                         String locations,
//                                                                         String connects) {
//        Map<String, Object> result = new HashMap<>(5);
//        //TODO 使用枚举创建项目信息
////        Project project = projectMapper.queryByName(projectName);
//        // check project auth
//        //TODO 校验用户权限是否有操作项目权限
//        /*Map<String, Object> checkResult = projectService.checkProjectAndAuth(loginUser, project, projectName);
//        Status resultStatus = (Status) checkResult.get(Constants.STATUS);
//        if (resultStatus != Status.SUCCESS) {
//            return checkResult;
//        }*/
//
//        ProcessDefinition processDefine = new ProcessDefinition();
//        Date now = new Date();
//
//        ProcessData processData = JSONUtils.parseObject(processDefinitionJson, ProcessData.class);
//        Map<String, Object> checkProcessJson = checkProcessNodeList(processData, processDefinitionJson);
//        if (checkProcessJson.get(Constants.STATUS) != Status.SUCCESS) {
//            return checkProcessJson;
//        }
//
//        processDefine.setName(name);
//        processDefine.setReleaseState(ReleaseState.OFFLINE);
////        processDefine.setProjectId(project.getId());
//        processDefine.setProjectCode(projectCode);
////        processDefine.setUserId(loginUser.getId());
//        processDefine.setUserCode(userCode);
//        processDefine.setProcessDefinitionJson(processDefinitionJson);
//        processDefine.setDescription(desc);
//        processDefine.setLocations(locations);
//        processDefine.setConnects(connects);
//        processDefine.setTimeout(processData.getTimeout());
//        processDefine.setTenantId(processData.getTenantId());
////        processDefine.setModifyBy(loginUser.getUserName());
//        processDefine.setModifyBy(userCode);
//        processDefine.setResourceIds(getResourceIds(processData));
//
//        //custom global params
//        List<Property> globalParamsList = processData.getGlobalParams();
//        if (CollectionUtils.isNotEmpty(globalParamsList)) {
//            Set<Property> globalParamsSet = new HashSet<>(globalParamsList);
//            globalParamsList = new ArrayList<>(globalParamsSet);
//            processDefine.setGlobalParamList(globalParamsList);
//        }
//        processDefine.setCreateTime(now);
//        processDefine.setUpdateTime(now);
//        processDefine.setFlag(Flag.YES);
//        processDefinitionMapper.insert(processDefine);
//        putMsg(result, Status.SUCCESS);
//        result.put(PROCESSDEFINITIONID, processDefine.getId());
//        return result;
//    }
//
//    @Override
//    public Map<String, Object> releaseProcessDefinition(/*User loginUser*/String userCode, String projectName, int id, int releaseState) {
//        HashMap<String, Object> result = new HashMap<>();
//        //TODO 使用枚举创建项目信息
//        Project project = projectMapper.queryByName(projectName);
//        //TODO 校验用户权限是否有操作项目权限
//        /*Map<String, Object> checkResult = projectService.checkProjectAndAuth(loginUser, project, projectName);
//        Status resultEnum = (Status) checkResult.get(Constants.STATUS);
//        if (resultEnum != Status.SUCCESS) {
//            return checkResult;
//        }*/
//
//        ReleaseState state = ReleaseState.getEnum(releaseState);
//
//        // check state
//        if (null == state) {
//            putMsg(result, Status.REQUEST_PARAMS_NOT_VALID_ERROR, "releaseState");
//            return result;
//        }
//
//        ProcessDefinition processDefinition = processDefinitionMapper.selectById(id);
//
//        switch (state) {
//            case ONLINE:
//                // To check resources whether they are already cancel authorized or deleted
//                String resourceIds = processDefinition.getResourceIds();
//                if (StringUtils.isNotBlank(resourceIds)) {
//                    //TODO 目前不涉及资源校验
//                    /*Integer[] resourceIdArray = Arrays.stream(resourceIds.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
//                    PermissionCheck<Integer> permissionCheck = new PermissionCheck(AuthorizationType.RESOURCE_FILE_ID, processService, resourceIdArray, loginUser.getId(), logger);
//                    try {
//                        permissionCheck.checkPermission();
//                    } catch (Exception e) {
//                        logger.error(e.getMessage(), e);
//                        putMsg(result, Status.RESOURCE_NOT_EXIST_OR_NO_PERMISSION, "releaseState");
//                        return result;
//                    }*/
//                }
//
//                processDefinition.setReleaseState(state);
//                processDefinitionMapper.updateById(processDefinition);
//                break;
//            case OFFLINE:
//                processDefinition.setReleaseState(state);
//                processDefinitionMapper.updateById(processDefinition);
//                List<Schedule> scheduleList = scheduleMapper.selectAllByProcessDefineArray(
//                        new int[]{processDefinition.getId()}
//                );
//
//                for (Schedule schedule : scheduleList) {
//                    logger.info("set schedule offline, project id: {}, schedule id: {}, process definition id: {}", projectName, schedule.getId(), id);
//                    // set status
//                    schedule.setReleaseState(ReleaseState.OFFLINE);
//                    scheduleMapper.updateById(schedule);
//                    //TODO 更具项目编码删除调度
//                    schedulerService.deleteSchedule(project.getId(), schedule.getId());
////                    schedulerService.deleteSchedule(projectCode, schedule.getId());
//                }
//                break;
//            default:
//                putMsg(result, Status.REQUEST_PARAMS_NOT_VALID_ERROR, "releaseState");
//                return result;
//        }
//
//        putMsg(result, Status.SUCCESS);
//        return result;
//    }
//
//    @Override
//    public Map<String, Object> execProcessInstance(/*User loginUser*/String userCode,
//                                                                     Long projectCode,
//                                                                     int processDefinitionId,
//                                                                     String cronTime,
//                                                                     CommandType commandType,
//                                                                     FailureStrategy failureStrategy,
//                                                                     String startNodeList,
//                                                                     TaskDependType taskDependType,
//                                                                     WarningType warningType,
//                                                                     int warningGroupId,
//                                                                     String receivers,
//                                                                     String receiversCc,
//                                                                     RunMode runMode,
//                                                                     Priority processInstancePriority,
//                                                                     String workerGroup,
//                                                                     Integer timeout) throws ParseException {
//        Map<String, Object> result = new HashMap<>(5);
//        // timeout is invalid
//        if (timeout <= 0 || timeout > MAX_TASK_TIMEOUT) {
//            putMsg(result, Status.TASK_TIMEOUT_PARAMS_ERROR);
//            return result;
//        }
//        //TODO 使用枚举创建项目信息
////        Project project = projectMapper.queryByName(projectName);
//        //TODO 校验用户权限是否有操作项目权限
//        /*Map<String, Object> checkResultAndAuth = checkResultAndAuth(loginUser, projectName, project);
//        if (checkResultAndAuth != null) {
//            return checkResultAndAuth;
//        }*/
//
//        // check process define release state
//        ProcessDefinition processDefinition = processDefinitionMapper.selectById(processDefinitionId);
//        result = checkProcessDefinitionValid(processDefinition, processDefinitionId);
//        if (result.get(Constants.STATUS) != Status.SUCCESS) {
//            return result;
//        }
//
//        //TODO 校验租户
//        /*if (!checkTenantSuitable(processDefinition)) {
//            logger.error("there is not any valid tenant for the process definition: id:{},name:{}, ",
//                    processDefinition.getId(), processDefinition.getName());
//            putMsg(result, Status.TENANT_NOT_SUITABLE);
//            return result;
//        }*/
//
//        // check master exists
//        if (!checkMasterExists(result)) {
//            return result;
//        }
//
//
//        /**
//         * create command
//         */
//        int create = this.createCommand(commandType, processDefinitionId,
//                taskDependType, failureStrategy, startNodeList, cronTime, warningType, /*loginUser.getId()*/userCode,
//                warningGroupId, runMode, processInstancePriority, workerGroup);
//        if (create > 0) {
//            /**
//             * according to the process definition ID updateProcessInstance and CC recipient
//             */
//            processDefinition.setReceivers(receivers);
//            processDefinition.setReceiversCc(receiversCc);
//            processDefinitionMapper.updateById(processDefinition);
//            putMsg(result, Status.SUCCESS);
//        } else {
//            putMsg(result, Status.START_PROCESS_INSTANCE_ERROR);
//        }
//        return result;
//    }
//
//    /**
//     * check the process definition node meets the specifications
//     *
//     * @param processData           process data
//     * @param processDefinitionJson process definition json
//     * @return check result code
//     */
//    public Map<String, Object> checkProcessNodeList(ProcessData processData, String processDefinitionJson) {
//
//        Map<String, Object> result = new HashMap<>(5);
//        try {
//            if (processData == null) {
//                logger.error("process data is null");
//                putMsg(result, Status.DATA_IS_NOT_VALID, processDefinitionJson);
//                return result;
//            }
//
//            // Check whether the task node is normal
//            List<TaskNode> taskNodes = processData.getTasks();
//
//            if (taskNodes == null) {
//                logger.error("process node info is empty");
//                putMsg(result, Status.DATA_IS_NULL, processDefinitionJson);
//                return result;
//            }
//
//            // check has cycle
//            if (graphHasCycle(taskNodes)) {
//                logger.error("process DAG has cycle");
//                putMsg(result, Status.PROCESS_NODE_HAS_CYCLE);
//                return result;
//            }
//
//            // check whether the process definition json is normal
//            for (TaskNode taskNode : taskNodes) {
//                if (!CheckUtils.checkTaskNodeParameters(taskNode.getParams(), taskNode.getType())) {
//                    logger.error("task node {} parameter invalid", taskNode.getName());
//                    putMsg(result, Status.PROCESS_NODE_S_PARAMETER_INVALID, taskNode.getName());
//                    return result;
//                }
//
//                // check extra params
//                CheckUtils.checkOtherParams(taskNode.getExtras());
//            }
//            putMsg(result, Status.SUCCESS);
//        } catch (Exception e) {
//            result.put(Constants.STATUS, Status.REQUEST_PARAMS_NOT_VALID_ERROR);
//            result.put(Constants.MSG, e.getMessage());
//        }
//        return result;
//    }
//
//    /**
//     * whether the graph has a ring
//     *
//     * @param taskNodeResponseList task node response list
//     * @return if graph has cycle flag
//     */
//    private boolean graphHasCycle(List<TaskNode> taskNodeResponseList) {
//        DAG<String, TaskNode, String> graph = new DAG<>();
//
//        // Fill the vertices
//        for (TaskNode taskNodeResponse : taskNodeResponseList) {
//            graph.addNode(taskNodeResponse.getName(), taskNodeResponse);
//        }
//
//        // Fill edge relations
//        for (TaskNode taskNodeResponse : taskNodeResponseList) {
//            taskNodeResponse.getPreTasks();
//            List<String> preTasks = JSONUtils.toList(taskNodeResponse.getPreTasks(), String.class);
//            if (CollectionUtils.isNotEmpty(preTasks)) {
//                for (String preTask : preTasks) {
//                    if (!graph.addEdge(preTask, taskNodeResponse.getName())) {
//                        return true;
//                    }
//                }
//            }
//        }
//
//        return graph.hasCycle();
//    }
//
//    /**
//     * get resource ids
//     *
//     * @param processData process data
//     * @return resource ids
//     */
//    private String getResourceIds(ProcessData processData) {
//        List<TaskNode> tasks = processData.getTasks();
//        Set<Integer> resourceIds = new HashSet<>();
//        for (TaskNode taskNode : tasks) {
//            String taskParameter = taskNode.getParams();
//            AbstractParameters params = TaskParametersUtils.getParameters(taskNode.getType(), taskParameter);
//            if (CollectionUtils.isNotEmpty(params.getResourceFilesList())) {
//                Set<Integer> tempSet = params.getResourceFilesList().stream().filter(t -> t.getId() != 0).map(ResourceInfo::getId).collect(Collectors.toSet());
//                resourceIds.addAll(tempSet);
//            }
//        }
//
//        StringBuilder sb = new StringBuilder();
//        for (int i : resourceIds) {
//            if (sb.length() > 0) {
//                sb.append(",");
//            }
//            sb.append(i);
//        }
//        return sb.toString();
//    }
//
////    /**
////     * check result and auth
////     *
////     * @param loginUser
////     * @param projectName
////     * @param project
////     * @return
////     */
////    private Map<String, Object> checkResultAndAuth(User loginUser, String projectName, Project project) {
////        // check project auth
////        Map<String, Object> checkResult = projectService.checkProjectAndAuth(loginUser, project, projectName);
////        Status status = (Status) checkResult.get(Constants.STATUS);
////        if (status != Status.SUCCESS) {
////            return checkResult;
////        }
////        return null;
////    }
//
////    /**
////     * check tenant suitable
////     *
////     * @param processDefinition process definition
////     * @return true if tenant suitable, otherwise return false
////     */
////    private boolean checkTenantSuitable(ProcessDefinition processDefinition) {
////        // checkTenantExists();
////        Tenant tenant = processService.getTenantForProcess(processDefinition.getTenantId(),
////                processDefinition.getUserId());
////        return tenant != null;
////    }
//
//    /**
//     * create command
//     *
//     * @param commandType             commandType
//     * @param processDefineId         processDefineId
//     * @param nodeDep                 nodeDep
//     * @param failureStrategy         failureStrategy
//     * @param startNodeList           startNodeList
//     * @param schedule                schedule
//     * @param warningType             warningType
//     * @param executor                executorId
//     * @param warningGroupId          warningGroupId
//     * @param runMode                 runMode
//     * @param processInstancePriority processInstancePriority
//     * @param workerGroup             workerGroup
//     * @return command id
//     * @throws ParseException
//     */
//    private int createCommand(CommandType commandType, int processDefineId,
//                              TaskDependType nodeDep, FailureStrategy failureStrategy,
//                              String startNodeList, String schedule, WarningType warningType,
//            /*int executorId*/String executor, int warningGroupId,
//                              RunMode runMode, Priority processInstancePriority, String workerGroup) throws ParseException {
//
//        /**
//         * instantiate command schedule instance
//         */
//        Command command = new Command();
//
//        Map<String, String> cmdParam = new HashMap<>();
//        if (commandType == null) {
//            command.setCommandType(CommandType.START_PROCESS);
//        } else {
//            command.setCommandType(commandType);
//        }
//        command.setProcessDefinitionId(processDefineId);
//        if (nodeDep != null) {
//            command.setTaskDependType(nodeDep);
//        }
//        if (failureStrategy != null) {
//            command.setFailureStrategy(failureStrategy);
//        }
//
//        if (StringUtils.isNotEmpty(startNodeList)) {
//            cmdParam.put(Constants.CMD_PARAM_START_NODE_NAMES, startNodeList);
//        }
//        if (warningType != null) {
//            command.setWarningType(warningType);
//        }
//        command.setCommandParam(JSONUtils.toJsonString(cmdParam));
////        command.setExecutorId(executorId);
//        //TODO 执行用户
//        command.setExecutor(executor);
//        command.setWarningGroupId(warningGroupId);
//        command.setProcessInstancePriority(processInstancePriority);
//        command.setWorkerGroup(workerGroup);
//
//        Date start = null;
//        Date end = null;
//        if (StringUtils.isNotEmpty(schedule)) {
//            String[] interval = schedule.split(",");
//            if (interval.length == 2) {
//                start = DateUtils.getScheduleDate(interval[0]);
//                end = DateUtils.getScheduleDate(interval[1]);
//            }
//        }
//
//        // determine whether to complement
//        if (commandType == CommandType.COMPLEMENT_DATA) {
//            runMode = (runMode == null) ? RunMode.RUN_MODE_SERIAL : runMode;
//            if (null != start && null != end && !start.after(end)) {
//                if (runMode == RunMode.RUN_MODE_SERIAL) {
//                    cmdParam.put(CMDPARAM_COMPLEMENT_DATA_START_DATE, DateUtils.dateToString(start));
//                    cmdParam.put(CMDPARAM_COMPLEMENT_DATA_END_DATE, DateUtils.dateToString(end));
//                    command.setCommandParam(JSONUtils.toJsonString(cmdParam));
//                    return processService.createCommand(command);
//                } else if (runMode == RunMode.RUN_MODE_PARALLEL) {
//                    List<Schedule> schedules = processService.queryReleaseSchedulerListByProcessDefinitionId(processDefineId);
//                    List<Date> listDate = new LinkedList<>();
//                    if (CollectionUtils.isNotEmpty(schedules)) {
//                        for (Schedule item : schedules) {
//                            listDate.addAll(CronUtils.getSelfFireDateList(start, end, item.getCrontab()));
//                        }
//                    }
//                    if (CollectionUtils.isNotEmpty(listDate)) {
//                        // loop by schedule date
//                        for (Date date : listDate) {
//                            cmdParam.put(CMDPARAM_COMPLEMENT_DATA_START_DATE, DateUtils.dateToString(date));
//                            cmdParam.put(CMDPARAM_COMPLEMENT_DATA_END_DATE, DateUtils.dateToString(date));
//                            command.setCommandParam(JSONUtils.toJsonString(cmdParam));
//                            processService.createCommand(command);
//                        }
//                        return listDate.size();
//                    } else {
//                        // loop by day
//                        int runCunt = 0;
//                        while (!start.after(end)) {
//                            runCunt += 1;
//                            cmdParam.put(CMDPARAM_COMPLEMENT_DATA_START_DATE, DateUtils.dateToString(start));
//                            cmdParam.put(CMDPARAM_COMPLEMENT_DATA_END_DATE, DateUtils.dateToString(start));
//                            command.setCommandParam(JSONUtils.toJsonString(cmdParam));
//                            processService.createCommand(command);
//                            start = DateUtils.getSomeDay(start, 1);
//                        }
//                        return runCunt;
//                    }
//                }
//            } else {
//                logger.error("there is not valid schedule date for the process definition: id:{},date:{}",
//                        processDefineId, schedule);
//            }
//        } else {
//            command.setCommandParam(JSONUtils.toJsonString(cmdParam));
//            return processService.createCommand(command);
//        }
//
//        return 0;
//    }
//
//    /**
//     * check whether the process definition can be executed
//     *
//     * @param processDefinition process definition
//     * @param processDefineId   process definition id
//     * @return check result code
//     */
//    public Map<String, Object> checkProcessDefinitionValid(ProcessDefinition processDefinition, int processDefineId) {
//        Map<String, Object> result = new HashMap<>(5);
//        if (processDefinition == null) {
//            // check process definition exists
//            putMsg(result, Status.PROCESS_DEFINE_NOT_EXIST, processDefineId);
//        } else if (processDefinition.getReleaseState() != ReleaseState.ONLINE) {
//            // check process definition online
//            putMsg(result, Status.PROCESS_DEFINE_NOT_RELEASE, processDefineId);
//        } else {
//            result.put(Constants.STATUS, Status.SUCCESS);
//        }
//        return result;
//    }
//
//    /**
//     * check whether master exists
//     *
//     * @param result result
//     * @return master exists return true , otherwise return false
//     */
//    private boolean checkMasterExists(Map<String, Object> result) {
//        // check master server exists
////        List<Server> masterServers = monitorService.getServerListFromZK(true);
//        List<Server> masterServers = monitorService.getServerListFromRegistry(true);
//        // no master
//        if (masterServers.size() == 0) {
//            putMsg(result, Status.MASTER_NOT_EXISTS);
//            return false;
//        }
//        return true;
//    }
//
//}
