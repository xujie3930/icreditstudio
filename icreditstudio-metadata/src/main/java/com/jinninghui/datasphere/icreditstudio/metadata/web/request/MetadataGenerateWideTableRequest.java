package com.jinninghui.datasphere.icreditstudio.metadata.web.request;

import com.jinninghui.datasphere.icreditstudio.metadata.service.param.StatementField;
import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class MetadataGenerateWideTableRequest {

    /**
     * 工作空间
     */
    private String workspaceId;
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
