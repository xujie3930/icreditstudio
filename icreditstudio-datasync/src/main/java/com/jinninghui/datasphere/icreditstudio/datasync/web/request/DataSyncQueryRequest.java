package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

/**
 * @author Peng
 */
@Data
public class DataSyncQueryRequest extends BusinessBasePageForm {
    /**
     * 工作空间ID
     */
    private String workspaceId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务状态【0：草稿，1：启用，2：停用】
     */
    private Integer taskStatus;
    /**
     * 执行状态【1：成功，2：失败，3：执行中】
     */
    private Integer execStatus;
}
