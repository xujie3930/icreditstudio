package com.jinninghui.datasphere.icreditstudio.datasync.feign.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Peng
 */
@Data
@NoArgsConstructor
public class FeignDataSourcesRequest {

    /**
     * 资源类型
     */
    private Integer sourceType;
    /**
     * 数据库名称
     */
    private String databaseName;
    /**
     * 数据源ID
     */
    private String datasourceId;
}
