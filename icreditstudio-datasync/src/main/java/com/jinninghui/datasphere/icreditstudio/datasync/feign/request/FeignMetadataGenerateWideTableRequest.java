package com.jinninghui.datasphere.icreditstudio.datasync.feign.request;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class FeignMetadataGenerateWideTableRequest {

    /**
     * 数据库名
     */
    private String databaseName;
    /**
     * 宽表名称
     */
    private String wideTableName;
    /**
     * 宽表字段
     */
    private List<StatementField> fieldList;
    /**
     * 分隔符
     */
    private String delimiter;
    /**
     * 分区字段
     */
    private String partition;
}
