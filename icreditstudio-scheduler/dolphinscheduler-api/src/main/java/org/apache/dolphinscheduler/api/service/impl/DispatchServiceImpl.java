package org.apache.dolphinscheduler.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.common.ResourceCodeBean;
import org.apache.dolphinscheduler.api.feign.DataSyncDispatchTaskFeignClient;
import org.apache.dolphinscheduler.api.param.DispatchTaskPageParam;
import org.apache.dolphinscheduler.api.param.LogPageParam;
import org.apache.dolphinscheduler.api.service.DispatchService;
import org.apache.dolphinscheduler.api.service.PlatformExecutorService;
import org.apache.dolphinscheduler.api.service.result.DispatchTaskPageResult;
import org.apache.dolphinscheduler.common.utils.DateUtils;
import org.apache.dolphinscheduler.common.utils.StringUtils;
import org.apache.dolphinscheduler.common.vo.DispatchLogVO;
import org.apache.dolphinscheduler.common.enums.CommandType;
import org.apache.dolphinscheduler.common.enums.ExecutionStatus;
import org.apache.dolphinscheduler.dao.entity.Command;
import org.apache.dolphinscheduler.dao.entity.ProcessDefinition;
import org.apache.dolphinscheduler.dao.entity.ProcessInstance;
import org.apache.dolphinscheduler.dao.mapper.ProcessInstanceMapper;
import org.apache.dolphinscheduler.dao.mapper.TaskInstanceMapper;
import org.apache.dolphinscheduler.service.process.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.apache.dolphinscheduler.common.Constants.CMDPARAM_RECOVER_PROCESS_ID_STRING;

@Service
public class DispatchServiceImpl implements DispatchService {

    @Autowired
    private DataSyncDispatchTaskFeignClient dataSyncDispatchTaskFeignClient;
    @Autowired
    private ProcessService processService;
    @Autowired
    private ProcessInstanceMapper processInstanceMapper;
    @Autowired
    private TaskInstanceMapper taskInstanceMapper;
    @Autowired
    private PlatformExecutorService platformExecutorService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<BusinessPageResult<DispatchTaskPageResult>> dispatchPage(DispatchTaskPageParam param) {
        if(StringUtils.isEmpty(param.getCurrLoginUserId())){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000011.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000011.message);
        }
        if(StringUtils.isEmpty(param.getWorkspaceId())){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000010.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000010.message);
        }
        return dataSyncDispatchTaskFeignClient.dispatchPage(param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> reStartOrStop(String processInstanceId, String execType) {
        // execType 执行类型 ：0 表示 重跑，1 表示 终止
        if(StringUtils.isEmpty(processInstanceId)){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000004.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000004.message);
        }
        if(StringUtils.isEmpty(execType)){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000005.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000005.message);
        }
        //processInstanceId , ExecuteType executeType
        int execStatus = this.executeInstance(processInstanceId, execType);
        if(execStatus == 0){
            return BusinessResult.success(true);
        }
        return BusinessResult.fail("", "任务执行失败");
    }

    /**
     * 任务重跑|终止
     * @param instanceId
     * @param execType
     * @return  返回值为 0|1 ，0 表示成功 ，1 表示失败
     */
    private int executeInstance(String instanceId, String execType) {
        ProcessInstance processInstance = processService.findProcessInstanceDetailById(instanceId);
        ProcessDefinition processDefinition = processService.findProcessDefineById(processInstance.getProcessDefinitionId());
        int result = 0;
        if("1".equals(execType)){
            if (processInstance.getState() != ExecutionStatus.RUNNING_EXECUTION) {//该任务不在 【执行中】，不能终止
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000008.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000008.message);
            }
            result = updateProcessInstancePrepare(processInstance, CommandType.STOP, ExecutionStatus.READY_STOP);
        }else{
            if (processInstance.getState() == ExecutionStatus.RUNNING_EXECUTION || processInstance.getState() == ExecutionStatus.SUBMITTED_SUCCESS ||
                    processInstance.getState() == ExecutionStatus.WAITTING_THREAD) {//该任务正在 【执行中】中，不能重跑
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000009.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000009.message);
            }
            dataSyncDispatchTaskFeignClient.updateExecStatusByScheduleId(processDefinition.getId());
            handleProcessInstance(processInstance);
            result = insertCommand(instanceId, processDefinition.getId(), CommandType.REPEAT_RUNNING);
        }
        return result;
    }

    /**
     *  处理 processInstance 并保存
     * @param processInstance
     */
    private void handleProcessInstance(ProcessInstance processInstance) {
        JSONObject obj = JSONObject.parseObject(processInstance.getProcessInstanceJson());
        JSONObject taskObj = (JSONObject) obj.getJSONArray("tasks").get(0);
        JSONObject paramObj = taskObj.getJSONObject("params");
        if(!"1".equals(paramObj.getString("customConfig"))){
            return ;
        }
        JSONObject jsonObj = JSONObject.parseObject(paramObj.getString("json"));
        JSONObject content = (JSONObject) jsonObj.getJSONArray("content").get(0);
        JSONObject writer = content.getJSONObject("writer");
        if(!"hdfswriter".equals(writer.getString("name"))){
            return ;
        }
        JSONObject parameter = writer.getJSONObject("parameter");
        String oldFileName = parameter.getString("fileName");
        StringBuilder target = new StringBuilder("\\\"fileName\\\":\\\"");
        target.append(oldFileName).append("\\\"");
        StringBuilder replaceStr = new StringBuilder("\\\"fileName\\\":\\\"");
        replaceStr.append(processInstance.getFileName()).append("\\\"");
        //设置 writemode 为backandwrite，备份之前的文件内容，重新写入
        String instanceJson = processInstance.getProcessInstanceJson().replace("\\\"writeMode\\\":\\\"append\\\"","\\\"writeMode\\\":\\\"backandwrite\\\"")
                .replace(target, replaceStr);
        processInstance.setProcessInstanceJson(instanceJson);
        processService.saveProcessInstance(processInstance);
    }

    private int updateProcessInstancePrepare(ProcessInstance processInstance, CommandType commandType, ExecutionStatus executionStatus) {
        processInstance.setCommandType(commandType);
        processInstance.setState(executionStatus);
        int update = processService.updateProcessInstance(processInstance);

        // determine whether the process is normal
        if (update > 0) {
            return 0;
        } else {
            return 1;
        }
    }

    private int insertCommand(String instanceId, String processDefinitionId, CommandType commandType) {
        Command command = new Command();
        command.setCommandType(commandType);
        command.setProcessDefinitionId(processDefinitionId);
        command.setCommandParam(String.format("{\"%s\":%s}",
                CMDPARAM_RECOVER_PROCESS_ID_STRING, instanceId));

        if (!processService.verifyIsNeedCreateCommand(command)) {
            throw new AppException("工作流实例[{0}]正在执行命令，请稍等...", processDefinitionId);
        }

        int create = processService.createCommand(command);

        if (create > 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public BusinessResult<BusinessPageResult<DispatchLogVO>> logPage(LogPageParam param) {
        int pageNum = (param.getPageNum() - 1) * param.getPageSize();
        String processDefinitionId = dataSyncDispatchTaskFeignClient.getProcessDefinitionIdByTaskId(param.getTaskId());
        long countLog = taskInstanceMapper.countTaskByProcessDefinitionId(processDefinitionId, param.getTaskStatus(), param.getExecTimeStart(), param.getExecTimeEnd());
        List<DispatchLogVO> logVOList = taskInstanceMapper.queryTaskByProcessDefinitionId(processDefinitionId, param.getTaskStatus(), param.getExecTimeStart(), param.getExecTimeEnd(), pageNum, param.getPageSize());
        for (DispatchLogVO dispatchLogVO : logVOList) {
            if(ExecutionStatus.SUCCESS.getCode() == dispatchLogVO.getTaskInstanceState() || ExecutionStatus.NEED_FAULT_TOLERANCE.getCode() == dispatchLogVO.getTaskInstanceState()){//成功
                dispatchLogVO.setTaskInstanceState(0);
            }else if(ExecutionStatus.SUBMITTED_SUCCESS.getCode() == dispatchLogVO.getTaskInstanceState() || ExecutionStatus.RUNNING_EXECUTION.getCode() == dispatchLogVO.getTaskInstanceState()
                    || ExecutionStatus.WAITTING_THREAD.getCode() == dispatchLogVO.getTaskInstanceState()){//执行中
                dispatchLogVO.setTaskInstanceState(2);
            }else{//失败
                dispatchLogVO.setTaskInstanceState(1);
            }
            dispatchLogVO.setTaskInstanceExecDuration(DateUtils.differSec(dispatchLogVO.getStartTime(), dispatchLogVO.getEndTime()));
        }
        return BusinessResult.success(BusinessPageResult.build(logVOList, param, countLog));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> nowRun(String taskId) {
        String definitionId = dataSyncDispatchTaskFeignClient.getProcessDefinitionIdByTaskId(taskId);
        ProcessInstance processInstance = processInstanceMapper.getLastInstanceByDefinitionId(definitionId);
        if(null == processInstance){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000014.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000014.message);
        }
        if (processInstance.getState() == ExecutionStatus.RUNNING_EXECUTION || processInstance.getState() == ExecutionStatus.SUBMITTED_SUCCESS ||
                processInstance.getState() == ExecutionStatus.WAITTING_THREAD) {//该任务正在 【执行中】中，不能执行
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000013.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000013.message);
        }
        dataSyncDispatchTaskFeignClient.updateExecStatusByScheduleId(definitionId);
        handleProcessInstance(processInstance);
        int result = insertCommand(processInstance.getId(), definitionId, CommandType.REPEAT_RUNNING);
        if(0 == result){
            return BusinessResult.success(true);
        }else{
            return BusinessResult.fail("","执行失败");
        }
    }

}
