package org.apache.dolphinscheduler.api.service.impl;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dolphinscheduler.api.enums.TaskExecStatusEnum;
import org.apache.dolphinscheduler.api.request.SchedulerHomepageRequest;
import org.apache.dolphinscheduler.api.service.HomePageService;
import org.apache.dolphinscheduler.api.service.StatisticsDefinitionService;
import org.apache.dolphinscheduler.api.service.StatisticsInstanceService;
import org.apache.dolphinscheduler.api.service.result.*;
import org.apache.dolphinscheduler.api.utils.DoubleUtils;
import org.apache.dolphinscheduler.common.enums.TaskStatus;
import org.apache.dolphinscheduler.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xujie
 * @description homepageService
 * @create 2021-10-08 15:13
 **/
@Slf4j
@Service
public class HomePageServiceImpl implements HomePageService {

    static final String[] units = new String[]{"B", "KB", "MB", "GB", "TB", "PB"};
    @Autowired
    private StatisticsInstanceService statisticsInstanceService;

    private static final String DEFAULT_WORKSPACEID = "0";
    @Autowired
    private StatisticsDefinitionService statisticsDefinitionService;
    static List<TaskSituationResult> situationZeroList = new ArrayList<>();

    static {
        situationZeroList.add(new TaskSituationResult(TaskStatus.SUCCESS.getDescp(), 0L, 0.00));
        situationZeroList.add(new TaskSituationResult(TaskStatus.FAILURE.getDescp(), 0L, 0.00));
        situationZeroList.add(new TaskSituationResult(TaskStatus.RUNNING_EXECUTION.getDescp(), 0L, 0.00));
        situationZeroList.add(new TaskSituationResult(TaskStatus.WAITING_THREAD.getDescp(), 0L, 0.00));
    }

    private static String getDataSizeUnit(Long size) {
        if (size == 0) {
            return units[0];
        }
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return units[digitGroups];
    }

    private static Double getDataSize(Long size) {
        if (size == 0) {
            return 0.00;
        }
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return DoubleUtils.formatDouble(size / Math.pow(1024, digitGroups));
    }

    @Override
    public BusinessResult<TaskRoughResult> rough(String userId, SchedulerHomepageRequest request) {
        if (!DEFAULT_WORKSPACEID.equals(request.getWorkspaceId())) {
            userId = "";
        }
        TaskRoughResult taskRoughResult = new TaskRoughResult();
        //前三天0点
        Date startTime = DateUtils.getStartOfDay(DateUtils.getSomeDay(new Date(), -3));
        //前一天24点
        Date endTime = DateUtils.getEndOfDay(DateUtils.getSomeDay(new Date(), -1));
        //总调度任务数
        Long count = statisticsInstanceService.countByWorkspaceIdAndTime(request.getWorkspaceId(), userId, startTime, endTime, new int[]{});
        taskRoughResult.setTaskCount(count);
        //执行失败实例
        Long failCount = statisticsInstanceService.countByWorkspaceIdAndTime(request.getWorkspaceId(), userId, startTime, endTime, new int[]{TaskExecStatusEnum.FAIL.ordinal()});
        taskRoughResult.setFailCount(failCount);
        //新增数据量条数
        Long newlyLine = statisticsInstanceService.totalRecordsByWorkspaceIdAndTime(request.getWorkspaceId(), userId, startTime, endTime);
        taskRoughResult.setNewlyLine(DoubleUtils.formatDouble(((double) newlyLine) / 10000));
        //新增数据量：最小单位为M，超过1024为G，一次类推最大为PB
        Long newlyDataSize = statisticsInstanceService.totalBytesByWorkspaceIdAndTime(request.getWorkspaceId(), userId, startTime, endTime);
        taskRoughResult.setNewlyDataSize(getDataSize(newlyDataSize));
        taskRoughResult.setUnit(getDataSizeUnit(newlyDataSize));
        return BusinessResult.success(taskRoughResult);
    }

    @Override
    public BusinessResult<List<TaskSituationResult>> situation(String userId, String workspaceId) {
        if (!DEFAULT_WORKSPACEID.equals(workspaceId)) {
            userId = "";
        }
        Date date = new Date();
        Date startTime = DateUtils.getStartOfDay(date);
        Date endTime = DateUtils.getEndOfDay((date));
        Long success = statisticsInstanceService.countByWorkspaceIdAndTime(workspaceId, userId, startTime, endTime, new int[]{TaskExecStatusEnum.SUCCESS.ordinal()});
        Long fail = statisticsInstanceService.countByWorkspaceIdAndTime(workspaceId, userId, startTime, endTime, new int[]{TaskExecStatusEnum.FAIL.ordinal()});
        Long running = statisticsInstanceService.countByWorkspaceIdAndTime(workspaceId, userId, startTime, null, new int[]{TaskExecStatusEnum.RUNNING.ordinal()});
        Long waiting = statisticsInstanceService.countByWorkspaceIdAndTime(workspaceId, userId, startTime, endTime, new int[]{TaskExecStatusEnum.WATTING.ordinal()});
        List<TaskSituationResult> taskSituationResultList = getTaskSituationList(success, fail, running, waiting);
        return BusinessResult.success(taskSituationResultList);
    }

    @Override
    public BusinessResult<List<TaskCountResult>> taskCount(String userId, SchedulerHomepageRequest request) {
        if (!DEFAULT_WORKSPACEID.equals(request.getWorkspaceId())) {
            userId = "";
        }
        List<TaskCountResult> resultList = statisticsInstanceService.countByDay(userId, request);
        return BusinessResult.success(resultList);
    }

    @Override
    public BusinessResult<List<RuntimeRankResult>> runtimeRank(String userId, SchedulerHomepageRequest request) {
        if (!DEFAULT_WORKSPACEID.equals(request.getWorkspaceId())) {
            userId = "";
        }
        List<RuntimeRankResult> runtimeRankResultList = new ArrayList<>();
        List<Map<String, Object>> maps = statisticsDefinitionService.runtimeTotalByDefinition(request.getWorkspaceId(), userId, DateUtils.getStartOfDay(DateUtils.getSomeDay(new Date(), -1)), DateUtils.getEndOfDay(DateUtils.getSomeDay(new Date(), -1)));
        for (Map<String, Object> m : maps) {
            runtimeRankResultList.add(new RuntimeRankResult((String) m.get("platformTaskId"), (String) m.get("name"), ((BigDecimal) m.get("speedTime")).doubleValue(), (Integer) m.get("scheduleType")));
        }
        runtimeRankResultList.sort(Comparator.comparing(RuntimeRankResult::getSpeedTime).reversed());

        return BusinessResult.success(runtimeRankResultList);
    }

    @Override
    public BusinessResult<List<RunErrorRankResult>> runErrorRank(String userId, SchedulerHomepageRequest request) {
        if (!DEFAULT_WORKSPACEID.equals(request.getWorkspaceId())) {
            userId = "";
        }
        List<RunErrorRankResult> runErrorRankList = new ArrayList<>();
        Date date = new Date();
        List<Map<String, Object>> maps = statisticsDefinitionService.errorTimeTotalByDefinition(request.getWorkspaceId(), userId, DateUtils.getStartOfDay(DateUtils.getSomeDay(date, -1)), DateUtils.getEndOfDay(DateUtils.getSomeDay(date, -1)));
        for (Map<String, Object> m : maps) {
            RunErrorRankResult result = new RunErrorRankResult(
                    (String) m.get("platformTaskId"), (String) m.get("name"), (Long) m.get("errorNum"), (Integer) m.get("scheduleType"));
            runErrorRankList.add(result);
        }
        //按照错误数量倒序排，且过滤掉错误数为0的数据
        runErrorRankList.sort(Comparator.comparing(RunErrorRankResult::getErrorNum).reversed());
        runErrorRankList = runErrorRankList.parallelStream().filter(runErrorRank -> runErrorRank.getErrorNum() != 0).sorted(Comparator.comparing(RunErrorRankResult::getErrorNum).reversed()).collect(Collectors.toList());

        return BusinessResult.success(runErrorRankList);
    }

    @Override
    public WorkBenchResult workbench(String userId, String id) {
        if (!DEFAULT_WORKSPACEID.equals(id)) {
            userId = "";
        }
        WorkBenchResult result = new WorkBenchResult();
        Long success = statisticsInstanceService.countByWorkspaceIdAndTime(id, userId, null, null, new int[]{TaskExecStatusEnum.SUCCESS.ordinal()});
        result.setSuccess(success);
        Long failure = statisticsInstanceService.countByWorkspaceIdAndTime(id, userId, null, null, new int[]{TaskExecStatusEnum.FAIL.ordinal()});
        result.setFailure(failure);
        Long running = statisticsInstanceService.countByWorkspaceIdAndTime(id, userId, null, null, new int[]{TaskExecStatusEnum.RUNNING.ordinal()});
        result.setRunning(running);
        Long notRun = statisticsInstanceService.countByWorkspaceIdAndTime(id, userId, null, null, new int[]{TaskExecStatusEnum.WATTING.ordinal()});
        result.setNotRun(notRun);
        return result;
    }

    private List<TaskSituationResult> getTaskSituationList(Long... args) {
        Long count = 0L;
        for (Long arg : args) {
            count += arg;
        }
        if (count == 0) {
            return situationZeroList;
        }
        List<TaskSituationResult> list = new ArrayList<>();
        list.add(new TaskSituationResult(TaskStatus.SUCCESS.getDescp(), args[0], DoubleUtils.formatDouble((double) args[0] / count)));
        list.add(new TaskSituationResult(TaskStatus.FAILURE.getDescp(), args[1], DoubleUtils.formatDouble((double) args[1] / count)));
        list.add(new TaskSituationResult(TaskStatus.RUNNING_EXECUTION.getDescp(), args[2], DoubleUtils.formatDouble((double) args[2] / count)));
        list.add(new TaskSituationResult(TaskStatus.WAITING_THREAD.getDescp(), args[3], DoubleUtils.formatDouble((double) args[3] / count)));
        return list;
    }
}
