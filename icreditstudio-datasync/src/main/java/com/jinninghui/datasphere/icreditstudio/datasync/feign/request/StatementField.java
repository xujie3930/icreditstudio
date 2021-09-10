package com.jinninghui.datasphere.icreditstudio.datasync.feign.request;

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
}
