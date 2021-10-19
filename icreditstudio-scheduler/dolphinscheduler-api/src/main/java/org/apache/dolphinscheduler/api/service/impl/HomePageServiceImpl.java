package org.apache.dolphinscheduler.api.service.impl;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
//import com.jinninghui.datasphere.icreditstudio.framework.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dolphinscheduler.api.request.SchedulerHomepageRequest;
import org.apache.dolphinscheduler.api.service.*;
import org.apache.dolphinscheduler.api.service.result.*;
import org.apache.dolphinscheduler.common.enums.ExecutionStatus;
import org.apache.dolphinscheduler.common.enums.TaskStatus;
import org.apache.dolphinscheduler.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xujie
 * @description homepageService
 * @create 2021-10-08 15:13
 **/
@Slf4j
@Service
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private ProcessDefinitionService processDefinitionService;
    @Autowired
    private TaskInstanceService taskInstanceService;

    static List<TaskSituationResult> situationZeroList = new ArrayList<>();

    static {
        situationZeroList.add(new TaskSituationResult(TaskStatus.SUCCESS.getDescp(), 0L, 0.00));
        situationZeroList.add(new TaskSituationResult(TaskStatus.FAILURE.getDescp(), 0L, 0.00));
        situationZeroList.add(new TaskSituationResult(TaskStatus.RUNNING_EXECUTION.getDescp(), 0L, 0.00));
        situationZeroList.add(new TaskSituationResult(TaskStatus.WAITING_THREAD.getDescp(), 0L, 0.00));
    }


    @Override
    public BusinessResult<TaskRoughResult> rough(SchedulerHomepageRequest request) {
        TaskRoughResult taskRoughResult = new TaskRoughResult();
        //前三天0点
        Date startTime = DateUtils.getStartOfDay(DateUtils.getSomeDay(new Date(), -3));
        //前一天24点
        Date endTime = DateUtils.getEndOfDay(DateUtils.getSomeDay(new Date(), -1));
        //总调度任务数
        Long count = taskInstanceService.countByWorkspaceIdAndTime(request.getWorkspaceId(), startTime, endTime, new int[]{});
        taskRoughResult.setTaskCount(count);
        //执行失败实例
        Long failCount = taskInstanceService.countByWorkspaceIdAndTime(request.getWorkspaceId(), startTime, endTime, new int[]{ExecutionStatus.FAILURE.getCode()});
        taskRoughResult.setFailCount(failCount);
        return BusinessResult.success(taskRoughResult);
    }

    @Override
    public BusinessResult<List<TaskSituationResult>> situation(String workspaceId) {
        Date date = new Date();
        Date startTime = DateUtils.getStartOfDay(date);
        Date endTime = DateUtils.getEndOfDay((date));
        Long success = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.SUCCESS.getCode()});
        Long fail = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.FAILURE.getCode()});
        Long running = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.RUNNING_EXECUTION.getCode()});
        Long waiting = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.WAITTING_THREAD.getCode(),
                ExecutionStatus.WAITTING_DEPEND.getCode()});
        List<TaskSituationResult> taskSituationResultList = getTaskSituationList(success, fail, running, waiting);
        return BusinessResult.success(taskSituationResultList);
    }

    @Override
    public BusinessResult<List<TaskCountResult>> taskCount(SchedulerHomepageRequest request) {
        return BusinessResult.success(taskInstanceService.countByDay(request));
    }

    @Override
    public BusinessResult<List<RuntimeRankResult>> runtimeRank(SchedulerHomepageRequest request) {
        //TODO:runtimeRankList改为每日定时获取
        List<RuntimeRankResult> runtimeRankResultList = new ArrayList<>();
        Date date = new Date();
        List<Map<String, Object>> definitionList = processDefinitionService.selectByWorkspaceIdAndTime(request.getWorkspaceId(),
                DateUtils.getStartOfDay(DateUtils.getSomeDay(new Date(), -1)), DateUtils.getEndOfDay(date));
        for (Map<String, Object> m : definitionList) {
            Long code = (Long) m.get("code");
            Double runtime = taskInstanceService.runtimeTotalByDefinition(code, new int[]{});
            runtimeRankResultList.add(new RuntimeRankResult((Integer)m.get("id"), (String)m.get("name"), runtime, (Integer)m.get("scheduleType")));
        }
        runtimeRankResultList.sort(Comparator.comparing(RuntimeRankResult::getSpeedTime).reversed());

        /*int startIndex = (request.getPageNum() - 1) * request.getPageSize();
        int endIndex = startIndex +  request.getPageSize();
        BusinessPageResult businessPageResult = new BusinessPageResult(runtimeRankResultList.size(), request.getPageNum(), request.getPageSize(), runtimeRankResultList.subList(startIndex, endIndex));*/
        return BusinessResult.success(runtimeRankResultList);
    }

    @Override
    public BusinessResult<List<RunErrorRankResult>> runErrorRank(SchedulerHomepageRequest request) {
        //TODO:runErrorRankList改为每日定时获取
        List<RunErrorRankResult> runErrorRankList = new ArrayList<>();
        Date date = new Date();
        List<Map<String, Object>> definitionList = processDefinitionService.selectByWorkspaceIdAndTime(request.getWorkspaceId(),
                DateUtils.getStartOfDay(DateUtils.getSomeMonth(date, -1)), DateUtils.getEndOfDay(DateUtils.getSomeDay(date, -1)));
        for (Map<String, Object> m : definitionList) {
            Long code = (Long) m.get("code");
            Long errorNum = taskInstanceService.getCountByByDefinitionAndStates(code, new int[]{ExecutionStatus.FAILURE.getCode()});
            runErrorRankList.add(new RunErrorRankResult((Integer)m.get("id"), (String)m.get("name"), errorNum, (Integer)m.get("scheduleType")));
        }
        runErrorRankList.sort(Comparator.comparing(RunErrorRankResult::getErrorNum).reversed());

        return BusinessResult.success(runErrorRankList.size() < 6 ? runErrorRankList : runErrorRankList.subList(0, 6));
    }

    private List<TaskSituationResult> getTaskSituationList(Long...args) {
        Long count = 0L;
        for (Long arg : args) {
            count += arg;
        }
        if (count == 0){
            return situationZeroList;
        }
        List<TaskSituationResult> list = new ArrayList<>();
        list.add(new TaskSituationResult(TaskStatus.SUCCESS.getDescp(), args[0], (double)args[0] / count));
        list.add(new TaskSituationResult(TaskStatus.FAILURE.getDescp(), args[1], (double)args[1] / count));
        list.add(new TaskSituationResult(TaskStatus.RUNNING_EXECUTION.getDescp(), args[2], (double)args[2] / count));
        list.add(new TaskSituationResult(TaskStatus.WAITING_THREAD.getDescp(), args[3], (double)args[3] / count));
        return list;
    }
}
