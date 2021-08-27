package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class TaskBuildInfo {
    /**
     * 目标库名
     */
    private String targetSource;
    /**
     * 宽表名称
     */
    private String wideTableName;
    /**
     * 分区字段
     */
    private String partition;
    /**
     * 【0：外部数据库，1：本地文件，2：区块链数据】
     */
    private Integer sourceType;
    /**
     * 源库ID
     */
    private String datasourceId;
    /**
     * 关联关系
     */
    private List<FileAssociated> view;
    /**
     * 宽表列表
     */
    private List<WideTableFieldInfo> fieldInfos;
}
