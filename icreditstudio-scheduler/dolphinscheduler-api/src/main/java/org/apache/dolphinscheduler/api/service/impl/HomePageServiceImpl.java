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
import org.apache.dolphinscheduler.api.utils.DoubleUtils;
import org.apache.dolphinscheduler.common.enums.ExecutionStatus;
import org.apache.dolphinscheduler.common.enums.TaskStatus;
import org.apache.dolphinscheduler.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    //TODO:这里暂且不采用缓存，正式发版时候补上
//    @Autowired
//    private RedisTemplate<Object, Object> redisTemplate;
//    @PostConstruct
//    public void init() {
//        RedisUtils.setRedisTemplate(redisTemplate);
//    }


    private static final String SCHEDULER= "scheduler-api";
    private static final String PREROUGH= SCHEDULER + "rough";
    private static final String PRESITUATION= SCHEDULER + "todaySituation";
    private static final String PRETASKCOUNT= SCHEDULER + "taskCount";
    private static final String PRERUNTIMERANK= SCHEDULER + "runtimeRank";
    private static final String PRERUNERRORRANK= SCHEDULER + "runErrorRank";
    private static final String WORKBENCH= SCHEDULER + "workbench";

    private static final long FIVE_MINUTE_TIME = 5 * 60L;
    private static final long ONE_HOUR_TIME = 60 * 60L;

    static List<TaskSituationResult> situationZeroList = new ArrayList<>();
    static final String[] units = new String[]{"B", "KB", "MB", "GB", "TB", "PB"};

    static {
        situationZeroList.add(new TaskSituationResult(TaskStatus.SUCCESS.getDescp(), 0L, 0.00));
        situationZeroList.add(new TaskSituationResult(TaskStatus.FAILURE.getDescp(), 0L, 0.00));
        situationZeroList.add(new TaskSituationResult(TaskStatus.RUNNING_EXECUTION.getDescp(), 0L, 0.00));
        situationZeroList.add(new TaskSituationResult(TaskStatus.WAITING_THREAD.getDescp(), 0L, 0.00));
    }


    @Override
    public BusinessResult<TaskRoughResult> rough(SchedulerHomepageRequest request) {
//        String redisKey = PREROUGH + request.getWorkspaceId();
//        TaskRoughResult result = (TaskRoughResult) RedisUtils.get(redisKey);
//        if (null != result){
//            return BusinessResult.success(result);
//        }
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
        taskRoughResult.setNewlyLine(DoubleUtils.formatDouble(((double)newlyLine) / 10000));
        //新增数据量：最小单位为M，超过1024为G，一次类推最大为PB
        Long newlyDataSize = taskInstanceService.totalBytesByWorkspaceIdAndTime(request.getWorkspaceId(), startTime, endTime);
        taskRoughResult.setNewlyDataSize(getDataSize(newlyDataSize));
        taskRoughResult.setUnit(getDataSizeUnit(newlyDataSize));
//        RedisUtils.set(redisKey, taskRoughResult, ONE_DAY_TIME);
        return BusinessResult.success(taskRoughResult);
    }

    private static String getDataSizeUnit(Long size) {
        if (size == 0){
            return units[0];
        }
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return units[digitGroups];
    }

    private static Double getDataSize(Long size) {
        if (size == 0){
            return 0.00;
        }
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return DoubleUtils.formatDouble(size / Math.pow(1024, digitGroups));
    }

    @Override
    public BusinessResult<List<TaskSituationResult>> situation(String workspaceId) {
//        String redisKey = PRESITUATION + workspaceId;
//        List<TaskSituationResult> list = (List<TaskSituationResult>) RedisUtils.get(redisKey);
//        if (!CollectionUtils.isEmpty(list)){
//            return BusinessResult.success(list);
//        }
        Date date = new Date();
        Date startTime = DateUtils.getStartOfDay(date);
        Date endTime = DateUtils.getEndOfDay((date));
        Long success = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.SUCCESS.getCode()});
        Long fail = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.FAILURE.getCode()});
        Long running = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.RUNNING_EXECUTION.getCode()});
        Long waiting = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.WAITTING_THREAD.getCode(),
                ExecutionStatus.WAITTING_DEPEND.getCode()});
        List<TaskSituationResult> taskSituationResultList = getTaskSituationList(success, fail, running, waiting);
//        RedisUtils.set(redisKey,taskSituationResultList, FIVE_MINUTE_TIME);
        return BusinessResult.success(taskSituationResultList);
    }

    @Override
    public BusinessResult<List<TaskCountResult>> taskCount(SchedulerHomepageRequest request) {
//        String redisKey = PRETASKCOUNT + request.getWorkspaceId();
//        List<TaskCountResult> list = (List<TaskCountResult>) RedisUtils.get(redisKey);
//        if (!CollectionUtils.isEmpty(list)){
//            return BusinessResult.success(list);
//        }
        List<TaskCountResult> resultList = taskInstanceService.countByDay(request);
//        RedisUtils.set(redisKey, resultList, ONE_DAY_TIME);
        return BusinessResult.success(resultList);
    }

    @Override
    public BusinessResult<List<RuntimeRankResult>> runtimeRank(SchedulerHomepageRequest request) {
//        String redisKey = PRERUNTIMERANK + request.getWorkspaceId();
//        List<RuntimeRankResult> list = (List<RuntimeRankResult>) RedisUtils.get(redisKey);
//        if (!CollectionUtils.isEmpty(list)){
//            return BusinessResult.success(list);
//        }
        List<RuntimeRankResult> runtimeRankResultList = new ArrayList<>();
        Date date = new Date();
        List<Map<String, Object>> definitionList = processDefinitionService.selectByWorkspaceIdAndTime(request.getWorkspaceId(),
                DateUtils.getStartOfDay(DateUtils.getSomeDay(new Date(), -1)), DateUtils.getEndOfDay(DateUtils.getSomeDay(new Date(), -1)));
        for (Map<String, Object> m : definitionList) {
            String definitionId = (String) m.get("id");
            Double runtime = taskInstanceService.runtimeTotalByDefinition(definitionId, new int[]{});
            runtimeRankResultList.add(new RuntimeRankResult((String) m.get("platformTaskId"), (String)m.get("name"), DoubleUtils.formatDouble(runtime), (Integer)m.get("scheduleType")));
        }
        runtimeRankResultList.sort(Comparator.comparing(RuntimeRankResult::getSpeedTime).reversed());

//        RedisUtils.set(redisKey, runtimeRankResultList, ONE_DAY_TIME);
        return BusinessResult.success(runtimeRankResultList);
    }

    @Override
    public BusinessResult<List<RunErrorRankResult>> runErrorRank(SchedulerHomepageRequest request) {
//        String redisKey = PRERUNERRORRANK + request.getWorkspaceId();
//        List<RunErrorRankResult> list = (List<RunErrorRankResult>) RedisUtils.get(redisKey);
//        if (!CollectionUtils.isEmpty(list)){
//            return BusinessResult.success(list);
//        }
        List<RunErrorRankResult> runErrorRankList = new ArrayList<>();
        Date date = new Date();
        List<Map<String, Object>> definitionList = processDefinitionService.selectByWorkspaceIdAndTime(request.getWorkspaceId(),
                DateUtils.getStartOfDay(DateUtils.getSomeDay(date, -1)), DateUtils.getEndOfDay(DateUtils.getSomeDay(date, -1)));
        for (Map<String, Object> m : definitionList) {
            String definitionId = (String) m.get("id");
            Long errorNum = taskInstanceService.getCountByByDefinitionAndStates(definitionId, new int[]{ExecutionStatus.FAILURE.getCode()});
            runErrorRankList.add(new RunErrorRankResult((String)m.get("platformTaskId"), (String)m.get("name"), errorNum, (Integer)m.get("scheduleType")));
        }
        runErrorRankList.sort(Comparator.comparing(RunErrorRankResult::getErrorNum).reversed());
        runErrorRankList = runErrorRankList.parallelStream().filter(runErrorRank -> "0".equals(runErrorRank.getErrorNum())).sorted(Comparator.comparing(RunErrorRankResult::getErrorNum).reversed()).collect(Collectors.toList());
        runErrorRankList = runErrorRankList.size() < 6 ? runErrorRankList : runErrorRankList.subList(0, 6);

//        RedisUtils.set(redisKey, runErrorRankList, ONE_DAY_TIME);
        return BusinessResult.success(runErrorRankList);
    }

    @Override
    public WorkBenchResult workbench(String userId, String id) {
//        String redisKey = WORKBENCH + userId + id;
//        WorkBenchResult redisCache = (WorkBenchResult) RedisUtils.get(redisKey);
//        if (null != redisCache){
//            return redisCache;
//        }
        WorkBenchResult result = new WorkBenchResult();
        List<Map<String, Object>> list = taskInstanceService.selectByWorkspaceIdAndUserId(userId, id);
        //正在执行
        Map<Integer, Long> state = list.stream().collect(
                Collectors.groupingBy(org -> (int)org.get("state"), Collectors.counting()));
        Long success = state.get(ExecutionStatus.SUCCESS.getCode()) == null? 0L : state.get(ExecutionStatus.SUCCESS.getCode());
        result.setSuccess(success);
        Long failure = state.get(ExecutionStatus.FAILURE.getCode()) == null? 0L: state.get(ExecutionStatus.FAILURE.getCode());
        result.setFailure(failure);
        Long running = state.get(ExecutionStatus.RUNNING_EXECUTION.getCode()) == null? 0L : state.get(ExecutionStatus.RUNNING_EXECUTION.getCode());
        result.setRunning(running);
        Long notRun = list.size() - success - failure - running;
        result.setNotRun(notRun);
//        RedisUtils.set(redisKey, result, ONE_HOUR_TIME);
        return result;
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
        list.add(new TaskSituationResult(TaskStatus.SUCCESS.getDescp(), args[0], DoubleUtils.formatDouble((double)args[0] / count)));
        list.add(new TaskSituationResult(TaskStatus.FAILURE.getDescp(), args[1], DoubleUtils.formatDouble((double)args[1] / count)));
        list.add(new TaskSituationResult(TaskStatus.RUNNING_EXECUTION.getDescp(), args[2], DoubleUtils.formatDouble((double)args[2] / count)));
        list.add(new TaskSituationResult(TaskStatus.WAITING_THREAD.getDescp(), args[3], DoubleUtils.formatDouble((double)args[3] / count)));
        return list;
    }

    public static void main(String[] args) {
        System.out.println(getDataSizeUnit(0L));
    }
}
