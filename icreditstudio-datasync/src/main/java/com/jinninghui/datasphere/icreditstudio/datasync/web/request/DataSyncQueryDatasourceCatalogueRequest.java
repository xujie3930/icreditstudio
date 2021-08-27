package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class DataSyncQueryDatasourceCatalogueRequest {
    /**
     * 工作空间ID
     */
    private String workspaceId;
    /**
     * 数据源名称
     */
    private String sourceName;
    /**
     * 数据源类型
     */
    private Integer sourceType;
}
