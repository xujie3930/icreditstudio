package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class SyncCondition {
    private String incrementalField;
    private String partition;
    private Integer n;
}
