package com.jinninghui.datasphere.icreditstudio.datasync.feign.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Peng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeignConnectionInfoRequest {
    private String workspaceId;
    private String datasourceId;
}
