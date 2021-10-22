package org.apache.dolphinscheduler.api.param;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Peng
 */
@Data
public class TaskNodeStruct {
    private String type;
    private String id;
    private String name;
    private NodeParam params;
    private String description;
    private TimeOutParam timeout;
    private String runFlag;
    private ConditionResult conditionResult;
    private Map<String, String> dependence;
    private String maxRetryTimes;
    private String retryInterval;
    private String taskInstancePriority;
    private String workerGroup;
    private List<TaskNodeStruct> preTasks;
}
