package org.apache.dolphinscheduler.api.service.result;

import lombok.Data;

/**
 * @author xujie
 * @description 任务72小时概况
 * @create 2021-10-09 18:34
 **/
@Data
public class TaskRoughResult {
    /**
     * 总调度任务数
     */
    private Long taskCount;
    /**
     * 执行失败实例
     */
    private Long failCount;
    /**
     * 新增数据量条数
     */
    private Long newlyLine = 0L;
    /**
     * 新增总数据量，单位KB
     */
    private double newlyDataSize = 0.00;
    /**
     * 实时任务记录速率，暂时为0
     */
    private double currentSpeed = 0.00;
}
