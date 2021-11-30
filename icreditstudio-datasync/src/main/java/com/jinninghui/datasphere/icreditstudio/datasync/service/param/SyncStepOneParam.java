package com.jinninghui.datasphere.icreditstudio.datasync.service.param;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class SyncStepOneParam {
    /**
     * 工作空间ID
     */
    private String workspaceId;
    /**
     * userId
     */
    private String userId;
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 【0：启用，1：停用】
     */
    private Integer enable;
    /**
     * 【0：可视化，1：sql】
     */
    private Integer createMode;
    /**
     * 任务描述
     */
    private String taskDescribe;
    /**
     * 任务状态
     */
    private Integer taskStatus;
}
