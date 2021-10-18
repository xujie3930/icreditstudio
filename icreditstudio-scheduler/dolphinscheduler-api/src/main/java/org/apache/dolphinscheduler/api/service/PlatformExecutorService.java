package org.apache.dolphinscheduler.api.service;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.param.ExecPlatformProcessDefinitionParam;
import org.apache.dolphinscheduler.common.enums.*;
import org.apache.dolphinscheduler.dao.entity.ProcessDefinition;

import java.text.ParseException;
import java.util.Map;

/**
 * @author Peng
 */
public interface PlatformExecutorService {

    /**
     * 执行流程定义
     *
     * @param param
     * @return
     */
    BusinessResult<Boolean> execProcessInstance(ExecPlatformProcessDefinitionParam param) throws ParseException;

    /**
     * check whether the process definition can be executed
     *
     * @param processDefinition process definition
     * @param processDefineId process definition code
     * @return check result code
     */
    Map<String, Object> checkProcessDefinitionValid(ProcessDefinition processDefinition, String processDefineId);

    int createCommand(CommandType commandType, String processDefineId,
                      TaskDependType nodeDep, FailureStrategy failureStrategy,
                      String startNodeList, String schedule, WarningType warningType,
                      String executorId, String warningGroupId,
                      RunMode runMode, Priority processInstancePriority, String workerGroup);
}
