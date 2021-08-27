package com.jinninghui.datasphere.icreditstudio.datasync.service.param;

import com.jinninghui.datasphere.icreditstudio.datasync.enums.ExecStatusEnum;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.TaskStatusEnum;
import lombok.Builder;
import lombok.Data;

/**
 * @author Peng
 */
@Data
@Builder
public class SyncTaskConditionParam {

    private String taskId;

    private String workspaceId;

    private String taskName;

    private TaskStatusEnum taskStatus;

    private ExecStatusEnum execStatus;
}
