package com.jinninghui.datasphere.icreditstudio.metadata.web.request;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class WorkspaceTableSavaRequest {
    /**
     * 用户Id
     */
    private String userId;
    /**
     * Id，
     */
    private String id;
    /**
     * 工作空间ID
     */
    private String workspaceId;
    /**
     * 数据库名
     */
    private String databaseName;
    /**
     * 表名称
     */
    private String tableName;
}
