package org.apache.dolphinscheduler.api.service.impl;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dolphinscheduler.api.dto.ScheduleParam;
import org.apache.dolphinscheduler.api.enums.Status;
import org.apache.dolphinscheduler.api.param.CreateSchedulerParam;
import org.apache.dolphinscheduler.api.service.ExecutorService;
import org.apache.dolphinscheduler.api.service.PlatformSchedulerService;
import org.apache.dolphinscheduler.api.service.result.CreateSchedulerResult;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.common.enums.ReleaseState;
import org.apache.dolphinscheduler.common.utils.DateUtils;
import org.apache.dolphinscheduler.common.utils.JSONUtils;
import org.apache.dolphinscheduler.dao.entity.ProcessDefinition;
import org.apache.dolphinscheduler.dao.entity.Schedule;
import org.apache.dolphinscheduler.dao.mapper.ProcessDefinitionMapper;
import org.apache.dolphinscheduler.dao.mapper.ScheduleMapper;
import org.apache.dolphinscheduler.service.process.ProcessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author Peng
 */
@Slf4j
@Service
public class PlatformSchedulerServiceImpl extends BaseServiceImpl implements PlatformSchedulerService {
    @Resource
    private ProcessService processService;
    @Resource
    private ExecutorService executorService;
    @Resource
    private ScheduleMapper scheduleMapper;
    @Resource
    private ProcessDefinitionMapper processDefinitionMapper;

    @Override
    public BusinessResult<CreateSchedulerResult> createSchedule(CreateSchedulerParam param) {
        // check work flow define release state
        ProcessDefinition processDefinition = processService.findProcessDefineById(param.getProcessDefineId());
        Map<String, Object> checkMap = executorService.checkProcessDefinitionValid(processDefinition, param.getProcessDefineId());
        if (checkMap.get(Constants.STATUS) != Status.SUCCESS) {
            return BusinessResult.fail("", (String) checkMap.get(Constants.MSG));
        }
        Schedule scheduleObj = new Schedule();
        Date now = new Date();
        scheduleObj.setProjectCode(param.getProjectCode());
        scheduleObj.setProcessDefinitionId(processDefinition.getId());
        scheduleObj.setProcessDefinitionName(processDefinition.getName());
        ScheduleParam scheduleParam = JSONUtils.parseObject(param.getSchedule(), ScheduleParam.class);
        if (DateUtils.differSec(scheduleParam.getStartTime(), scheduleParam.getEndTime()) == 0) {
            log.warn("The start time must not be the same as the end");
            putMsg(checkMap, Status.SCHEDULE_START_TIME_END_TIME_SAME);
            return BusinessResult.fail("", (String) checkMap.get(Constants.MSG));
        }
        scheduleObj.setStartTime(scheduleParam.getStartTime());
        scheduleObj.setEndTime(scheduleParam.getEndTime());
        if (!org.quartz.CronExpression.isValidExpression(scheduleParam.getCrontab())) {
            log.error(scheduleParam.getCrontab() + " verify failure");
            putMsg(checkMap, Status.REQUEST_PARAMS_NOT_VALID_ERROR, scheduleParam.getCrontab());
            return BusinessResult.fail("", (String) checkMap.get(Constants.MSG));
        }
        scheduleObj.setCrontab(scheduleParam.getCrontab());
        scheduleObj.setWarningType(param.getWarningType());
        scheduleObj.setWarningGroupId(param.getWarningGroupId());
        scheduleObj.setFailureStrategy(param.getFailureStrategy());
        scheduleObj.setCreateTime(now);
        scheduleObj.setUpdateTime(now);
        scheduleObj.setUserId(param.getAccessUser().getId());
//        scheduleObj.setUserName(loginUser.getUserName());
        scheduleObj.setReleaseState(ReleaseState.OFFLINE);
        scheduleObj.setProcessInstancePriority(param.getProcessInstancePriority());
        scheduleObj.setWorkerGroup(param.getWorkerGroup());
        scheduleMapper.insert(scheduleObj);

        CreateSchedulerResult result = new CreateSchedulerResult();
        result.setSchedulerId(scheduleObj.getId());
        return BusinessResult.success(result);
    }
}
