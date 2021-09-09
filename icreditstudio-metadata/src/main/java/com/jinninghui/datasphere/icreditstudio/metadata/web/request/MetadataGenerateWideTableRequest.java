package com.jinninghui.datasphere.icreditstudio.metadata.web.request;

import com.jinninghui.datasphere.icreditstudio.metadata.service.param.WideTableField;
import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class MetadataGenerateWideTableRequest {
    /**
     * 宽表名称
     */
    private String wideTableName;
    /**
     * 宽表字段
     */
    private List<WideTableField> fieldList;
    /**
     * 分隔符
     */
    private String delimiter;
    /**
     * 分区字段
     */
    private String partition;
}
