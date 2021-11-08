package com.jinninghui.datasphere.icreditstudio.metadata.service.param;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class StatementField {
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private String fieldType;
    /**
     * 长度
     */
    private int length;
    /**
     * 备注
     */
    private String comment;
}
