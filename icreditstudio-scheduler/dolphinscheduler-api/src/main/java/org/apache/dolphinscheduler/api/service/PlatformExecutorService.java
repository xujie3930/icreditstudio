package org.apache.dolphinscheduler.api.service;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.param.ExecPlatformProcessDefinitionParam;
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
     * @throws ParseException
     */
    BusinessResult<Boolean> execProcessInstance(ExecPlatformProcessDefinitionParam param);

    /**
     * 上线周期任务
     * @param processDefinitionId
     * @return
     */
    BusinessResult<Boolean> execCycle(String processDefinitionId);

    /**
     * check whether the process definition can be executed
     *
     * @param processDefinition process definition
     * @param processDefineId   process definition code
     * @return check result code
     */
    Map<String, Object> checkProcessDefinitionValid(ProcessDefinition processDefinition, String processDefineId);

    String execSyncTask(String processDefinitionId);

    String stopSyncTask(String processDefinitionId);

    String deleteSyncTask(String processDefinitionId);

    String enableSyncTask(String processDefinitionId);

    String ceaseSyncTask(String processDefinitionId);
}
