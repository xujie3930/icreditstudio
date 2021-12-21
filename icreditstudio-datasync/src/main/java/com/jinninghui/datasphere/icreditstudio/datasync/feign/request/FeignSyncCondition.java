package com.jinninghui.datasphere.icreditstudio.datasync.feign.request;

import com.jinninghui.datasphere.icreditstudio.datasync.service.result.SyncCondition;
import lombok.Data;

/**
 * @author Peng
 */
@Data
public class FeignSyncCondition extends SyncCondition {

    private Boolean firstFull;
}
