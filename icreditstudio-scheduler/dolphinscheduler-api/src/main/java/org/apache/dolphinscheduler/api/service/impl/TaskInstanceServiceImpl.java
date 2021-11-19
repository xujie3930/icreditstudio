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

import org.apache.dolphinscheduler.api.request.SchedulerHomepageRequest;
import org.apache.dolphinscheduler.api.service.TaskInstanceService;
import org.apache.dolphinscheduler.api.service.result.TaskCountResult;
import org.apache.dolphinscheduler.common.enums.ExecutionStatus;
import org.apache.dolphinscheduler.common.utils.DateUtils;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.dao.mapper.TaskInstanceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * task instance service impl
 */
@Service
public class TaskInstanceServiceImpl extends BaseServiceImpl implements TaskInstanceService {

//    @Resource
//    ProjectMapper projectMapper;
//
//    @Resource
//    ProjectService projectService;
//
//    @Resource
//    ProcessService processService;

    @Resource
    TaskInstanceMapper taskInstanceMapper;

//    @Resource
//    ProcessInstanceService processInstanceService;
//
//    @Resource
//    UsersService usersService;
//
//    /**
//     * query task list by project, process instance, task name, task start time, task end time, task status, keyword paging
//     *
//     * @param loginUser         login user
//     * @param projectName       project name
//     * @param processInstanceId process instance id
//     * @param searchVal         search value
//     * @param taskName          task name
//     * @param stateType         state type
//     * @param host              host
//     * @param startDate         start time
//     * @param endDate           end time
//     * @param pageNo            page number
//     * @param pageSize          page size
//     * @return task list page
//     */
//    @Override
//    public Map<String, Object> queryTaskListPaging(User loginUser, String projectName,
//                                                   Integer processInstanceId, String processInstanceName, String taskName, String executorName, String startDate,
//                                                   String endDate, String searchVal, ExecutionStatus stateType, String host,
//                                                   Integer pageNo, Integer pageSize) {
//        Map<String, Object> result = new HashMap<>();
//        Project project = projectMapper.queryByName(projectName);
//
////        Map<String, Object> checkResult = projectService.checkProjectAndAuth(loginUser, project, projectName);
////        Status status = (Status) checkResult.get(Constants.STATUS);
////        if (status != Status.SUCCESS) {
////            return checkResult;
////        }
//
//        int[] statusArray = null;
//        if (stateType != null) {
//            statusArray = new int[]{stateType.ordinal()};
//        }
//
//        Map<String, Object> checkAndParseDateResult = checkAndParseDateParameters(startDate, endDate);
//        if (checkAndParseDateResult.get(Constants.STATUS) != Status.SUCCESS) {
//            return checkAndParseDateResult;
//        }
//        Date start = (Date) checkAndParseDateResult.get(Constants.START_TIME);
//        Date end = (Date) checkAndParseDateResult.get(Constants.END_TIME);
//
//        Page<TaskInstance> page = new Page<>(pageNo, pageSize);
//        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(pageNo, pageSize);
////        int executorId = usersService.getUserIdByName(executorName);
//
//        IPage<TaskInstance> taskInstanceIPage = taskInstanceMapper.queryTaskInstanceListPaging(
//                page, project.getCode(), processInstanceId, processInstanceName, searchVal, taskName, loginUser.getId(), statusArray, host, start, end
//        );
//        Set<String> exclusionSet = new HashSet<>();
//        exclusionSet.add(Constants.CLASS);
//        exclusionSet.add("taskJson");
//        List<TaskInstance> taskInstanceList = taskInstanceIPage.getRecords();
//
//        for (TaskInstance taskInstance : taskInstanceList) {
//            taskInstance.setDuration(DateUtils.format2Duration(taskInstance.getStartTime(), taskInstance.getEndTime()));
//            //TODO
////            User executor = usersService.queryUser(taskInstance.getExecutorId());
////            if (null != executor) {
////                taskInstance.setExecutorName(executor.getUserName());
////            }
//        }
//        pageInfo.setTotalCount((int) taskInstanceIPage.getTotal());
//        pageInfo.setLists(CollectionUtils.getListByExclusion(taskInstanceIPage.getRecords(), exclusionSet));
//        result.put(Constants.DATA_LIST, pageInfo);
//        putMsg(result, Status.SUCCESS);
//
//        return result;
//    }
//
//    /**
//     * change one task instance's state from failure to forced success
//     *
//     * @param loginUser      login user
//     * @param projectName    project name
//     * @param taskInstanceId task instance id
//     * @return the result code and msg
//     */
//    @Override
//    public Map<String, Object> forceTaskSuccess(User loginUser, String projectName, Integer taskInstanceId) {
//        Map<String, Object> result = new HashMap<>();
//        Project project = projectMapper.queryByName(projectName);
//
//        // check user auth
////        Map<String, Object> checkResult = projectService.checkProjectAndAuth(loginUser, project, projectName);
////        Status status = (Status) checkResult.get(Constants.STATUS);
////        if (status != Status.SUCCESS) {
////            return checkResult;
////        }
//
//        // check whether the task instance can be found
//        TaskInstance task = taskInstanceMapper.selectById(taskInstanceId);
//        if (task == null) {
//            putMsg(result, Status.TASK_INSTANCE_NOT_FOUND);
//            return result;
//        }
//
//        // check whether the task instance state type is failure or cancel
//        if (!task.getState().typeIsFailure() && !task.getState().typeIsCancel()) {
//            putMsg(result, Status.TASK_INSTANCE_STATE_OPERATION_ERROR, taskInstanceId, task.getState().toString());
//            return result;
//        }
//
//        // change the state of the task instance
//        task.setState(ExecutionStatus.FORCED_SUCCESS);
//        int changedNum = taskInstanceMapper.updateById(task);
//        if (changedNum > 0) {
//            putMsg(result, Status.SUCCESS);
//        } else {
//            putMsg(result, Status.FORCE_TASK_SUCCESS_ERROR);
//        }
//
//        return result;
//    }

    @Override
    public Map<String, Object> queryTaskListPaging(User loginUser, String projectName, Integer processInstanceId, String processInstanceName, String taskName, String executorName, String startDate, String endDate, String searchVal, ExecutionStatus stateType, String host, Integer pageNo, Integer pageSize) {
        return null;
    }

    @Override
    public Map<String, Object> forceTaskSuccess(User loginUser, String projectName, Integer taskInstanceId) {
        return null;
    }

    @Override
    public Long countByWorkspaceIdAndTime(String workspaceId, String userId, Date startTime, Date endTime, int[] statusArray) {
        return taskInstanceMapper.countByWorkspaceIdAndTime(workspaceId, userId, startTime, endTime, statusArray);
    }

    @Override
    public List<TaskCountResult> countByDay(String userId, SchedulerHomepageRequest request) {
        Date startTime;
        Date endTime;
        //默认统计前七天的数据
        if (Objects.isNull(request.getSchedulerStartTime()) && Objects.isNull(request.getSchedulerEndTime())){
            startTime = DateUtils.getStartOfDay(DateUtils.getSomeDay(new Date(), -7));
            endTime = DateUtils.getEndOfDay(DateUtils.getSomeDay(new Date(), -1));
        }else {
            startTime = DateUtils.getStartOfDay(new Date(request.getSchedulerStartTime()));
            endTime = DateUtils.getEndOfDay(new Date(request.getSchedulerEndTime()));
        }

        //TODO:数据量大后t_ds_task_instance表加覆盖索引
        List<Map<String, Object>> countByDay = taskInstanceMapper.countByDay(request.getWorkspaceId(), userId, request.getScheduleType(), startTime, endTime,  new int[]{});
        List<TaskCountResult> list = getDaysCount(startTime, endTime, countByDay);
        return list;
    }

    private List<TaskCountResult> getDaysCount(Date startTime, Date endTime, List<Map<String, Object>> countByDay) {
        List<TaskCountResult> list = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(startTime);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(endTime);
        Map<String, Object> tempMap = new HashMap<>();
        for (Map<String, Object> m : countByDay) {
            tempMap.put((String) m.get("date"), m.get("count"));
        }
        while (tempStart.before(tempEnd)) {
            String date = dateFormat.format(tempStart.getTime());
            if (!tempMap.containsKey(date)) {
                tempMap.put(date, 0L);
            }
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        for (Map.Entry<String, Object> m : tempMap.entrySet()) {
            list.add(new TaskCountResult((String) m.getKey(), (long)m.getValue()));
        }
        list.sort(Comparator.comparing(TaskCountResult::getDate));
        return list;
    }

    @Override
    public List<Map<String, Object>> runtimeTotalByDefinition(String workspaceId, String userId, int[] statusArray, Date startTime, Date endTime) {
        return taskInstanceMapper.runtimeTotalByDefinition(workspaceId, userId, statusArray, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>>  getCountByByDefinitionAndStates(String workspaceId, String userId, int[] statusArray, Date startTime, Date endTime) {
        return taskInstanceMapper.getCountByByDefinitionAndStates(workspaceId, userId, statusArray, startTime, endTime);
    }

    @Override
    public Long totalRecordsByWorkspaceIdAndTime(String workspaceId, String userId, Date startTime, Date endTime) {
        return taskInstanceMapper.totalRecordsByWorkspaceIdAndTime(workspaceId, userId, startTime, endTime);
    }

    @Override
    public Long totalBytesByWorkspaceIdAndTime(String workspaceId, String userId, Date startTime, Date endTime) {
        return taskInstanceMapper.totalBytesByWorkspaceIdAndTime(workspaceId, userId, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> selectByWorkspaceIdAndUserId(String userId, String id) {
        return taskInstanceMapper.selectByWorkspaceIdAndUserId(userId, id);
    }
}
