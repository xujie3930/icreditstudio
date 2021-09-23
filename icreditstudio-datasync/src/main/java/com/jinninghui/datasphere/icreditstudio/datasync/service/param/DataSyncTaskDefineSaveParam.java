package com.jinninghui.datasphere.icreditstudio.datasync.service.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Peng
 */
@Data
public class DataSyncTaskDefineSaveParam {
    /**
     * 工作空间id
     */
    @NotBlank(message = "60000000")
    private String workspaceId;
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 任务名称
     */
    @NotBlank(message = "60000009")
    private String taskName;
    /**
     * 【0：未启用，1：启用】
     */
    @Range(max = 1, message = "")
    @NotNull(message = "60000010")
    private Integer enable;
    /**
     * 【0：可视化，1：sql】
     */
    @NotNull(message = "60000011")
    private Integer createMode;
    /**
     * 任务描述
     */
    @Length(max = 255, message = "60000012")
    private String taskDescribe;
    /**
     * 任务状态【0：启用，1：草稿，2：停用】
     */
    private Integer taskStatus;
    /**
     * 任务状态【0：成功，1：失败，2：执行中】
     */
    private Integer execStatus;
}
