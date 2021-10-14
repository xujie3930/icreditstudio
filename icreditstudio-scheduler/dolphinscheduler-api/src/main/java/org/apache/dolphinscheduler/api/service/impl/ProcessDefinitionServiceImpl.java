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

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.dolphinscheduler.api.enums.Status;
import org.apache.dolphinscheduler.api.service.ProcessDefinitionService;
import org.apache.dolphinscheduler.api.service.SchedulerService;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.common.enums.Flag;
import org.apache.dolphinscheduler.common.enums.ReleaseState;
import org.apache.dolphinscheduler.common.process.Property;
import org.apache.dolphinscheduler.common.utils.CollectionUtils;
import org.apache.dolphinscheduler.common.utils.JSONUtils;
import org.apache.dolphinscheduler.dao.entity.ProcessData;
import org.apache.dolphinscheduler.dao.entity.ProcessDefinition;
import org.apache.dolphinscheduler.dao.entity.Schedule;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.dao.mapper.ProcessDefinitionMapper;
import org.apache.dolphinscheduler.dao.mapper.ScheduleMapper;
import org.apache.dolphinscheduler.service.process.ProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

//import static org.apache.dolphinscheduler.common.Constants.CMD_PARAM_SUB_PROCESS_DEFINE_ID;

/**
 * process definition service impl
 */
@Service
public class ProcessDefinitionServiceImpl extends BaseServiceImpl implements ProcessDefinitionService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessDefinitionServiceImpl.class);
//
//    private static final String PROCESSDEFINITIONCODE = "processDefinitionCode";
//
//    private static final String PROCESSDEFINITIONID = "processDefinitionId";
//
//    private static final String RELEASESTATE = "releaseState";
//
//    private static final String TASKS = "tasks";
//
//    @javax.annotation.Resource
//    private ProjectMapper projectMapper;
//
//    @javax.annotation.Resource
//    private ProjectService projectService;

//    @Autowired
//    private UserMapper userMapper;

//    @javax.annotation.Resource
//    private ProcessDefinitionLogMapper processDefinitionLogMapper;

    private static final String PROCESSDEFINITIONID = "processDefinitionId";

    @Resource
    private ProcessDefinitionMapper processDefinitionMapper;
    @Resource
    private ScheduleMapper scheduleMapper;
    @Resource
    private SchedulerService schedulerService;
    @Autowired
    private ProcessService processService;


    @Override
    public Map<String, Object> createProcessDefinition(User loginUser, String projectCode, String name, String processDefinitionJson, String desc, String locations, String connects) throws JsonProcessingException {
        Map<String, Object> result = new HashMap<>(5);
//        Project project = projectMapper.queryByName(projectName);
        // check project auth
//        Map<String, Object> checkResult = projectService.checkProjectAndAuth(loginUser, project, projectName);
//        Status resultStatus = (Status) checkResult.get(Constants.STATUS);
//        if (resultStatus != Status.SUCCESS) {
//            return checkResult;
//        }

        ProcessDefinition processDefine = new ProcessDefinition();
        Date now = new Date();

        ProcessData processData = JSONUtils.parseObject(processDefinitionJson, ProcessData.class);
        Map<String, Object> checkProcessJson = checkProcessNodeList(processData, processDefinitionJson);
        if (checkProcessJson.get(Constants.STATUS) != Status.SUCCESS) {
            return checkProcessJson;
        }

        processDefine.setName(name);
        processDefine.setReleaseState(ReleaseState.OFFLINE);
        processDefine.setProjectCode(projectCode);
        processDefine.setUserId(loginUser.getId());
        processDefine.setProcessDefinitionJson(processDefinitionJson);
        processDefine.setDescription(desc);
        processDefine.setLocations(locations);
        processDefine.setConnects(connects);
        processDefine.setTimeout(processData.getTimeout());
        processDefine.setTenantCode(loginUser.getTenantCode());
        processDefine.setModifyBy(loginUser.getUserName());
//        processDefine.setResourceIds(getResourceIds(processData));

        //custom global params
        List<Property> globalParamsList = processData.getGlobalParams();
        if (CollectionUtils.isNotEmpty(globalParamsList)) {
            Set<Property> globalParamsSet = new HashSet<>(globalParamsList);
            globalParamsList = new ArrayList<>(globalParamsSet);
            processDefine.setGlobalParamList(globalParamsList);
        }
        processDefine.setCreateTime(now);
        processDefine.setUpdateTime(now);
        processDefine.setFlag(Flag.YES);
        processDefinitionMapper.insert(processDefine);
        putMsg(result, Status.SUCCESS);
        result.put(PROCESSDEFINITIONID, processDefine.getId());
        return result;
    }

    @Override
    public Map<String, Object> queryProcessDefinitionList(User loginUser, String projectName) {
        return null;
    }

    @Override
    public Map<String, Object> queryProcessDefinitionListPaging(User loginUser, String projectName, String searchVal, Integer pageNo, Integer pageSize, String userId) {
        return null;
    }

    @Override
    public Map<String, Object> queryProcessDefinitionById(User loginUser, String projectName, String processId) {
        return null;
    }

    @Override
    public Map<String, Object> queryProcessDefinitionByName(User loginUser, String projectName, String processDefinitionName) {
        return null;
    }

    @Override
    public Map<String, Object> batchCopyProcessDefinition(User loginUser, String projectName, String processDefinitionIds, String targetProjectId) {
        return null;
    }

    @Override
    public Map<String, Object> batchMoveProcessDefinition(User loginUser, String projectName, String processDefinitionIds, String targetProjectId) {
        return null;
    }

    @Override
    public Map<String, Object> updateProcessDefinition(User loginUser, String projectCode, String id, String name, String processDefinitionJson, String desc, String locations, String connects) {
        Map<String, Object> result = new HashMap<>(5);

        ProcessData processData = JSONUtils.parseObject(processDefinitionJson, ProcessData.class);
        Map<String, Object> checkProcessJson = checkProcessNodeList(processData, processDefinitionJson);
        if ((checkProcessJson.get(Constants.STATUS) != Status.SUCCESS)) {
            return checkProcessJson;
        }
        ProcessDefinition processDefine = processService.findProcessDefineById(id);
        // check process definition exists
        if (processDefine == null) {
            putMsg(result, Status.PROCESS_DEFINE_NOT_EXIST, id);
            return result;
        }

        if (processDefine.getReleaseState() == ReleaseState.ONLINE) {
            // online can not permit edit
            putMsg(result, Status.PROCESS_DEFINE_NOT_ALLOWED_EDIT, processDefine.getName());
            return result;
        }

        if (!name.equals(processDefine.getName())) {
            // check whether the new process define name exist
            ProcessDefinition definition = processDefinitionMapper.verifyByDefineName(projectCode, name);
            if (definition != null) {
                putMsg(result, Status.VERIFY_PROCESS_DEFINITION_NAME_UNIQUE_ERROR, name);
                return result;
            }
        }

        Date now = new Date();

        processDefine.setId(id);
        processDefine.setName(name);
        processDefine.setReleaseState(ReleaseState.OFFLINE);
        processDefine.setProjectCode(projectCode);
        processDefine.setProcessDefinitionJson(processDefinitionJson);
        processDefine.setDescription(desc);
        processDefine.setLocations(locations);
        processDefine.setConnects(connects);
        processDefine.setTimeout(processData.getTimeout());
        processDefine.setTenantCode(loginUser.getTenantCode());
        processDefine.setModifyBy(loginUser.getUserName());

        //custom global params
        List<Property> globalParamsList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(processData.getGlobalParams())) {
            Set<Property> userDefParamsSet = new HashSet<>(processData.getGlobalParams());
            globalParamsList = new ArrayList<>(userDefParamsSet);
        }
        processDefine.setGlobalParamList(globalParamsList);
        processDefine.setUpdateTime(now);
        processDefine.setFlag(Flag.YES);
        if (processDefinitionMapper.updateById(processDefine) > 0) {
            putMsg(result, Status.SUCCESS);

        } else {
            putMsg(result, Status.UPDATE_PROCESS_DEFINITION_ERROR);
        }
        return result;
    }

    @Override
    public Map<String, Object> verifyProcessDefinitionName(User loginUser, String projectCode, String name) {
        Map<String, Object> result = new HashMap<>();
        ProcessDefinition processDefinition = processDefinitionMapper.verifyByDefineName(projectCode, name);
        if (processDefinition == null) {
            putMsg(result, Status.SUCCESS);
        } else {
            putMsg(result, Status.VERIFY_PROCESS_DEFINITION_NAME_UNIQUE_ERROR, name);
        }
        return result;
    }

    @Override
    public Map<String, Object> deleteProcessDefinitionById(User loginUser, String projectCode, String processDefinitionId) {

        Map<String, Object> result = new HashMap<>(5);

        ProcessDefinition processDefinition = processDefinitionMapper.selectById(processDefinitionId);

        if (processDefinition == null) {
            putMsg(result, Status.PROCESS_DEFINE_NOT_EXIST, processDefinitionId);
            return result;
        }

        // check process definition is already online
        if (processDefinition.getReleaseState() == ReleaseState.ONLINE) {
            putMsg(result, Status.PROCESS_DEFINE_STATE_ONLINE, processDefinitionId);
            return result;
        }

        // get the timing according to the process definition
        List<Schedule> schedules = scheduleMapper.queryByProcessDefinitionId(processDefinitionId);
        if (!schedules.isEmpty() && schedules.size() > 1) {
            logger.warn("scheduler num is {},Greater than 1", schedules.size());
            putMsg(result, Status.DELETE_PROCESS_DEFINE_BY_ID_ERROR);
            return result;
        } else if (schedules.size() == 1) {
            Schedule schedule = schedules.get(0);
            if (schedule.getReleaseState() == ReleaseState.OFFLINE) {
                scheduleMapper.deleteById(schedule.getId());
            } else if (schedule.getReleaseState() == ReleaseState.ONLINE) {
                putMsg(result, Status.SCHEDULE_CRON_STATE_ONLINE, schedule.getId());
                return result;
            }
        }

        int delete = processDefinitionMapper.deleteById(processDefinitionId);

        if (delete > 0) {
            putMsg(result, Status.SUCCESS);
        } else {
            putMsg(result, Status.DELETE_PROCESS_DEFINE_BY_ID_ERROR);
        }
        return result;
    }

    @Override
    public Map<String, Object> releaseProcessDefinition(User loginUser, String projectCode, int id, int releaseState) {
        HashMap<String, Object> result = new HashMap<>();
        ReleaseState state = ReleaseState.getEnum(releaseState);

        // check state
        if (null == state) {
            putMsg(result, Status.REQUEST_PARAMS_NOT_VALID_ERROR, "releaseState");
            return result;
        }

        ProcessDefinition processDefinition = processDefinitionMapper.selectById(id);

        switch (state) {
            case ONLINE:
                processDefinition.setReleaseState(state);
                processDefinitionMapper.updateById(processDefinition);
                break;
            case OFFLINE:
                processDefinition.setReleaseState(state);
                processDefinitionMapper.updateById(processDefinition);
                List<Schedule> scheduleList = scheduleMapper.selectAllByProcessDefineArray(
                        new String[]{processDefinition.getId()}
                );

                for (Schedule schedule : scheduleList) {
                    logger.info("set schedule offline, project id: {}, schedule id: {}, process definition id: {}", projectCode, schedule.getId(), id);
                    // set status
                    schedule.setReleaseState(ReleaseState.OFFLINE);
                    scheduleMapper.updateById(schedule);
                    schedulerService.deleteSchedule(projectCode, schedule.getId());
                }
                break;
            default:
                putMsg(result, Status.REQUEST_PARAMS_NOT_VALID_ERROR, "releaseState");
                return result;
        }

        putMsg(result, Status.SUCCESS);
        return result;
    }

    @Override
    public void batchExportProcessDefinitionByIds(User loginUser, String projectName, String processDefinitionIds, HttpServletResponse response) {

    }

    @Override
    public Map<String, Object> importProcessDefinition(User loginUser, MultipartFile file, String currentProjectName) {
        return null;
    }

    @Override
    public Map<String, Object> checkProcessNodeList(ProcessData processData, String processDefinitionJson) {
        return null;
    }

    @Override
    public Map<String, Object> getTaskNodeListByDefinitionCode(Long defineCode) {
        return null;
    }

    @Override
    public Map<String, Object> getTaskNodeListByDefinitionCodeList(String defineCodeList) {
        return null;
    }

    @Override
    public Map<String, Object> queryProcessDefinitionAllByProjectId(String projectId) {
        return null;
    }

    @Override
    public Map<String, Object> viewTree(String processId, Integer limit) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> switchProcessDefinitionVersion(User loginUser, String projectName, String processDefinitionId, long version) {
        return null;
    }

    @Override
    public Map<String, Object> queryProcessDefinitionVersions(User loginUser, String projectName, int pageNo, int pageSize, long processDefinitionCode) {
        return null;
    }

    @Override
    public Map<String, Object> deleteByProcessDefinitionIdAndVersion(User loginUser, String projectName, String processDefinitionId, long version) {
        return null;
    }

    @Override
    public boolean checkHasAssociatedProcessDefinition(String processDefinitionId, long version) {
        return false;
    }

    @Override
    public List<Map<String, Object>> selectByWorkspaceIdAndTime(String workspaceId, Date startOfDay, Date endOfDay) {
        return processDefinitionMapper.selectByWorkspaceIdAndTime(workspaceId, startOfDay, endOfDay);
    }
}
