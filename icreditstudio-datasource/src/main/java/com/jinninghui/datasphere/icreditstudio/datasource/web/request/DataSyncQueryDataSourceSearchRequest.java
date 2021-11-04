package com.jinninghui.datasphere.icreditstudio.datasource.web.request;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class DataSyncQueryDataSourceSearchRequest {

    private String workspaceId;

    private String tableName;

    private Integer sourceType;
}
