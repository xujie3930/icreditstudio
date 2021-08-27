package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class TaskScheduleInfo {
    /**
     * 最大并发数
     */
    private Integer maxConcurrent;
    /**
     * 同步速率【0：限流，1：不限流】
     */
    private Integer syncRate;
    /**
     * 调度类型【0：周期执行，1：手动执行】
     */
    private Integer scheduleType;

    private Integer limitRate;
    /**
     * 同步周期
     */
    private Long syncCycle;
    /**
     * cron表达式
     */
    private String cron;
}
