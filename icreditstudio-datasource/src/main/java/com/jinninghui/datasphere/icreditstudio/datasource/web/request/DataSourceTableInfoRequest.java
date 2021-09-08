package com.jinninghui.datasphere.icreditstudio.datasource.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Peng
 */
@Data
public class DataSourceTableInfoRequest {
    /**
     * 数据源ID
     */
    @NotBlank(message = "70000002")
    private String datasourceId;
    /**
     * 表名称
     */
    @NotBlank(message = "70000001")
    private String tableName;
}
