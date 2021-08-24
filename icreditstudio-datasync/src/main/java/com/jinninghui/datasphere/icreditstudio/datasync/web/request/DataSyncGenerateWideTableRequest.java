package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class DataSyncGenerateWideTableRequest {

    /**
     * 工作空间ID
     */
    private String workspaceId;
    /**
     * 目标库ID
     */
    private String targetSourceId;
    /**
     * 宽表名称
     */
    private String wideTableName;
    /**
     * 分区字段
     */
    private String partition;
    /**
     * 资源类型
     */
    private Integer sourceType;
    /**
     * 关联关系
     */
    private List<FileAssociated> view;
}
