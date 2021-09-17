package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class WideTableFieldInfo {
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 来源表
     */
    private String sourceTable;
    /**
     * 字段中文名称
     */
    private String fieldChineseName;
    /**
     * 关联字典表
     */
    private String associateDict;
    /**
     * 备注
     */
    private String remark;
    /**
     * 来源库
     */
    private String databaseName;
}
