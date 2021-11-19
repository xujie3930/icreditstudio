package org.apache.dolphinscheduler.api.service.impl;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dolphinscheduler.api.enums.Status;
import org.apache.dolphinscheduler.api.param.ExecPlatformProcessDefinitionParam;
import org.apache.dolphinscheduler.api.service.MonitorService;
import org.apache.dolphinscheduler.api.service.PlatformExecutorService;
import org.apache.dolphinscheduler.api.service.SchedulerService;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.common.enums.*;
import org.apache.dolphinscheduler.common.model.Server;
import org.apache.dolphinscheduler.common.utils.CollectionUtils;
import org.apache.dolphinscheduler.common.utils.DateUtils;
import org.apache.dolphinscheduler.common.utils.JSONUtils;
import org.apache.dolphinscheduler.common.utils.StringUtils;
import org.apache.dolphinscheduler.dao.entity.Command;
import org.apache.dolphinscheduler.dao.entity.ProcessDefinition;
import org.apache.dolphinscheduler.dao.entity.Schedule;
import org.apache.dolphinscheduler.dao.mapper.ProcessDefinitionMapper;
import org.apache.dolphinscheduler.dao.mapper.ProcessInstanceMapper;
import org.apache.dolphinscheduler.dao.mapper.ScheduleMapper;
import org.apache.dolphinscheduler.dao.mapper.TaskInstanceMapper;
import org.apache.dolphinscheduler.service.process.ProcessService;
import org.apache.dolphinscheduler.service.quartz.cron.CronUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

import static org.apache.dolphinscheduler.common.Constants.*;

/**
 * @author Peng
 */
@Slf4j
@Service
public class PlatformExecutorServiceImpl extends BaseServiceImpl implements PlatformExecutorService {
    @Resource
    private ProcessDefinitionMapper processDefinitionMapper;
    @Resource
    private MonitorService monitorService;
    @Resource
    private ProcessService processService;
    @Resource
    private SchedulerService schedulerService;
    @Resource
    private ProcessInstanceMapper processInstanceMapper;
    @Resource
    private TaskInstanceMapper taskInstanceMapper;
    @Resource
    private ScheduleMapper scheduleMapper;

    @Override
    public BusinessResult<Boolean> execProcessInstance(ExecPlatformProcessDefinitionParam param) throws ParseException {
        if (param.getTimeout() == null) {
            param.setTimeout(Constants.MAX_TASK_TIMEOUT);
        }
        Map<String, Object> result = new HashMap<>(5);
        // timeout is invalid
        if (param.getTimeout() <= 0 || param.getTimeout() > MAX_TASK_TIMEOUT) {
            putMsg(result, Status.TASK_TIMEOUT_PARAMS_ERROR);
            return BusinessResult.fail("", (String) result.get(Constants.MSG));
        }

        // check process define release state
        ProcessDefinition processDefinition = processDefinitionMapper.selectById(param.getProcessDefinitionId());
        result = checkProcessDefinitionValid(processDefinition, param.getProcessDefinitionId());
        if (result.get(Constants.STATUS) != Status.SUCCESS) {
            return BusinessResult.fail("", (String) result.get(Constants.MSG));
        }
        // check master exists
        if (!checkMasterExists(result)) {
            return BusinessResult.fail("", (String) result.get(Constants.MSG));
        }
        /**
         * create command
         */
        this.createCommand(param.getCommandType(), param.getProcessDefinitionId(),
                param.getTaskDependType(), param.getFailureStrategy(), param.getStartNodeList(), param.getCronTime(), param.getWarningType(),
                param.getAccessUser().getId(), param.getWarningGroupId(), param.getRunMode(), param.getProcessInstancePriority(), param.getWorkerGroup());

        return BusinessResult.success(true);
    }

    @Override
    public BusinessResult<Boolean> execCycle(String processDefinitionId) {
        schedulerService.setScheduleState(processDefinitionId, ReleaseState.ONLINE);
        return BusinessResult.success(true);
    }

    @Override
    public Map<String, Object> checkProcessDefinitionValid(ProcessDefinition processDefinition, String processDefineId) {
        Map<String, Object> result = new HashMap<>(5);
        if (processDefinition == null) {
            // check process definition exists
            putMsg(result, Status.PROCESS_DEFINE_NOT_EXIST, processDefineId);
        } else if (processDefinition.getReleaseState() != ReleaseState.ONLINE) {
            // check process definition online
            putMsg(result, Status.PROCESS_DEFINE_NOT_RELEASE, processDefineId);
        } else {
            result.put(Constants.STATUS, Status.SUCCESS);
        }
        return result;
    }

    /**
     * check whether master exists
     *
     * @param result result
     * @return master exists return true , otherwise return false
     */
    private boolean checkMasterExists(Map<String, Object> result) {
        // check master server exists
        List<Server> masterServers = monitorService.getServerListFromZK(true);

        // no master
        if (masterServers.size() == 0) {
            putMsg(result, Status.MASTER_NOT_EXISTS);
            return false;
        }
        return true;
    }

    /**
     * create command
     *
     * @param commandType             commandType
     * @param processDefineId         processDefineId
     * @param nodeDep                 nodeDep
     * @param failureStrategy         failureStrategy
     * @param startNodeList           startNodeList
     * @param schedule                schedule
     * @param warningType             warningType
     * @param executorId              executorId
     * @param warningGroupId          warningGroupId
     * @param runMode                 runMode
     * @param processInstancePriority processInstancePriority
     * @param workerGroup             workerGroup
     * @return command id
     * @throws ParseException
     */
    private int createCommand(CommandType commandType, String processDefineId,
                              TaskDependType nodeDep, FailureStrategy failureStrategy,
                              String startNodeList, String schedule, WarningType warningType,
                              String executorId, String warningGroupId,
                              RunMode runMode, Priority processInstancePriority, String workerGroup) throws ParseException {

        /**
         * instantiate command schedule instance
         */
        Command command = new Command();

        Map<String, String> cmdParam = new HashMap<>();
        if (commandType == null) {
            command.setCommandType(CommandType.START_PROCESS);
        } else {
            command.setCommandType(commandType);
        }
        command.setProcessDefinitionId(processDefineId);
        if (nodeDep != null) {
            command.setTaskDependType(nodeDep);
        }
        if (failureStrategy != null) {
            command.setFailureStrategy(failureStrategy);
        }

        if (StringUtils.isNotEmpty(startNodeList)) {
            cmdParam.put(CMDPARAM_START_NODE_NAMES, startNodeList);
        }
        if (warningType != null) {
            command.setWarningType(warningType);
        }
        command.setCommandParam(JSONUtils.toJson(cmdParam));
        command.setExecutorId(executorId);
        command.setWarningGroupId(warningGroupId);
        command.setProcessInstancePriority(processInstancePriority);
        command.setWorkerGroup(workerGroup);

        Date start = null;
        Date end = null;
        if (StringUtils.isNotEmpty(schedule)) {
            String[] interval = schedule.split(",");
            if (interval.length == 2) {
                start = DateUtils.getScheduleDate(interval[0]);
                end = DateUtils.getScheduleDate(interval[1]);
            }
        }

        // determine whether to complement
        if (commandType == CommandType.COMPLEMENT_DATA) {
            runMode = (runMode == null) ? RunMode.RUN_MODE_SERIAL : runMode;
            if (null != start && null != end && !start.after(end)) {
                if (runMode == RunMode.RUN_MODE_SERIAL) {
                    cmdParam.put(CMDPARAM_COMPLEMENT_DATA_START_DATE, DateUtils.dateToString(start));
                    cmdParam.put(CMDPARAM_COMPLEMENT_DATA_END_DATE, DateUtils.dateToString(end));
                    command.setCommandParam(JSONUtils.toJson(cmdParam));
                    return processService.createCommand(command);
                } else if (runMode == RunMode.RUN_MODE_PARALLEL) {
                    List<Schedule> schedules = processService.queryReleaseSchedulerListByProcessDefinitionId(processDefineId);
                    List<Date> listDate = new LinkedList<>();
                    if (CollectionUtils.isNotEmpty(schedules)) {
                        for (Schedule item : schedules) {
                            listDate.addAll(CronUtils.getSelfFireDateList(start, end, item.getCrontab()));
                        }
                    }
                    if (CollectionUtils.isNotEmpty(listDate)) {
                        // loop by schedule date
                        for (Date date : listDate) {
                            cmdParam.put(CMDPARAM_COMPLEMENT_DATA_START_DATE, DateUtils.dateToString(date));
                            cmdParam.put(CMDPARAM_COMPLEMENT_DATA_END_DATE, DateUtils.dateToString(date));
                            command.setCommandParam(JSONUtils.toJson(cmdParam));
                            processService.createCommand(command);
                        }
                        return listDate.size();
                    } else {
                        // loop by day
                        int runCunt = 0;
                        while (!start.after(end)) {
                            runCunt += 1;
                            cmdParam.put(CMDPARAM_COMPLEMENT_DATA_START_DATE, DateUtils.dateToString(start));
                            cmdParam.put(CMDPARAM_COMPLEMENT_DATA_END_DATE, DateUtils.dateToString(start));
                            command.setCommandParam(JSONUtils.toJson(cmdParam));
                            processService.createCommand(command);
                            start = DateUtils.getSomeDay(start, 1);
                        }
                        return runCunt;
                    }
                }
            } else {
                log.error("there is not valid schedule date for the process definition: id:{},date:{}",
                        processDefineId, schedule);
            }
        } else {
            command.setCommandParam(JSONUtils.toJson(cmdParam));
            return processService.createCommand(command);
        }
        return 0;
    }

    private void manualExecSyncTask(ExecPlatformProcessDefinitionParam param) throws ParseException {
        this.createCommand(param.getCommandType(), param.getProcessDefinitionId(),
                param.getTaskDependType(), param.getFailureStrategy(), param.getStartNodeList(), param.getCronTime(), param.getWarningType(),
                "", param.getWarningGroupId(), param.getRunMode(), param.getProcessInstancePriority(), param.getWorkerGroup());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String execSyncTask(String processDefinitionId) {
        String execResult = "false";
        try {
            ExecPlatformProcessDefinitionParam param = new ExecPlatformProcessDefinitionParam();
            param.setWorkerGroup("default");
            param.setTimeout(86400);
            param.setProcessDefinitionId(processDefinitionId);
            manualExecSyncTask(param);
            //更新对应processDefinition表的updateTime
            processDefinitionMapper.updateTimeById(new Date(), processDefinitionId);
            execResult = "true";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return execResult;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String stopSyncTask(String processDefinitionId) {
        /*processDefinitionMapper.updateStatusById(processDefinitionId, ReleaseState.OFFLINE.getCode());//定义下线
        schedulerService.setScheduleState(processDefinitionId, ReleaseState.OFFLINE);*/
        ProcessDefinition processDefinition = processDefinitionMapper.selectById(processDefinitionId);
        processDefinition.setReleaseState(ReleaseState.OFFLINE);
        processDefinitionMapper.updateById(processDefinition);
        List<Schedule> scheduleList = scheduleMapper.selectAllByProcessDefineArray(
                new String[]{processDefinition.getId()}
        );

        for (Schedule schedule : scheduleList) {
            // set status
            schedule.setReleaseState(ReleaseState.OFFLINE);
            scheduleMapper.updateById(schedule);
            schedulerService.deleteSchedule("icredit", schedule.getId());
        }
        //更新对应processDefinition表的updateTime
        processDefinitionMapper.updateTimeById(new Date(), processDefinitionId);
        return "true";
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String deleteSyncTask(String processDefinitionId) {
        String result = processInstanceMapper.isRunningForSyncTask(processDefinitionId);
        if (StringUtils.isNotEmpty(result)) {//流程正在执行，不能删除
            return "1";
        }
        taskInstanceMapper.deleteByProcessDefinitionId(processDefinitionId);
        processInstanceMapper.deleteByProcessDefinitionId(processDefinitionId);
        processDefinitionMapper.deleteById(processDefinitionId);
        schedulerService.deleteByProcessDefinitionId(processDefinitionId);
        return "0";
    }

    @Override
    public String enableSyncTask(String processDefinitionId) {
        processDefinitionMapper.updateStatusById(processDefinitionId, ReleaseState.ONLINE.getCode());
        schedulerService.setScheduleState(processDefinitionId, ReleaseState.ONLINE);
        //更新对应processDefinition表的updateTime
        processDefinitionMapper.updateTimeById(new Date(), processDefinitionId);
        return "true";
    }

    @Override
    public String ceaseSyncTask(String processDefinitionId) {
        schedulerService.setScheduleState(processDefinitionId, ReleaseState.OFFLINE);
        //更新对应processDefinition表的updateTime
        processDefinitionMapper.updateTimeById(new Date(), processDefinitionId);
        return "true";
    }
}
