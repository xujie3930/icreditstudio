package org.apache.dolphinscheduler.api.service.impl;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.utils.DateUtils;
import org.apache.dolphinscheduler.api.request.SchedulerHomepageRequest;
import org.apache.dolphinscheduler.api.service.*;
import org.apache.dolphinscheduler.api.service.result.TaskRough;
import org.apache.dolphinscheduler.api.service.result.TaskSituation;
import org.apache.dolphinscheduler.common.enums.ExecutionStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        Date startTime = DateUtils.getFirstSecondOfDate(DateUtils.addDate(new Date(), -3));
        //前一天24点
        Date endTime = DateUtils.getLastSecondOfDate(DateUtils.addDate(new Date(), -1));
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
        Date startTime = DateUtils.getFirstSecondOfDate(date);
        Date endTime = DateUtils.getLastSecondOfDate(date);
        Long success = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.SUCCESS.getCode()});
        Long fail = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.FAILURE.getCode()});
        Long running = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.RUNNING_EXECUTION.getCode()});
        Long waiting = taskInstanceService.countByWorkspaceIdAndTime(workspaceId, startTime, endTime, new int[]{ExecutionStatus.WAITING_THREAD.getCode(),
                ExecutionStatus.WAITING_DEPEND.getCode()});
        /*List<TaskSituation> taskSituationList = getTaskSituationList(success, fail, running, waiting);*/
        List<TaskSituation> taskSituationList = new ArrayList<>();
        return BusinessResult.success(taskSituationList);
    }

    private void getTaskSituationList(Long...args) {
        Long count = 0L;
        for (Long arg : args) {
            count += arg;
        }
        ArrayList<Object> list = new ArrayList<>();
    }
}
