package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class DataSyncExecRequest {
    private String taskId;
    private int execType;
}
