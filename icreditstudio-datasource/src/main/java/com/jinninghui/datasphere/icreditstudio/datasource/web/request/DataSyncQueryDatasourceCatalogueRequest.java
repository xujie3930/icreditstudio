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
    @NotBlank(message = "70000005")
    private String workspaceId;
    /**
     * 数据源名称
     */
    private String tableName;
    /**
     * 数据源类型【0:外部数据源，1：本地数据源，2：区块链】
     */
    @NotNull(message = "70000006")
    private Integer sourceType;
}
