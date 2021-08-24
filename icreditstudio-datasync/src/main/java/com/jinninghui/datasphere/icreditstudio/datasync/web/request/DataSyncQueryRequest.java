package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class DataSyncQueryRequest {
    /**
     * 工作空间ID
     */
    private String workspaceId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务状态
     */
    private Integer taskStatus;
    /**
     * 执行状态
     */
    private Integer execStatus;
}
