package org.apache.dolphinscheduler.api.request;

import lombok.Data;
import org.apache.dolphinscheduler.common.enums.*;
import org.apache.dolphinscheduler.dao.entity.User;

/**
 * @author Peng
 */
@Data
public class ExecPlatformProcessDefinitionRequest {
    /**
     * 访问用户
     */
    private User accessUser;
    /**
     * 流程定义ID
     */
    String processDefinitionId;
    /**
     * 时间
     */
    String cronTime = "";
    /**
     * 指令类型
     */
    CommandType commandType = null;
    /**
     * 失败策略
     */
    FailureStrategy failureStrategy = FailureStrategy.CONTINUE;
    /**
     * 开始节点
     */
    String startNodeList = "";
    TaskDependType taskDependType = TaskDependType.TASK_POST;
    WarningType warningType = WarningType.NONE;
    /**
     * 警告组
     */
    String warningGroupId;
    RunMode runMode = RunMode.RUN_MODE_SERIAL;
    Priority processInstancePriority = Priority.MEDIUM;
    /**
     * 工作组
     */
    String workerGroup;
    Integer timeout = null;
}
