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

package org.apache.dolphinscheduler.api.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dolphinscheduler.api.dto.ScheduleParam;
import org.apache.dolphinscheduler.api.enums.Status;
import org.apache.dolphinscheduler.api.exceptions.ServiceException;
import org.apache.dolphinscheduler.api.service.ExecutorService;
import org.apache.dolphinscheduler.api.service.MonitorService;
import org.apache.dolphinscheduler.api.service.SchedulerService;
import org.apache.dolphinscheduler.api.utils.PageInfo;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.common.enums.FailureStrategy;
import org.apache.dolphinscheduler.common.enums.Priority;
import org.apache.dolphinscheduler.common.enums.ReleaseState;
import org.apache.dolphinscheduler.common.enums.WarningType;
import org.apache.dolphinscheduler.common.model.Server;
import org.apache.dolphinscheduler.common.utils.DateUtils;
import org.apache.dolphinscheduler.common.utils.JSONUtils;
import org.apache.dolphinscheduler.common.utils.StringUtils;
import org.apache.dolphinscheduler.dao.entity.ProcessDefinition;
import org.apache.dolphinscheduler.dao.entity.Schedule;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.dao.mapper.ProcessDefinitionMapper;
import org.apache.dolphinscheduler.dao.mapper.ScheduleMapper;
import org.apache.dolphinscheduler.service.process.ProcessService;
import org.apache.dolphinscheduler.service.quartz.ProcessScheduleJob;
import org.apache.dolphinscheduler.service.quartz.QuartzExecutors;
import org.apache.dolphinscheduler.service.quartz.cron.CronUtils;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * scheduler service impl
 */
@Service
public class SchedulerServiceImpl extends BaseServiceImpl implements SchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private MonitorService monitorService;

    @Autowired
    private ProcessService processService;

    @Resource
    private ScheduleMapper scheduleMapper;

    @Resource
    private ProcessDefinitionMapper processDefinitionMapper;

    /**
     * TODO 已调整
     * save schedule
     *
     * @param loginUser               login user
     * @param projectCode             project code
     * @param processDefineId         process definition id
     * @param schedule                scheduler
     * @param warningType             warning type
     * @param warningGroupId          warning group id
     * @param failureStrategy         failure strategy
     * @param processInstancePriority process instance priority
     * @param workerGroup             worker group
     * @return create result code
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Map<String, Object> insertSchedule(User loginUser, String projectCode,
                                              String processDefineId,
                                              String schedule,
                                              WarningType warningType,
                                              String warningGroupId,
                                              FailureStrategy failureStrategy,
                                              String receivers,
                                              String receiversCc,
                                              Priority processInstancePriority,
                                              String workerGroup) throws IOException {

        Map<String, Object> result = new HashMap<String, Object>(5);

        // check work flow define release state
        ProcessDefinition processDefinition = processService.findProcessDefineById(processDefineId);
        result = executorService.checkProcessDefinitionValid(processDefinition, processDefineId);
        if (result.get(Constants.STATUS) != Status.SUCCESS) {
            return result;
        }

        Schedule scheduleObj = new Schedule();
        Date now = new Date();

        scheduleObj.setProjectCode(projectCode);
        scheduleObj.setProcessDefinitionId(processDefinition.getId());
        scheduleObj.setProcessDefinitionName(processDefinition.getName());

        ScheduleParam scheduleParam = JSONUtils.parseObject(schedule, ScheduleParam.class);
        if (DateUtils.differSec(scheduleParam.getStartTime(), scheduleParam.getEndTime()) == 0) {
            logger.warn("The start time must not be the same as the end");
            putMsg(result, Status.SCHEDULE_START_TIME_END_TIME_SAME);
            return result;
        }
        scheduleObj.setStartTime(scheduleParam.getStartTime());
        scheduleObj.setEndTime(scheduleParam.getEndTime());
        if (!org.quartz.CronExpression.isValidExpression(scheduleParam.getCrontab())) {
            logger.error(scheduleParam.getCrontab() + " verify failure");

            putMsg(result, Status.REQUEST_PARAMS_NOT_VALID_ERROR, scheduleParam.getCrontab());
            return result;
        }
        scheduleObj.setCrontab(scheduleParam.getCrontab());
        scheduleObj.setWarningType(warningType);
        scheduleObj.setWarningGroupId(warningGroupId);
        scheduleObj.setFailureStrategy(failureStrategy);
        scheduleObj.setCreateTime(now);
        scheduleObj.setUpdateTime(now);
        scheduleObj.setUserId(loginUser.getId());
        scheduleObj.setUserName(loginUser.getTenantCode());
        scheduleObj.setReleaseState(ReleaseState.OFFLINE);
        scheduleObj.setProcessInstancePriority(processInstancePriority);
        scheduleObj.setWorkerGroup(workerGroup);
        scheduleMapper.insert(scheduleObj);

        /**
         * updateProcessInstance receivers and cc by process definition id
         */
        processDefinition.setReceivers(receivers);
        processDefinition.setReceiversCc(receiversCc);
        processDefinitionMapper.updateById(processDefinition);
        putMsg(result, Status.SUCCESS);

        result.put("scheduleId", scheduleObj.getId());
        return result;
    }

    /**
     * updateProcessInstance schedule
     *
     * @param loginUser               login user
     * @param projectName             project name
     * @param id                      scheduler id
     * @param scheduleExpression      scheduler
     * @param warningType             warning type
     * @param warningGroupId          warning group id
     * @param failureStrategy         failure strategy
     * @param workerGroup             worker group
     * @param processInstancePriority process instance priority
     * @param scheduleStatus          schedule status
     * @return update result code
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Map<String, Object> updateSchedule(User loginUser,
                                              String projectName,
                                              String id,
                                              String scheduleExpression,
                                              WarningType warningType,
                                              String warningGroupId,
                                              FailureStrategy failureStrategy,
                                              String receivers,
                                              String receiversCc,
                                              ReleaseState scheduleStatus,
                                              Priority processInstancePriority,
                                              String workerGroup) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>(5);

        // check schedule exists
        Schedule schedule = scheduleMapper.selectById(id);

        if (schedule == null) {
            putMsg(result, Status.SCHEDULE_CRON_NOT_EXISTS, id);
            return result;
        }

        ProcessDefinition processDefinition = processService.findProcessDefineById(schedule.getProcessDefinitionId());
        if (processDefinition == null) {
            putMsg(result, Status.PROCESS_DEFINE_NOT_EXIST, schedule.getProcessDefinitionId());
            return result;
        }

        /**
         * scheduling on-line status forbid modification
         */
        if (checkValid(result, schedule.getReleaseState() == ReleaseState.ONLINE, Status.SCHEDULE_CRON_ONLINE_FORBID_UPDATE)) {
            return result;
        }

        Date now = new Date();

        // updateProcessInstance param
        if (StringUtils.isNotEmpty(scheduleExpression)) {
            ScheduleParam scheduleParam = JSONUtils.parseObject(scheduleExpression, ScheduleParam.class);
            if (DateUtils.differSec(scheduleParam.getStartTime(), scheduleParam.getEndTime()) == 0) {
                logger.warn("The start time must not be the same as the end");
                putMsg(result, Status.SCHEDULE_START_TIME_END_TIME_SAME);
                return result;
            }
            schedule.setStartTime(scheduleParam.getStartTime());
            schedule.setEndTime(scheduleParam.getEndTime());
            if (!org.quartz.CronExpression.isValidExpression(scheduleParam.getCrontab())) {
                putMsg(result, Status.SCHEDULE_CRON_CHECK_FAILED, scheduleParam.getCrontab());
                return result;
            }
            schedule.setCrontab(scheduleParam.getCrontab());
        }

        if (warningType != null) {
            schedule.setWarningType(warningType);
        }

        schedule.setWarningGroupId(warningGroupId);

        if (failureStrategy != null) {
            schedule.setFailureStrategy(failureStrategy);
        }

        if (scheduleStatus != null) {
            schedule.setReleaseState(scheduleStatus);
        }
        schedule.setWorkerGroup(workerGroup);
        schedule.setUpdateTime(now);
        schedule.setProcessInstancePriority(processInstancePriority);
        scheduleMapper.updateById(schedule);

        /**
         * updateProcessInstance recipients and cc by process definition ID
         */
        processDefinition.setReceivers(receivers);
        processDefinition.setReceiversCc(receiversCc);
        processDefinitionMapper.updateById(processDefinition);

        putMsg(result, Status.SUCCESS);
        return result;
    }


    /**
     * set schedule online or offline
     *
     * @param definitionId   processDefinition id
     * @param scheduleStatus schedule status
     * @return publish result code
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Map<String, Object> setScheduleState(String definitionId,
                                                ReleaseState scheduleStatus) {
        Map<String, Object> result = new HashMap<>(8);

        ProcessDefinition definition = processDefinitionMapper.selectById(definitionId);
        String projectCode = definition.getProjectCode();
        Schedule scheduleObj = null;
        if(ReleaseState.ONLINE == scheduleStatus){//上线操作
            scheduleObj = scheduleMapper.getScheduleByDefinitionIdAndStatus(definitionId, ReleaseState.OFFLINE.getCode());
        }else{//下线操作
            scheduleObj = scheduleMapper.getScheduleByDefinitionIdAndStatus(definitionId, ReleaseState.ONLINE.getCode());
        }
        // check schedule exists

        if (scheduleObj == null) {
            putMsg(result, Status.DEFINITION_SCHEDULE_NOT_EXISTS, definitionId);
            return result;
        }
        // check schedule release state
        if (scheduleObj.getReleaseState() == scheduleStatus) {
            logger.info("schedule release is already {},needn't to change schedule id: {} from {} to {}",
                    scheduleObj.getReleaseState(), scheduleObj.getId(), scheduleObj.getReleaseState(), scheduleStatus);
            putMsg(result, Status.SCHEDULE_CRON_REALEASE_NEED_NOT_CHANGE, scheduleStatus);
            return result;
        }
        ProcessDefinition processDefinition = processService.findProcessDefineById(scheduleObj.getProcessDefinitionId());
        if (processDefinition == null) {
            putMsg(result, Status.PROCESS_DEFINE_NOT_EXIST, scheduleObj.getProcessDefinitionId());
            return result;
        }

        if (scheduleStatus == ReleaseState.ONLINE) {
            // check process definition release state
            if (processDefinition.getReleaseState() != ReleaseState.ONLINE) {
                logger.info("not release process definition id: {} , name : {}",
                        processDefinition.getId(), processDefinition.getName());
                putMsg(result, Status.PROCESS_DEFINE_NOT_RELEASE, definition.getName());
                return result;
            }
        }

        // check master server exists
        List<Server> masterServers = monitorService.getServerListFromZK(true);

        if (masterServers.size() == 0) {
            putMsg(result, Status.MASTER_NOT_EXISTS);
            return result;
        }

        // set status
        scheduleObj.setReleaseState(scheduleStatus);

        scheduleMapper.updateById(scheduleObj);

        try {
            switch (scheduleStatus) {
                case ONLINE: {
                    logger.info("Call master client set schedule online, project id: {}, flow id: {},host: {}", projectCode, processDefinition.getId(), masterServers);
                    setSchedule(projectCode, scheduleObj.getId());
                    break;
                }
                case OFFLINE: {
                    logger.info("Call master client set schedule offline, project id: {}, flow id: {},host: {}", projectCode, processDefinition.getId(), masterServers);
                    deleteSchedule(projectCode, scheduleObj.getId());
                    break;
                }
                default: {
                    putMsg(result, Status.SCHEDULE_STATUS_UNKNOWN, scheduleStatus.toString());
                    return result;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            result.put(Constants.MSG, scheduleStatus == ReleaseState.ONLINE ? "set online failure" : "set offline failure");
            e.printStackTrace();
            throw new RuntimeException(result.get(Constants.MSG).toString());
        }

        putMsg(result, Status.SUCCESS);
        return result;
    }

    /**
     * query schedule
     *
     * @param loginUser       login user
     * @param projectName     project name
     * @param processDefineId process definition id
     * @param pageNo          page number
     * @param pageSize        page size
     * @param searchVal       search value
     * @return schedule list page
     */
    @Override
    public Map<String, Object> querySchedule(User loginUser, String projectName, String processDefineId, String searchVal, Integer pageNo, Integer pageSize) {

        HashMap<String, Object> result = new HashMap<>();

        ProcessDefinition processDefinition = processService.findProcessDefineById(processDefineId);
        if (processDefinition == null) {
            putMsg(result, Status.PROCESS_DEFINE_NOT_EXIST, processDefineId);
            return result;
        }
        Page<Schedule> page = new Page(pageNo, pageSize);
        IPage<Schedule> scheduleIPage = scheduleMapper.queryByProcessDefineIdPaging(
                page, processDefineId, searchVal
        );


        PageInfo pageInfo = new PageInfo<Schedule>(pageNo, pageSize);
        pageInfo.setTotalCount((int) scheduleIPage.getTotal());
        pageInfo.setLists(scheduleIPage.getRecords());
        result.put(Constants.DATA_LIST, pageInfo);
        putMsg(result, Status.SUCCESS);

        return result;
    }

    /**
     * query schedule list
     *
     * @param loginUser   login user
     * @param projectName project name
     * @return schedule list
     */
    @Override
    public Map<String, Object> queryScheduleList(User loginUser, String projectName) {
        Map<String, Object> result = new HashMap<>(5);

        List<Schedule> schedules = scheduleMapper.querySchedulerListByProjectName(projectName);

        result.put(Constants.DATA_LIST, schedules);
        putMsg(result, Status.SUCCESS);

        return result;
    }

    public void setSchedule(String projectId, String scheduleId) {
        logger.info("set schedule, project id: {}, scheduleId: {}", projectId, scheduleId);


        Schedule schedule = processService.querySchedule(scheduleId);
        if (schedule == null) {
            logger.warn("process schedule info not exists");
            return;
        }

        Date startDate = schedule.getStartTime();
        Date endDate = schedule.getEndTime();

        String jobName = QuartzExecutors.buildJobName(scheduleId);
        String jobGroupName = QuartzExecutors.buildJobGroupName(projectId);

        Map<String, Object> dataMap = QuartzExecutors.buildDataMap(projectId, scheduleId, schedule);

        QuartzExecutors.getInstance().addJob(ProcessScheduleJob.class, jobName, jobGroupName, startDate, endDate,
                schedule.getCrontab(), dataMap);

    }

    /**
     * delete schedule
     *
     * @param projectCode project id
     * @param scheduleId  schedule id
     * @throws RuntimeException runtime exception
     */
    @Override
    public void deleteSchedule(String projectCode, String scheduleId) {
        logger.info("delete schedules of project id:{}, schedule id:{}", projectCode, scheduleId);

        String jobName = QuartzExecutors.buildJobName(scheduleId);
        String jobGroupName = QuartzExecutors.buildJobGroupName(projectCode);

        if (!QuartzExecutors.getInstance().deleteJob(jobName, jobGroupName)) {
            logger.warn("set offline failure:projectId:{},scheduleId:{}", projectCode, scheduleId);
            throw new ServiceException("set offline failure");
        }

    }

    /**
     * check valid
     *
     * @param result result
     * @param bool   bool
     * @param status status
     * @return check result code
     */
    private boolean checkValid(Map<String, Object> result, boolean bool, Status status) {
        // timeout is valid
        if (bool) {
            putMsg(result, status);
            return true;
        }
        return false;
    }

    /**
     * delete schedule by id
     *
     * @param loginUser   login user
     * @param projectCode project name
     * @param scheduleId  scheule id
     * @return delete result code
     */
    @Override
    public Map<String, Object> deleteScheduleById(User loginUser, String projectCode, String scheduleId) {
        Map<String, Object> result = new HashMap<>(5);

        Schedule schedule = scheduleMapper.selectById(scheduleId);

        if (schedule == null) {
            putMsg(result, Status.SCHEDULE_CRON_NOT_EXISTS, scheduleId);
            return result;
        }

        // check schedule is already online
        if (schedule.getReleaseState() == ReleaseState.ONLINE) {
            putMsg(result, Status.SCHEDULE_CRON_STATE_ONLINE, schedule.getId());
            return result;
        }


        int delete = scheduleMapper.deleteById(scheduleId);

        if (delete > 0) {
            putMsg(result, Status.SUCCESS);
        } else {
            putMsg(result, Status.DELETE_SCHEDULE_CRON_BY_ID_ERROR);
        }
        return result;
    }

    /**
     * preview schedule
     *
     * @param loginUser   login user
     * @param projectCode project code
     * @param schedule    schedule expression
     * @return the next five fire time
     */
    @Override
    public Map<String, Object> previewSchedule(User loginUser, String projectCode, String schedule) {
        Map<String, Object> result = new HashMap<>();
        CronExpression cronExpression;
        ScheduleParam scheduleParam = JSONUtils.parseObject(schedule, ScheduleParam.class);
        Date now = new Date();

        Date startTime = now.after(scheduleParam.getStartTime()) ? now : scheduleParam.getStartTime();
        Date endTime = scheduleParam.getEndTime();
        try {
            cronExpression = CronUtils.parse2CronExpression(scheduleParam.getCrontab());
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            putMsg(result, Status.PARSE_TO_CRON_EXPRESSION_ERROR);
            return result;
        }
        List<Date> selfFireDateList = CronUtils.getSelfFireDateList(startTime, endTime, cronExpression, Constants.PREVIEW_SCHEDULE_EXECUTE_COUNT);
        result.put(Constants.DATA_LIST, selfFireDateList.stream().map(DateUtils::dateToString));
        putMsg(result, Status.SUCCESS);
        return result;
    }

    @Override
    public void updateStatusByProcessDefinitionId(String processDefinitionId, int state){
        try {
            scheduleMapper.updateStatusByProcessDefinitionId(processDefinitionId, state);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByProcessDefinitionId(String processDefinitionId) {
        scheduleMapper.deleteByProcessDefinitionId(processDefinitionId);
    }
}
