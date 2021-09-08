package com.jinninghui.datasphere.icreditstudio.datasync.service.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Peng
 */
@Data
public class TaskParamSaveParam {
    /**
     * 任务ID
     */
    @NotBlank(message = "60000016")
    private String taskId;
    /**
     * 最大并发数
     */
    private Integer maxThread;
    /**
     * 同步速率【0：限流，1：不限流】
     */
    private Integer syncRate;
    /**
     * 限流速率 XXX条/s
     */
    private Integer limitRate;
    /**
     * 调度类型【0：周期，1：手动】
     */
    private Integer scheduleType;
    /**
     * cron表达式
     */
    private String cron;
}
