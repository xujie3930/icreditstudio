package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class SyncTaskInfo {
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务状态【0：启用，1：草稿，2：停用】
     */
    private Integer taskStatus;
    /**
     * 任务创建方式【0：可视化，1：SQL】
     */
    private Integer taskBuildMode;
    /**
     * 执行方式【0：手动执行，1：周期执行】
     */
    private Integer execMode;
    /**
     * 同步模式【0:增量同步，1：全量同步】
     */
    private Integer syncMode;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 近一次执行时间
     */
    private Long lastScheduleTime;
    /**
     * 执行状态【0：成功，1：失败，2：执行中】
     */
    private Integer execStatus;
}
