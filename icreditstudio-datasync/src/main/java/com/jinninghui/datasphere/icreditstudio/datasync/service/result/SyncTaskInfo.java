package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class SyncTaskInfo {
    private String taskId;
    private String taskName;
    private Integer taskStatus;
    private Integer taskBuildMode;
    private Integer execMode;
    private Integer syncMode;
    private Long createTime;
    private Long lastScheduleTime;
    private Integer execStatus;
}
