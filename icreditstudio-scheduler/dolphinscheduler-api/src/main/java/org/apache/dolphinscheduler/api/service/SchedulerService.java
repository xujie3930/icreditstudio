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

package org.apache.dolphinscheduler.api.service;

import org.apache.dolphinscheduler.common.enums.FailureStrategy;
import org.apache.dolphinscheduler.common.enums.Priority;
import org.apache.dolphinscheduler.common.enums.ReleaseState;
import org.apache.dolphinscheduler.common.enums.WarningType;
import org.apache.dolphinscheduler.dao.entity.User;

import java.io.IOException;
import java.util.Map;

/**
 * scheduler service
 */
public interface SchedulerService {

    /**
     * save schedule
     *
     * @param loginUser               login user
     * @param projectName             project name
     * @param processDefineId         process definition id
     * @param schedule                scheduler
     * @param warningType             warning type
     * @param warningGroupId          warning group id
     * @param failureStrategy         failure strategy
     * @param processInstancePriority process instance priority
     * @param workerGroup             worker group
     * @return create result code
     */
    Map<String, Object> insertSchedule(User loginUser, String projectName,
                                       String processDefineId,
                                       String schedule,
                                       WarningType warningType,
                                       String warningGroupId,
                                       FailureStrategy failureStrategy,
                                       String receivers,
                                       String receiversCc,
                                       Priority processInstancePriority,
                                       String workerGroup) throws IOException;

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
    Map<String, Object> updateSchedule(User loginUser,
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
                                       String workerGroup) throws IOException;


    /**
     * set schedule online or offline
     *
     * @param loginUser      login user
     * @param projectName    project name
     * @param id             scheduler id
     * @param scheduleStatus schedule status
     * @return publish result code
     */
    Map<String, Object> setScheduleState(User loginUser,
                                         String projectName,
                                         String id,
                                         ReleaseState scheduleStatus);

    /**
     * query schedule
     *
     * @param loginUser       login user
     * @param projectCode     project code
     * @param processDefineId process definition id
     * @param pageNo          page number
     * @param pageSize        page size
     * @param searchVal       search value
     * @return schedule list page
     */
    Map<String, Object> querySchedule(User loginUser, String projectCode, String processDefineId, String searchVal, Integer pageNo, Integer pageSize);

    /**
     * query schedule list
     *
     * @param loginUser   login user
     * @param projectCode project code
     * @return schedule list
     */
    Map<String, Object> queryScheduleList(User loginUser, String projectCode);

    /**
     * delete schedule
     *
     * @param projectCode project code
     * @param scheduleId  schedule id
     * @throws RuntimeException runtime exception
     */
    void deleteSchedule(String projectCode, String scheduleId);

    /**
     * delete schedule by id
     *
     * @param loginUser   login user
     * @param projectCode project code
     * @param scheduleId  scheule id
     * @return delete result code
     */
    Map<String, Object> deleteScheduleById(User loginUser, String projectCode, String scheduleId);

    /**
     * preview schedule
     *
     * @param loginUser   login user
     * @param projectCode project code
     * @param schedule    schedule expression
     * @return the next five fire time
     */
    Map<String, Object> previewSchedule(User loginUser, String projectCode, String schedule);

    void updateStatusByProcessDefinitionId(String processDefinitionId, int state);

    void deleteByProcessDefinitionId(String processDefinitionId);
}