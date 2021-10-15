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
    String cronTime;
    /**
     * 指令类型
     */
    CommandType commandType;
    /**
     * 失败策略
     */
    FailureStrategy failureStrategy;
    /**
     * 节点
     */
    String startNodeList;
    TaskDependType taskDependType;
    WarningType warningType;
    String warningGroupId;
    RunMode runMode;
    Priority processInstancePriority;
    String workerGroup;
    Integer timeout;
}
