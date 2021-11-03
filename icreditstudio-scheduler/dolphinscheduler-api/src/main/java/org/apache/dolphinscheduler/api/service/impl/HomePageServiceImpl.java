package org.apache.dolphinscheduler.api.service.impl;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.utils.CollectionUtils;
import com.jinninghui.datasphere.icreditstudio.framework.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dolphinscheduler.api.request.SchedulerHomepageRequest;
import org.apache.dolphinscheduler.api.service.HomePageService;
import org.apache.dolphinscheduler.api.service.ProcessDefinitionService;
import org.apache.dolphinscheduler.api.service.TaskInstanceService;
import org.apache.dolphinscheduler.api.service.result.*;
import org.apache.dolphinscheduler.common.enums.ExecutionStatus;
import org.apache.dolphinscheduler.common.enums.TaskStatus;
import org.apache.dolphinscheduler.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
    //TODO:这里暂且用redisTemplate构建RedisUtils,存在ABA和一致性的问题，后续再完善redis工具类
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @PostConstruct
    public void init() {
        RedisUtils.setRedisTemplate(redisTemplate);
    }


    private static final String PREROUGH= "rough";
    private static final String PRESITUATION= "todaySituation";
    private static final String PRETASKCOUNT= "taskCount";
    private static final String PRERUNTIMERANK= "runtimeRank";
    private static final String PRERUNERRORRANK= "runErrorRank";

    private static final long FIVE_MINUTE_TIME = 5 * 60L;
    private static final long ONE_DAY_TIME = 24 * 60 * 60L;

    static List<TaskSituationResult> situationZeroList = new ArrayList<>();

    static {
        situationZeroList.add(new TaskSituationResult(TaskStatus.SUCCESS.getDescp(), 0L, 0.00));
        situationZeroList.add(new TaskSituationResult(TaskStatus.FAILURE.getDescp(), 0L, 0.00));
        situationZeroList.add(new TaskSituationResult(TaskStatus.RUNNING_EXECUTION.getDescp(), 0L, 0.00));
        situationZeroList.add(new TaskSituationResult(TaskStatus.WAITING_THREAD.getDescp(), 0L, 0.00));
    }


    @Override
    public BusinessResult<TaskRoughResult> rough(SchedulerHomepageRequest request) {
        TaskRoughResult result = (TaskRoughResult) RedisUtils.get(PREROUGH + request.getWorkspaceId());
        if (null != result){
            return BusinessResult.success(result);
        }
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
        //新增数据量条数
        Long newlyLine = taskInstanceService.totalRecordsByWorkspaceIdAndTime(request.getWorkspaceId(), startTime, endTime);
        taskRoughResult.setNewlyLine(newlyLine);
        //新增数据量
        Long newlyDataSize = taskInstanceService.totalBytesByWorkspaceIdAndTime(request.getWorkspaceId(), startTime, endTime);
        taskRoughResult.setNewlyDataSize((double) newlyDataSize / 1024);
        RedisUtils.set(PREROUGH + request.getWorkspaceId(), taskRoughResult, ONE_DAY_TIME);
        return BusinessResult.success(taskRoughResult);
    }

    @Override
    public BusinessResult<List<TaskSituationResult>> situation(String workspaceId) {
        List<TaskSituationResult> list = (List<TaskSituationResult>) RedisUtils.get(PRESITUATION + workspaceId);
        if (!CollectionUtils.isEmpty(list)){
            return BusinessResult.success(list);
        }
        Date date = new Date();
        Date startTime = DateUtils.getStartOfDay(date);
        Date endTime = DateUtils.getEndOfDay((date));
        Long success = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.SUCCESS.getCode()});
        Long fail = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.FAILURE.getCode()});
        Long running = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.RUNNING_EXECUTION.getCode()});
        Long waiting = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.WAITTING_THREAD.getCode(),
                ExecutionStatus.WAITTING_DEPEND.getCode()});
        List<TaskSituationResult> taskSituationResultList = getTaskSituationList(success, fail, running, waiting);
        RedisUtils.set(PRESITUATION + workspaceId,taskSituationResultList, FIVE_MINUTE_TIME);
        return BusinessResult.success(taskSituationResultList);
    }

    @Override
    public BusinessResult<List<TaskCountResult>> taskCount(SchedulerHomepageRequest request) {
        List<TaskCountResult> list = (List<TaskCountResult>) RedisUtils.get(PRETASKCOUNT + request.getWorkspaceId());
        if (!CollectionUtils.isEmpty(list)){
            return BusinessResult.success(list);
        }
        List<TaskCountResult> resultList = taskInstanceService.countByDay(request);
        RedisUtils.set(PRETASKCOUNT + request.getWorkspaceId(), resultList, ONE_DAY_TIME);
        return BusinessResult.success(resultList);
    }

    @Override
    public BusinessResult<List<RuntimeRankResult>> runtimeRank(SchedulerHomepageRequest request) {
        List<RuntimeRankResult> list = (List<RuntimeRankResult>) RedisUtils.get(PRERUNTIMERANK + request.getWorkspaceId());
        if (!CollectionUtils.isEmpty(list)){
            return BusinessResult.success(list);
        }
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

        RedisUtils.set(PRERUNTIMERANK + request.getWorkspaceId(), runtimeRankResultList, ONE_DAY_TIME);
        return BusinessResult.success(runtimeRankResultList);
    }

    @Override
    public BusinessResult<List<RunErrorRankResult>> runErrorRank(SchedulerHomepageRequest request) {
        List<RunErrorRankResult> list = (List<RunErrorRankResult>) RedisUtils.get(PRERUNERRORRANK + request.getWorkspaceId());
        if (!CollectionUtils.isEmpty(list)){
            return BusinessResult.success(list);
        }
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
        runErrorRankList = runErrorRankList.size() < 6 ? runErrorRankList : runErrorRankList.subList(0, 6);

        RedisUtils.set(PRERUNERRORRANK + request.getWorkspaceId(), runErrorRankList, ONE_DAY_TIME);
        return BusinessResult.success(runErrorRankList);
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
