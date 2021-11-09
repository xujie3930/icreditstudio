package com.jinninghui.datasphere.icreditstudio.datasync.container.vo;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class QueryField {

    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 来源表
     */
    private String sourceTable;
    /**
     * 来源库
     */
    private String databaseName;
}
