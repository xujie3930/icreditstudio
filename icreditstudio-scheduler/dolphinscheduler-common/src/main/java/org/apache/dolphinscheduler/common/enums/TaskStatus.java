/**
 * @author xujie
 * @description 调度任务状态
 * @create 2021-10-11 10:34
 **/

package org.apache.dolphinscheduler.common.enums;

import java.util.HashMap;

/**
 * running status for workflow and task nodes
 */
public enum TaskStatus {


    SUCCESS(0, "运行成功"),
    FAILURE(1, "运行失败"),
    RUNNING_EXECUTION(2, "运行中"),
    WAITING_THREAD(3, "等待中");

    TaskStatus(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    private final int code;
    private final String descp;

    private static HashMap<Integer, TaskStatus> EXECUTION_STATUS_MAP = new HashMap<>();

    static {
        for (TaskStatus executionStatus : TaskStatus.values()) {
            EXECUTION_STATUS_MAP.put(executionStatus.code, executionStatus);
        }
    }


    public int getCode() {
        return code;
    }

    public String getDescp() {
        return descp;
    }

    public static TaskStatus of(int status) {
        if (EXECUTION_STATUS_MAP.containsKey(status)) {
            return EXECUTION_STATUS_MAP.get(status);
        }
        throw new IllegalArgumentException("invalid status : " + status);
    }
}
