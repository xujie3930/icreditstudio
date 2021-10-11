package org.apache.dolphinscheduler.api.service.impl;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.request.SchedulerHomepageRequest;
import org.apache.dolphinscheduler.api.service.*;
import org.apache.dolphinscheduler.api.service.result.*;
import org.apache.dolphinscheduler.common.enums.ExecutionStatus;
import org.apache.dolphinscheduler.common.enums.TaskStatus;
import org.apache.dolphinscheduler.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author xujie
 * @description homepageService
 * @create 2021-10-08 15:13
 **/
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private ProcessDefinitionService processDefinitionService;
    @Autowired
    private TaskDefinitionService taskDefinitionService;
    @Autowired
    private ProcessInstanceService processInstanceService;
    @Autowired
    private TaskInstanceService taskInstanceService;

    @Override
    public BusinessResult<TaskRough> rough(SchedulerHomepageRequest request) {
        TaskRough taskRough = new TaskRough();
        //前三天0点
        Date startTime = DateUtils.getStartOfDay(DateUtils.getSomeDay(new Date(), -3));
        //前一天24点
        Date endTime = DateUtils.getEndOfDay(DateUtils.getSomeDay(new Date(), -1));
        //总调度任务数
        Long count = taskInstanceService.countByWorkspaceIdAndTime(request.getWorkspaceId(), startTime, endTime, new int[]{});
        taskRough.setTaskCount(count);
        //执行失败实例
        Long failCount = taskInstanceService.countByWorkspaceIdAndTime(request.getWorkspaceId(), startTime, endTime, new int[]{ExecutionStatus.FAILURE.getCode()});
        taskRough.setFailCount(failCount);
        return BusinessResult.success(taskRough);
    }

    @Override
    public BusinessResult<List<TaskSituation>> situation(String workspaceId) {
        Date date = new Date();
        Date startTime = DateUtils.getStartOfDay(date);
        Date endTime = DateUtils.getStartOfDay(date);
        Long success = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.SUCCESS.getCode()});
        Long fail = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.FAILURE.getCode()});
        Long running = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.RUNNING_EXECUTION.getCode()});
        Long waiting = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.WAITTING_THREAD.getCode(),
                ExecutionStatus.WAITTING_DEPEND.getCode()});
        List<TaskSituation> taskSituationList = getTaskSituationList(success, fail, running, waiting);
        return BusinessResult.success(taskSituationList);
    }

    @Override
    public BusinessResult<List<TaskCount>> taskCount(SchedulerHomepageRequest request) {
        return BusinessResult.success(taskInstanceService.countByDay(request));
    }

    @Override
    public BusinessResult<BusinessPageResult> runtimeRank(SchedulerHomepageRequest request) {
        //TODO:runtimeRankList改为每日定时获取
        List<RuntimeRank> runtimeRankList = new ArrayList<>();
        Date date = new Date();
        List<Map<String, Object>> definitionList = processDefinitionService.selectByWorkspaceIdAndTime(request.getWorkspaceId(),
                DateUtils.getStartOfDay(date), DateUtils.getEndOfDay(date));
        for (Map<String, Object> m : definitionList) {
            String code = (String) m.get("code");
            Double runtime = taskInstanceService.runtimeTotalByDefinition(code, new int[]{});
            runtimeRankList.add(new RuntimeRank((Integer)m.get("id"), (String)m.get("name"), runtime, (Integer)m.get("scheduleType")));
        }
        runtimeRankList.sort(Comparator.comparing(RuntimeRank::getSpeedTime).reversed());

        int startIndex = (request.getPageNum() - 1) * request.getPageSize();
        int endIndex = startIndex +  request.getPageSize();
        BusinessPageResult businessPageResult = new BusinessPageResult(runtimeRankList.size(), request.getPageNum(), request.getPageSize(), runtimeRankList.subList(startIndex, endIndex));
        return BusinessResult.success(businessPageResult);
    }

    @Override
    public BusinessResult<List<RunErrorRank>> runErrorRank(SchedulerHomepageRequest request) {
        //TODO:runErrorRankList改为每日定时获取
        List<RunErrorRank> runErrorRankList = new ArrayList<>();
        Date date = new Date();
        List<Map<String, Object>> definitionList = processDefinitionService.selectByWorkspaceIdAndTime(request.getWorkspaceId(),
                DateUtils.getStartOfDay(DateUtils.getSomeMonth(date, -1)), DateUtils.getEndOfDay(DateUtils.getSomeDay(date, -1)));
        for (Map<String, Object> m : definitionList) {
            String code = (String) m.get("code");
            Long errorNum = taskInstanceService.getCountByByDefinitionAndStates(code, new int[]{ExecutionStatus.FAILURE.getCode()});
            runErrorRankList.add(new RunErrorRank((Integer)m.get("id"), (String)m.get("name"), errorNum, (Integer)m.get("scheduleType")));
        }
        runErrorRankList.sort(Comparator.comparing(RunErrorRank::getErrorNum).reversed());

        return BusinessResult.success(runErrorRankList.subList(0, 6));
    }

    private List<TaskSituation> getTaskSituationList(Long...args) {
        Long count = 0L;
        for (Long arg : args) {
            count += arg;
        }
        List<TaskSituation> list = new ArrayList<>();
        list.add(new TaskSituation(TaskStatus.SUCCESS.getDescp(), args[0], (double)args[0] / count));
        list.add(new TaskSituation(TaskStatus.FAILURE.getDescp(), args[1], (double)args[1] / count));
        list.add(new TaskSituation(TaskStatus.RUNNING_EXECUTION.getDescp(), args[2], (double)args[2] / count));
        list.add(new TaskSituation(TaskStatus.WAITING_THREAD.getDescp(), args[3], (double)args[3] / count));
        return list;
    }
}
