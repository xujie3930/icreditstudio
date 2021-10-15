package org.apache.dolphinscheduler.api.service.impl;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dolphinscheduler.api.enums.Status;
import org.apache.dolphinscheduler.api.param.CreatePlatformProcessDefinitionParam;
import org.apache.dolphinscheduler.api.param.ReleasePlatformProcessDefinitionParam;
import org.apache.dolphinscheduler.api.service.PlatformProcessDefinitionService;
import org.apache.dolphinscheduler.api.service.PlatformSchedulerService;
import org.apache.dolphinscheduler.api.service.result.CreatePlatformTaskResult;
import org.apache.dolphinscheduler.api.utils.CheckUtils;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.common.enums.Flag;
import org.apache.dolphinscheduler.common.enums.ReleaseState;
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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Peng
 */
@Slf4j
@Service
public class PlatformProcessDefinitionServiceImpl extends BaseServiceImpl implements PlatformProcessDefinitionService {

    @Resource
    private ProcessDefinitionMapper processDefinitionMapper;
    @Resource
    private ScheduleMapper scheduleMapper;
    @Resource
    private PlatformSchedulerService schedulerService;

    @Override
    public BusinessResult<CreatePlatformTaskResult> create(CreatePlatformProcessDefinitionParam param) {
        ProcessDefinition processDefine = new ProcessDefinition();
        Date now = new Date();

        ProcessData processData = JSONUtils.parseObject(param.getProcessDefinitionJson(), ProcessData.class);
        Map<String, Object> checkProcessJson = checkProcessNodeList(processData, param.getProcessDefinitionJson());
        if (checkProcessJson.get(Constants.STATUS) != Status.SUCCESS) {
            return BusinessResult.fail("", (String) checkProcessJson.get(Constants.MSG));
        }
        processDefine.setName(param.getName());
        processDefine.setReleaseState(ReleaseState.OFFLINE);
        processDefine.setProjectCode(param.getProjectCode());
        processDefine.setUserId(param.getAccessUser().getId());
        processDefine.setProcessDefinitionJson(param.getProcessDefinitionJson());
        processDefine.setDescription(param.getDesc());
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

    @Override
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
                    log.info("set schedule offline, project id: {}, schedule id: {}, process definition id: {}", param.getProjectCode(), schedule.getId(), param.getProcessDefinitionId());
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
                log.error("process data is null");
                putMsg(result, Status.DATA_IS_NOT_VALID, processDefinitionJson);
                return result;
            }

            // Check whether the task node is normal
            List<TaskNode> taskNodes = processData.getTasks();

            if (taskNodes == null) {
                log.error("process node info is empty");
                putMsg(result, Status.DATA_IS_NULL, processDefinitionJson);
                return result;
            }

            // check has cycle
            if (graphHasCycle(taskNodes)) {
                log.error("process DAG has cycle");
                putMsg(result, Status.PROCESS_NODE_HAS_CYCLE);
                return result;
            }

            // check whether the process definition json is normal
            for (TaskNode taskNode : taskNodes) {
                if (!CheckUtils.checkTaskNodeParameters(taskNode.getParams(), taskNode.getType())) {
                    log.error("task node {} parameter invalid", taskNode.getName());
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
