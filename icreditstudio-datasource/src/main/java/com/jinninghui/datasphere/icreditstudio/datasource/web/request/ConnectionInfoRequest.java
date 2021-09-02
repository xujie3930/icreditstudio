package com.jinninghui.datasphere.icreditstudio.datasource.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Peng
 */
@Data
public class ConnectionInfoRequest {
    private String workspaceId;
    @NotBlank(message = "")
    private String datasourceId;
}
