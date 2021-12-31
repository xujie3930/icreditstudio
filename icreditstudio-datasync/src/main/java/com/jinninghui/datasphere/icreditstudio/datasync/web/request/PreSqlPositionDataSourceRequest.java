package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class PreSqlPositionDataSourceRequest {
    /**
     * 【0：外部数据库，1：本地文件，2：区块链数据】
     */
    private Integer sourceType;
    /**
     * 查询语句
     */
    private String sql;
    /**
     * 工作空间ID
     */
    private String workspaceId;
}
