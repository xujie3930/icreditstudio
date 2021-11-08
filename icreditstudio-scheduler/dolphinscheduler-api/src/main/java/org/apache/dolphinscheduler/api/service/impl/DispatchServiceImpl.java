package org.apache.dolphinscheduler.api.service.impl;

import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.common.ResourceCodeBean;
import org.apache.dolphinscheduler.api.feign.DataSyncDispatchTaskFeignClient;
import org.apache.dolphinscheduler.api.param.DispatchTaskPageParam;
import org.apache.dolphinscheduler.api.service.DispatchService;
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

import java.util.List;

import static org.apache.dolphinscheduler.common.Constants.CMDPARAM_RECOVER_PROCESS_ID_STRING;

@Service
public class DispatchServiceImpl implements DispatchService {

    @Autowired
    private DataSyncDispatchTaskFeignClient dataSyncDispatchTaskFeignClient;
    @Autowired
    private ProcessService processService;
    @Autowired
    private TaskInstanceMapper taskInstanceMapper;
    @Autowired
    private ProcessInstanceMapper processInstanceMapper;

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
    public BusinessResult<Boolean> startOrStop(String taskId, String execType) {
        if(StringUtils.isEmpty(taskId)){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000004.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000004.message);
        }
        if(StringUtils.isEmpty(execType)){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000005.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000005.message);
        }
        String processDefinitionId = dataSyncDispatchTaskFeignClient.getProcessDefinitionIdByTaskId(taskId);
        if(null == processDefinitionId){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000007.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000007.message);
        }
        String processInstanceId = processInstanceMapper.getIdByProcessDefinitionId(processDefinitionId);
        if(StringUtils.isEmpty(processInstanceId)){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000006.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000006.message);
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
            if (processInstance.getState() != ExecutionStatus.FAILURE) {//该任务不是 【失败】状态，不能重跑
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000009.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000009.message);
            }
            result = insertCommand(instanceId, processDefinition.getId(), CommandType.REPEAT_RUNNING);
        }
        return result;
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
    public BusinessResult<List<DispatchLogVO>> logPage(String taskId) {
        String processDefinitionId = dataSyncDispatchTaskFeignClient.getProcessDefinitionIdByTaskId(taskId);
        List<DispatchLogVO> logVOList = taskInstanceMapper.queryTaskByProcessDefinitionId(processDefinitionId);
        for (DispatchLogVO dispatchLogVO : logVOList) {
            if(7 == dispatchLogVO.getTaskInstanceState() || 8 == dispatchLogVO.getTaskInstanceState()){//成功
                dispatchLogVO.setTaskInstanceState(0);
            }else if(0 == dispatchLogVO.getTaskInstanceState() || 1 == dispatchLogVO.getTaskInstanceState() || 10 == dispatchLogVO.getTaskInstanceState()){//执行中
                dispatchLogVO.setTaskInstanceState(2);
            }else{//失败
                dispatchLogVO.setTaskInstanceState(1);
            }
            dispatchLogVO.setTaskInstanceExecDuration(DateUtils.differSec(dispatchLogVO.getStartTime(), dispatchLogVO.getEndTime()));
        }
        return BusinessResult.success(logVOList);
    }
}
