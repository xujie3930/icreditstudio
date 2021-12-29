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
package org.apache.dolphinscheduler.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.dolphinscheduler.common.enums.ExecutionStatus;
import org.apache.dolphinscheduler.common.enums.Flag;
import org.apache.dolphinscheduler.common.vo.DispatchLogVO;
import org.apache.dolphinscheduler.dao.entity.ExecuteStatusCount;
import org.apache.dolphinscheduler.dao.entity.TaskInstance;
import org.apache.dolphinscheduler.dao.entity.result.TaskInstanceStatisticsResult;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * task instance mapper interface
 */
public interface TaskInstanceMapper extends BaseMapper<TaskInstance> {


    List<String> queryTaskByProcessIdAndState(@Param("processInstanceId") String processInstanceId,
                                               @Param("state") Integer state);

    List<TaskInstance> findValidTaskListByProcessId(@Param("processInstanceId") String processInstanceId,
                                                    @Param("flag") Flag flag);

    List<TaskInstance> queryByHostAndStatus(@Param("host") String host,
                                            @Param("states") int[] stateArray);

    int setFailoverByHostAndStateArray(@Param("host") String host,
                                       @Param("states") int[] stateArray,
                                       @Param("destStatus") ExecutionStatus destStatus);

    TaskInstance queryByInstanceIdAndName(@Param("processInstanceId") String processInstanceId,
                                          @Param("name") String name);

    Integer countTask(
            @Param("projectIds") String[] projectIds,
            @Param("taskIds") String[] taskIds);

    List<ExecuteStatusCount> countTaskInstanceStateByUser(
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime,
            @Param("projectIds") String[] projectIds);

    IPage<TaskInstance> queryTaskInstanceListPaging(IPage<TaskInstance> page,
                                                    @Param("projectId") String projectId,
                                                    @Param("processInstanceId") String processInstanceId,
                                                    @Param("searchVal") String searchVal,
                                                    @Param("taskName") String taskName,
                                                    @Param("executorId") String executorId,
                                                    @Param("states") int[] statusArray,
                                                    @Param("host") String host,
                                                    @Param("startTime") Date startTime,
                                                    @Param("endTime") Date endTime
    );

    List<Map<String, Object>> countByDay(@Param("workspaceId")String workspaceId, @Param("userId")String userId, @Param("scheduleType")Integer scheduleType, @Param("startTime")Date startTime, @Param("endTime")Date endTime,@Param("states") int[] statusArray);

    List<DispatchLogVO> queryTaskByProcessDefinitionId(@Param("processDefinitionId") String processDefinitionId, @Param("taskStatus") Integer taskStatus,
                                                       @Param("execTimeStart") Date execTimeStart, @Param("execTimeEnd") Date execTimeEnd, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    void deleteByProcessDefinitionId(@Param("processDefinitionId") String processDefinitionId);

    Long totalRecordsByWorkspaceIdAndTime(@Param("workspaceId")String workspaceId, @Param("userId")String userId, @Param("startTime")Date startTime, @Param("endTime")Date endTime);

    Long totalBytesByWorkspaceIdAndTime(@Param("workspaceId")String workspaceId, @Param("userId")String userId, @Param("startTime")Date startTime, @Param("endTime")Date endTime);

    long countTaskByProcessDefinitionId(@Param("processDefinitionId") String processDefinitionId, @Param("taskStatus") Integer taskStatus,
                                        @Param("execTimeStart") Date execTimeStart, @Param("execTimeEnd") Date execTimeEnd);

    List<TaskInstanceStatisticsResult> selectInstanceListByScanState(@Param("scanState") int scanState);

    void updateScanStateById(@Param("id") String id, @Param("scanState")Integer scanState);

    Long countByWorkspaceIdAndTime(@Param("workspaceId")String workspaceId, @Param("userId")String userId, @Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("states") int[] statusArray);
}
