package com.jinninghui.datasphere.icreditstudio.datasource.web.request;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class DataSourcesQueryRequest {
    /**
     * 数据源名称
     */
    private String databaseName;
    /**
     * 数据源ID
     */
    private String datasourceId;
}
