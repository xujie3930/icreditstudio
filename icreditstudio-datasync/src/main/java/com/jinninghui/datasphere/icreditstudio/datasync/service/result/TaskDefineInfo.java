package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class TaskDefineInfo {
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 启用状态【0：启用，1：草稿，2：停用】
     */
    private Integer enable;
    /**
     * 创建方式【0：可视化，1：SQL】
     */
    private Integer createMode;
    /**
     * 任务描述
     */
    private String taskDescribe;
}
