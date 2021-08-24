package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class TaskDefineInfo {
    private String taskId;
    private String taskName;
    private Integer enable;
    private Integer buildMode;
    private String taskDescription;
}
