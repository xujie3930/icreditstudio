package org.apache.dolphinscheduler.api.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.enums.Status;
import org.apache.dolphinscheduler.api.param.*;
import org.apache.dolphinscheduler.api.service.PlatformProcessDefinitionService;
import org.apache.dolphinscheduler.api.service.PlatformSchedulerService;
import org.apache.dolphinscheduler.api.service.result.CreatePlatformTaskResult;
import org.apache.dolphinscheduler.api.utils.CheckUtils;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.common.enums.Flag;
import org.apache.dolphinscheduler.common.enums.ReleaseState;
import org.apache.dolphinscheduler.common.enums.TaskType;
import org.apache.dolphinscheduler.common.graph.DAG;
import org.apache.dolphinscheduler.common.model.TaskNode;
import org.apache.dolphinscheduler.common.process.Property;
import org.apache.dolphinscheduler.common.utils.CollectionUtils;
import org.apache.dolphinscheduler.common.utils.JSONUtils;
import org.apache.dolphinscheduler.dao.entity.ProcessData;
import org.apache.dolphinscheduler.dao.entity.ProcessDefinition;
import org.apache.dolphinscheduler.dao.entity.Schedule;
import org.apache.dolphinscheduler.dao.mapper.ProcessDefinitionMapper;
import org.apache.dolphinscheduler.dao.mapper.ScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Peng
 */
@Service
public class PlatformProcessDefinitionServiceImpl extends BaseServiceImpl implements PlatformProcessDefinitionService {

    private static final Logger logger = LoggerFactory.getLogger(PlatformProcessDefinitionServiceImpl.class);

    @Resource
    private ProcessDefinitionMapper processDefinitionMapper;
    @Resource
    private ScheduleMapper scheduleMapper;
    @Resource
    private PlatformSchedulerService schedulerService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<CreatePlatformTaskResult> create(CreatePlatformProcessDefinitionParam param) {
        System.out.println(JSONObject.toJSONString(param));
        ProcessDefinition processDefine = new ProcessDefinition();
        Date now = new Date();

        ProcessDefinitionJson definitionJson = buildProcessDefinitionJson(param);
        ProcessData processData = JSONUtils.parseObject(JSONObject.toJSONString(definitionJson), ProcessData.class);

        processDefine.setPlatformTaskId(param.getOrdinaryParam().getPlatformTaskId());
        processDefine.setName(param.getOrdinaryParam().getName());
        processDefine.setReleaseState(ReleaseState.OFFLINE);
        processDefine.setProjectCode(param.getOrdinaryParam().getProjectCode());
        processDefine.setUserId(param.getAccessUser().getId());
        processDefine.setProcessDefinitionJson(JSONObject.toJSONString(definitionJson));
        processDefine.setTimeout(processData.getTimeout());
        processDefine.setTenantCode(param.getAccessUser().getTenantCode());
        processDefine.setModifyBy(param.getAccessUser().getId());
        //custom global params
        List<Property> globalParamsList = processData.getGlobalParams();
        if (CollectionUtils.isNotEmpty(globalParamsList)) {
            Set<Property> globalParamsSet = new HashSet<>(globalParamsList);
            globalParamsList = new ArrayList<>(globalParamsSet);
            processDefine.setGlobalParamList(globalParamsList);
        }
        processDefine.setCreateTime(now);
        processDefine.setUpdateTime(now);
        processDefine.setFlag(Flag.YES);
        processDefinitionMapper.insert(processDefine);

        CreatePlatformTaskResult result = new CreatePlatformTaskResult();
        result.setProcessDefinitionId(processDefine.getId());
        return BusinessResult.success(result);
    }

    private ProcessDefinitionJson buildProcessDefinitionJson(CreatePlatformProcessDefinitionParam param) {
        ProcessDefinitionJson definitionJson = new ProcessDefinitionJson();
        definitionJson.setTimeout(param.getOrdinaryParam().getTimeOut());
        definitionJson.setTenantCode(param.getAccessUser().getTenantCode());
        definitionJson.setGlobalParams(Lists.newArrayList());
        List<TaskNodeStruct> structs = Lists.newArrayList();
        TaskNodeStruct struct = new TaskNodeStruct();
        struct.setType(TaskType.DATAX.getDesc());
        struct.setId(StrUtil.concat(true, "tasks-", RandomUtil.randomNumbers(5)));
        struct.setName(param.getOrdinaryParam().getName());
        struct.setParams(new NodeParam(1, param.getOrdinaryParam().getTaskJson(), Lists.newArrayList()));
        struct.setDescription("");
        struct.setTimeout(new TimeOutParam("", null, false));
        struct.setRunFlag("NORMAL");
        struct.setConditionResult(new ConditionResult(Lists.newArrayList(), Lists.newArrayList()));
        struct.setDependence(Maps.newHashMap());
        struct.setMaxRetryTimes("0");
        struct.setRetryInterval("1");
        struct.setTaskInstancePriority("MEDIUM");
        struct.setWorkerGroup("default");
        struct.setPreTasks(Lists.newArrayList());

        structs.add(struct);
        definitionJson.setTasks(structs);
        return definitionJson;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> release(ReleasePlatformProcessDefinitionParam param) {
        ReleaseState state = ReleaseState.getEnum(param.getReleaseState());

        HashMap<String, Object> result = new HashMap<>();
        // check state
        if (null == state) {
            putMsg(result, Status.REQUEST_PARAMS_NOT_VALID_ERROR, "releaseState");
            return BusinessResult.fail("", (String) result.get(Constants.MSG));
        }

        ProcessDefinition processDefinition = processDefinitionMapper.selectById(param.getProcessDefinitionId());

        switch (state) {
            case ONLINE:
                processDefinition.setReleaseState(state);
                processDefinitionMapper.updateById(processDefinition);
                break;
            case OFFLINE:
                processDefinition.setReleaseState(state);
                processDefinitionMapper.updateById(processDefinition);
                List<Schedule> scheduleList = scheduleMapper.selectAllByProcessDefineArray(
                        new String[]{processDefinition.getId()}
                );

                for (Schedule schedule : scheduleList) {
                    logger.info("set schedule offline, project id: {}, schedule id: {}, process definition id: {}", param.getProjectCode(), schedule.getId(), param.getProcessDefinitionId());
                    // set status
                    schedule.setReleaseState(ReleaseState.OFFLINE);
                    scheduleMapper.updateById(schedule);
                    schedulerService.deleteSchedule(param.getProjectCode(), schedule.getId());
                }
                break;
            default:
                putMsg(result, Status.REQUEST_PARAMS_NOT_VALID_ERROR, "releaseState");
                return BusinessResult.fail("", (String) result.get(Constants.MSG));
        }
        return BusinessResult.success(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> delete(DeletePlatformProcessDefinitionParam param) {
        Map<String, Object> result = new HashMap<>(5);
        ProcessDefinition processDefinition = processDefinitionMapper.selectById(param.getProcessDefinitionId());

        if (processDefinition == null) {
            putMsg(result, Status.PROCESS_DEFINE_NOT_EXIST, param.getProcessDefinitionId());
            return BusinessResult.fail("", (String) result.get(Constants.MSG));
        }

        // check process definition is already online
        if (processDefinition.getReleaseState() == ReleaseState.ONLINE) {
            putMsg(result, Status.PROCESS_DEFINE_STATE_ONLINE, param.getProcessDefinitionId());
            return BusinessResult.fail("", (String) result.get(Constants.MSG));
        }

        // get the timing according to the process definition
        List<Schedule> schedules = scheduleMapper.queryByProcessDefinitionId(param.getProcessDefinitionId());
        if (!schedules.isEmpty() && schedules.size() > 1) {
            logger.warn("scheduler num is {},Greater than 1", schedules.size());
            putMsg(result, Status.DELETE_PROCESS_DEFINE_BY_ID_ERROR);
            return BusinessResult.fail("", (String) result.get(Constants.MSG));
        } else if (schedules.size() == 1) {
            Schedule schedule = schedules.get(0);
            if (schedule.getReleaseState() == ReleaseState.OFFLINE) {
                scheduleMapper.deleteById(schedule.getId());
            } else if (schedule.getReleaseState() == ReleaseState.ONLINE) {
                putMsg(result, Status.SCHEDULE_CRON_STATE_ONLINE, schedule.getId());
                return BusinessResult.fail("", (String) result.get(Constants.MSG));
            }
        }

        int delete = processDefinitionMapper.deleteById(param.getProcessDefinitionId());

        if (delete > 0) {
            putMsg(result, Status.SUCCESS);
        } else {
            putMsg(result, Status.DELETE_PROCESS_DEFINE_BY_ID_ERROR);
        }
        return BusinessResult.fail("", (String) result.get(Constants.MSG));
    }

    @Override
    public BusinessResult<Boolean> update(UpdatePlatformProcessDefinitionParam param) {
        Map<String, Object> result = new HashMap<>(5);

        ProcessData processData = JSONUtils.parseObject(param.getProcessDefinitionJson(), ProcessData.class);
        Map<String, Object> checkProcessJson = checkProcessNodeList(processData, param.getProcessDefinitionJson());
        if ((checkProcessJson.get(Constants.STATUS) != Status.SUCCESS)) {
            return BusinessResult.fail("", (String) result.get(Constants.MSG));
        }
        ProcessDefinition processDefine = processDefinitionMapper.selectById(param.getProcessDefinitionId());
        // check process definition exists
        if (processDefine == null) {
            putMsg(result, Status.PROCESS_DEFINE_NOT_EXIST, param.getProcessDefinitionId());
            return BusinessResult.fail("", (String) result.get(Constants.MSG));
        }

        if (processDefine.getReleaseState() == ReleaseState.ONLINE) {
            // online can not permit edit
            putMsg(result, Status.PROCESS_DEFINE_NOT_ALLOWED_EDIT, processDefine.getName());
            return BusinessResult.fail("", (String) result.get(Constants.MSG));
        }

        if (!param.getName().equals(processDefine.getName())) {
            // check whether the new process define name exist
            ProcessDefinition definition = processDefinitionMapper.verifyByDefineName(param.getProjectCode(), param.getName());
            if (definition != null) {
                putMsg(result, Status.VERIFY_PROCESS_DEFINITION_NAME_UNIQUE_ERROR, param.getName());
                return BusinessResult.fail("", (String) result.get(Constants.MSG));
            }
        }

        Date now = new Date();

        processDefine.setId(param.getProcessDefinitionId());
        processDefine.setName(param.getName());
        processDefine.setReleaseState(ReleaseState.OFFLINE);
        processDefine.setProjectCode(param.getProjectCode());
        processDefine.setProcessDefinitionJson(param.getProcessDefinitionJson());
        processDefine.setDescription(param.getDesc());
        processDefine.setTimeout(processData.getTimeout());
        processDefine.setTenantCode(processData.getTenantCode());
        processDefine.setModifyBy(param.getAccessUser().getId());

        //custom global params
        List<Property> globalParamsList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(processData.getGlobalParams())) {
            Set<Property> userDefParamsSet = new HashSet<>(processData.getGlobalParams());
            globalParamsList = new ArrayList<>(userDefParamsSet);
        }
        processDefine.setGlobalParamList(globalParamsList);
        processDefine.setUpdateTime(now);
        processDefine.setFlag(Flag.YES);
        processDefinitionMapper.updateById(processDefine);
        return BusinessResult.success(true);
    }

    /**
     * check the process definition node meets the specifications
     *
     * @param processData           process data
     * @param processDefinitionJson process definition json
     * @return check result code
     */
    public Map<String, Object> checkProcessNodeList(ProcessData processData, String processDefinitionJson) {

        Map<String, Object> result = new HashMap<>(5);
        try {
            if (processData == null) {
                logger.error("process data is null");
                putMsg(result, Status.DATA_IS_NOT_VALID, processDefinitionJson);
                return result;
            }

            // Check whether the task node is normal
            List<TaskNode> taskNodes = processData.getTasks();

            if (taskNodes == null) {
                logger.error("process node info is empty");
                putMsg(result, Status.DATA_IS_NULL, processDefinitionJson);
                return result;
            }

            // check has cycle
            if (graphHasCycle(taskNodes)) {
                logger.error("process DAG has cycle");
                putMsg(result, Status.PROCESS_NODE_HAS_CYCLE);
                return result;
            }

            // check whether the process definition json is normal
            for (TaskNode taskNode : taskNodes) {
                if (!CheckUtils.checkTaskNodeParameters(taskNode.getParams(), taskNode.getType())) {
                    logger.error("task node {} parameter invalid", taskNode.getName());
                    putMsg(result, Status.PROCESS_NODE_S_PARAMETER_INVALID, taskNode.getName());
                    return result;
                }

                // check extra params
                CheckUtils.checkOtherParams(taskNode.getExtras());
            }
            putMsg(result, Status.SUCCESS);
        } catch (Exception e) {
            result.put(Constants.STATUS, Status.REQUEST_PARAMS_NOT_VALID_ERROR);
            result.put(Constants.MSG, e.getMessage());
        }
        return result;
    }

    /**
     * whether the graph has a ring
     *
     * @param taskNodeResponseList task node response list
     * @return if graph has cycle flag
     */
    private boolean graphHasCycle(List<TaskNode> taskNodeResponseList) {
        DAG<String, TaskNode, String> graph = new DAG<>();

        // Fill the vertices
        for (TaskNode taskNodeResponse : taskNodeResponseList) {
            graph.addNode(taskNodeResponse.getName(), taskNodeResponse);
        }

        // Fill edge relations
        for (TaskNode taskNodeResponse : taskNodeResponseList) {
            taskNodeResponse.getPreTasks();
            List<String> preTasks = JSONUtils.toList(taskNodeResponse.getPreTasks(), String.class);
            if (CollectionUtils.isNotEmpty(preTasks)) {
                for (String preTask : preTasks) {
                    if (!graph.addEdge(preTask, taskNodeResponse.getName())) {
                        return true;
                    }
                }
            }
        }
        return graph.hasCycle();
    }

}
