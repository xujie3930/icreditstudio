package org.apache.dolphinscheduler.api.service.impl;

import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dolphinscheduler.api.dto.ScheduleParam;
import org.apache.dolphinscheduler.api.exceptions.ServiceException;
import org.apache.dolphinscheduler.api.param.CreateSchedulerParam;
import org.apache.dolphinscheduler.api.service.PlatformSchedulerService;
import org.apache.dolphinscheduler.api.service.result.CreateSchedulerResult;
import org.apache.dolphinscheduler.common.enums.ReleaseState;
import org.apache.dolphinscheduler.common.utils.DateUtils;
import org.apache.dolphinscheduler.dao.entity.ProcessDefinition;
import org.apache.dolphinscheduler.dao.entity.Schedule;
import org.apache.dolphinscheduler.dao.mapper.ProcessDefinitionMapper;
import org.apache.dolphinscheduler.dao.mapper.ScheduleMapper;
import org.apache.dolphinscheduler.service.quartz.QuartzExecutors;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author Peng
 */
@Slf4j
@Service
public class PlatformSchedulerServiceImpl extends BaseServiceImpl implements PlatformSchedulerService {
    @Resource
    private ScheduleMapper scheduleMapper;
    @Resource
    private ProcessDefinitionMapper processDefinitionMapper;

    @Override
    public CreateSchedulerResult createSchedule(CreateSchedulerParam param) {
        Date now = new Date();
        // check work flow define release state
        ProcessDefinition processDefinition = processDefinitionMapper.selectById(param.getProcessDefineId());

        //检查流程定义
        checkProcessDefinition(processDefinition, param.getProcessDefineId());

        Schedule scheduleObj = new Schedule();
        scheduleObj.setProjectCode(param.getProjectCode());
        scheduleObj.setProcessDefinitionId(processDefinition.getId());
        scheduleObj.setProcessDefinitionName(processDefinition.getName());
        ScheduleParam scheduleParam = param.getSchedule();
        //校验ScheduleParam
        checkScheduleParam(scheduleParam);

        scheduleObj.setStartTime(scheduleParam.getStartTime());
        scheduleObj.setEndTime(scheduleParam.getEndTime());
        scheduleObj.setCrontab(scheduleParam.getCrontab());
        scheduleObj.setWarningType(param.getWarningType());
        scheduleObj.setWarningGroupId(param.getWarningGroupId());
        scheduleObj.setFailureStrategy(param.getFailureStrategy());
        scheduleObj.setCreateTime(now);
        scheduleObj.setUpdateTime(now);
        scheduleObj.setUserId(param.getAccessUser().getId());
        scheduleObj.setUserName(param.getAccessUser().getTenantCode());
        scheduleObj.setReleaseState(ReleaseState.OFFLINE);
        scheduleObj.setProcessInstancePriority(param.getProcessInstancePriority());
        scheduleObj.setWorkerGroup(param.getWorkerGroup());
        scheduleMapper.insert(scheduleObj);

        return new CreateSchedulerResult(scheduleObj.getId());
    }

    @Override
    public void deleteSchedule(String projectCode, String scheduleId) {
        log.info("delete schedules of project id:{}, schedule id:{}", projectCode, scheduleId);

        String jobName = QuartzExecutors.buildJobName(scheduleId);
        String jobGroupName = QuartzExecutors.buildJobGroupName(projectCode);

        if (!QuartzExecutors.getInstance().deleteJob(jobName, jobGroupName)) {
            log.warn("set offline failure:projectId:{},scheduleId:{}", projectCode, scheduleId);
            throw new ServiceException("set offline failure");
        }
    }

    private void checkProcessDefinition(ProcessDefinition processDefinition, String processDefinitionId) {
        if (Objects.isNull(processDefinition)) {
            log.error(String.format("工作流定义%s不存在", processDefinitionId));
            throw new AppException("60000000");
        }
        /*if (processDefinition.getReleaseState() != ReleaseState.ONLINE) {
            log.error(String.format("工作流定义%s不是上线状态", processDefinitionId));
            throw new AppException("60000001");
        }*/
    }

    private void checkScheduleParam(ScheduleParam scheduleParam) {
        if (DateUtils.differSec(scheduleParam.getStartTime(), scheduleParam.getEndTime()) == 0) {
            log.warn("开始时间不能和结束时间一样");
            throw new AppException("60000002");
        }
        if (!org.quartz.CronExpression.isValidExpression(scheduleParam.getCrontab())) {
            log.error(scheduleParam.getCrontab() + " verify failure");
            throw new AppException("60000003");
        }
    }
}
