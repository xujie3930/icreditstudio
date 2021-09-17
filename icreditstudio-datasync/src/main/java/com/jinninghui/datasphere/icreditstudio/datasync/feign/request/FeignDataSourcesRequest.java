package com.jinninghui.datasphere.icreditstudio.datasync.feign.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Peng
 */
@Data
@NoArgsConstructor
public class FeignDataSourcesRequest {

    private String databaseName;

    private String datasourceId;
}
