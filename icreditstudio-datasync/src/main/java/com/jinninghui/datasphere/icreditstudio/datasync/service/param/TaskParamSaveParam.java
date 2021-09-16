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
     * 同步方式【0：增量同步，1：全量同步】
     */
    private Integer syncMode;
    /**
     * cron表达式
     */
    private String cron;
    /**
     * 任务状态【0：启用，1：草稿，2：停用】
     */
    private Integer taskStatus;
}
