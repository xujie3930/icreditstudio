package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Peng
 */
@Data
public class DataSyncDetailRequest {
    @NotBlank(message = "60000016")
    private String taskId;
}
