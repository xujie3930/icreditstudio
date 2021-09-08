package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

/**
 * @author Peng
 */
@Data
public class DataSyncQueryRequest extends BusinessBasePageForm {
    /**
     * 工作空间ID
     */
    @NotBlank(message = "60000000")
    private String workspaceId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务状态【0：草稿，1：启用，2：停用,3:全部】
     */
    @Range(max = 3, message = "60000007")
    private Integer taskStatus;
    /**
     * 执行状态【0：成功，1：失败，2：执行中,3:全部】
     */
    @Range(max = 3, message = "60000008")
    private Integer execStatus;
}
