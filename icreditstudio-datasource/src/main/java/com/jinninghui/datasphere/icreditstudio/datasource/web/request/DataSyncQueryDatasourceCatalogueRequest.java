package com.jinninghui.datasphere.icreditstudio.datasource.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Peng
 */
@Data
public class DataSyncQueryDatasourceCatalogueRequest {
    /**
     * 工作空间ID
     */
    @NotBlank(message = "")
    private String workspaceId;
    /**
     * 数据源名称
     */
    private String tableName;
    /**
     * 数据源类型【0:】
     */
    @NotNull(message = "")
    private Integer sourceType;
}
