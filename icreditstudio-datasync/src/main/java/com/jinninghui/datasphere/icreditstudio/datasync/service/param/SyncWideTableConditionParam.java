package com.jinninghui.datasphere.icreditstudio.datasync.service.param;

import lombok.Builder;
import lombok.Data;

/**
 * @author Peng
 */
@Data
@Builder
public class SyncWideTableConditionParam {
    private String taskId;
    private Integer version;
}
