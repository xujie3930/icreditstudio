package org.apache.dolphinscheduler.api.service.impl;

import cn.hutool.core.date.CalendarUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dolphinscheduler.api.dto.ScheduleParam;
import org.apache.dolphinscheduler.api.enums.ScheduleType;
import org.apache.dolphinscheduler.api.enums.Status;
import org.apache.dolphinscheduler.api.param.*;
import org.apache.dolphinscheduler.api.service.PlatformProcessDefinitionService;
import org.apache.dolphinscheduler.api.service.PlatformSchedulerService;
import org.apache.dolphinscheduler.api.service.result.CreatePlatformTaskResult;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.common.enums.*;
import org.apache.dolphinscheduler.common.process.Property;
import org.apache.dolphinscheduler.common.utils.CollectionUtils;
import org.apache.dolphinscheduler.common.utils.JSONUtils;
import org.apache.dolphinscheduler.dao.entity.ProcessData;
import org.apache.dolphinscheduler.dao.entity.ProcessDefinition;
import org.apache.dolphinscheduler.dao.entity.Schedule;
import org.apache.dolphinscheduler.dao.mapper.ProcessDefinitionMapper;
import org.apache.dolphinscheduler.dao.mapper.ScheduleMapper;
import org.apache.dolphinscheduler.service.increment.IncrementUtil;
import org.apache.dolphinscheduler.service.quartz.PlatformPartitionParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Peng
 */
@Slf4j
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
    public CreatePlatformTaskResult create(CreatePlatformProcessDefinitionParam param) {
        log.info("创建同步任务定义参数：" + JSONObject.toJSONString(param));
        ProcessDefinition processDefine = new ProcessDefinition();
        Date now = new Date();

        ProcessDefinitionJson definitionJson = param.buildProcessDefinitionJson();
        ProcessData processData = JSONUtils.parseObject(JSONObject.toJSONString(definitionJson), ProcessData.class);

        PlatformPartitionParam platformPartitionParam = BeanCopyUtils.copyProperties(param.getPartitionParam(), PlatformPartitionParam.class);

        if (Objects.isNull(param.getSchedulerParam())) {
            param.setSchedulerParam(new SchedulerParam());
        }
        PlatformPartitionParam syncCondition = IncrementUtil.getSyncCondition(platformPartitionParam, param.getSchedulerParam().getCron());
        processDefine.setPartitionParam(JSONObject.toJSONString(syncCondition));

        processDefine.setWorkspaceId(param.getOrdinaryParam().getWorkspaceId());

        processDefine.setTaskType(param.getOrdinaryParam().getTaskType());
        processDefine.setVersion(param.getOrdinaryParam().getVersion());
        processDefine.setTargetTable(param.getOrdinaryParam().getTargetTable());
        processDefine.setSourceTable(param.getOrdinaryParam().getSourceTableStr());
        processDefine.setCron(param.getOrdinaryParam().getCron());

        processDefine.setScheduleType(0);
        processDefine.setPlatformTaskId(param.getOrdinaryParam().getPlatformTaskId());
        processDefine.setName(param.getOrdinaryParam().getName());
        processDefine.setReleaseState(param.getOrdinaryParam().getReleaseState());
        processDefine.setProjectCode(param.getOrdinaryParam().getProjectCode());
        processDefine.setUserId(param.getAccessUser().getId());
        processDefine.setProcessDefinitionJson(JSONObject.toJSONString(definitionJson));
        processDefine.setTimeout(processData.getTimeout());
        processDefine.setTenantCode(param.getAccessUser().getTenantCode());
        processDefine.setModifyBy(param.getAccessUser().getTenantCode());
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
        //如果是周期执行，则给流程创建scheduler
        if (param.getSchedulerParam().getSchedulerType() == ScheduleType.PERIOD) {
            CreateSchedulerParam createSchedulerParam = buildCreateSchedulerParam(param, processDefine.getId());
            schedulerService.createSchedule(createSchedulerParam);
        }

        CreatePlatformTaskResult result = new CreatePlatformTaskResult();
        result.setProcessDefinitionId(processDefine.getId());
        return result;
    }


    private CreateSchedulerParam buildCreateSchedulerParam(CreatePlatformProcessDefinitionParam param, String processDefinitionId) {

        Date startTime = new Date();
        Date endTime = DateUtil.offset(startTime, DateField.YEAR, 10);
        ScheduleParam scheduleParam = new ScheduleParam(startTime, endTime, CalendarUtil.calendar().getTimeZone().getID(), param.getSchedulerParam().getCron());
        return CreateSchedulerParam.builder()
                .accessUser(param.getAccessUser())
                .processDefineId(processDefinitionId)
                .schedule(scheduleParam)
                .processInstancePriority(Priority.MEDIUM)
                .projectCode(param.getOrdinaryParam().getProjectCode())
                .failureStrategy(FailureStrategy.CONTINUE)
                .warningType(WarningType.NONE)
                .workerGroup("default")
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> release(ReleasePlatformProcessDefinitionParam param) {
        ReleaseState state = ReleaseState.getEnum(param.getReleaseState());

        HashMap<String, Object> result = new HashMap<>(5);
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
        log.info("编辑同步任务定义参数：" + JSONObject.toJSONString(param));
        Map<String, Object> result = new HashMap<>(5);

        ProcessDefinition processDefine = processDefinitionMapper.selectById(param.getProcessDefinitionId());
        // check process definition exists
        if (processDefine == null) {
            logger.error("process definition  does not exist");
            putMsg(result, Status.PROCESS_DEFINE_NOT_EXIST, param.getProcessDefinitionId());
            return BusinessResult.fail("", (String) result.get(Constants.MSG));
        }

        if (processDefine.getReleaseState() == ReleaseState.ONLINE) {
            // online can not permit edit
            logger.error("process definition {} does not allow edit", ReleaseState.ONLINE.getDescp());
            putMsg(result, Status.PROCESS_DEFINE_NOT_ALLOWED_EDIT, processDefine.getName());
            return BusinessResult.fail("", (String) result.get(Constants.MSG));
        }

        if (!param.getOrdinaryParam().getName().equals(processDefine.getName())) {
            // check whether the new process define name exist
            ProcessDefinition definition = processDefinitionMapper.verifyByDefineName(param.getOrdinaryParam().getProjectCode(), param.getOrdinaryParam().getName());
            if (definition != null) {
                putMsg(result, Status.VERIFY_PROCESS_DEFINITION_NAME_UNIQUE_ERROR, param.getOrdinaryParam().getName());
                return BusinessResult.fail("", (String) result.get(Constants.MSG));
            }
        }

        CreatePlatformProcessDefinitionParam definitionParam = BeanCopyUtils.copyProperties(param, CreatePlatformProcessDefinitionParam.class);
        ProcessDefinitionJson definitionJson = param.buildProcessDefinitionJson();
        ProcessData processData = JSONUtils.parseObject(JSONObject.toJSONString(definitionJson), ProcessData.class);
        Date now = new Date();

        processDefine.setTaskType(param.getOrdinaryParam().getTaskType());
        processDefine.setVersion(param.getOrdinaryParam().getVersion());
        processDefine.setTargetTable(param.getOrdinaryParam().getTargetTable());
        processDefine.setSourceTable(param.getOrdinaryParam().getSourceTableStr());
        processDefine.setCron(param.getOrdinaryParam().getCron());

        processDefine.setPlatformTaskId(param.getOrdinaryParam().getPlatformTaskId());
        processDefine.setName(param.getOrdinaryParam().getName());
        processDefine.setReleaseState(param.getOrdinaryParam().getReleaseState());
        processDefine.setProjectCode(param.getOrdinaryParam().getProjectCode());
        processDefine.setUserId(param.getAccessUser().getId());
        processDefine.setProcessDefinitionJson(JSONObject.toJSONString(definitionJson));
        processDefine.setTimeout(processData.getTimeout());
        processDefine.setTenantCode(param.getAccessUser().getTenantCode());
        processDefine.setModifyBy(param.getAccessUser().getTenantCode());
        //custom global params
        List<Property> globalParamsList = processData.getGlobalParams();
        if (CollectionUtils.isNotEmpty(globalParamsList)) {
            Set<Property> globalParamsSet = new HashSet<>(globalParamsList);
            globalParamsList = new ArrayList<>(globalParamsSet);
            processDefine.setGlobalParamList(globalParamsList);
        }
        processDefine.setUpdateTime(now);
        processDefine.setFlag(Flag.YES);
        processDefinitionMapper.updateById(processDefine);

        //如果是周期执行，则给流程创建scheduler
        if (param.getSchedulerParam().getSchedulerType() == ScheduleType.PERIOD) {
            CreateSchedulerParam createSchedulerParam = buildUpdateSchedulerParam(param, processDefine.getId());
            schedulerService.createSchedule(createSchedulerParam);
        }
        return BusinessResult.success(true);
    }

    private CreateSchedulerParam buildUpdateSchedulerParam(UpdatePlatformProcessDefinitionParam param, String processDefinitionId) {

        Date startTime = new Date();
        Date endTime = DateUtil.offset(startTime, DateField.YEAR, 10);
        ScheduleParam scheduleParam = new ScheduleParam(startTime, endTime, CalendarUtil.calendar().getTimeZone().getID(), param.getSchedulerParam().getCron());
        return CreateSchedulerParam.builder()
                .accessUser(param.getAccessUser())
                .processDefineId(processDefinitionId)
                .schedule(scheduleParam)
                .processInstancePriority(Priority.MEDIUM)
                .projectCode(param.getOrdinaryParam().getProjectCode())
                .failureStrategy(FailureStrategy.CONTINUE)
                .warningType(WarningType.NONE)
                .workerGroup("default")
                .build();
    }
}
