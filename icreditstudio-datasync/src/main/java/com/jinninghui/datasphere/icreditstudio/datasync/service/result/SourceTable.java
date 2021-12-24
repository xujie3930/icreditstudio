package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.Data;

@Data
public class SourceTable {

    /**
     * 数据源 库名称
     */
    private String database;
    /**
     * 数据源ID
     */
    private String datasourceId;
    /**
     * 数据源表名称
     */
    private String tableName;

}
