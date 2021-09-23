package com.jinninghui.datasphere.icreditstudio.datasync.service.param;

import com.jinninghui.datasphere.icreditstudio.datasync.web.request.DataSyncSaveRequest;
import lombok.Data;

/**
 * @author Peng
 */
@Data
public class DataSyncSaveParam extends DataSyncSaveRequest {
    /**
     * 任务状态【0：启用，1：草稿，2：停用】
     */
    private Integer taskStatus;
    /**
     * 任务执行状态【0：成功,1：失败，2：执行中】
     */
    private Integer execStatus;
}
