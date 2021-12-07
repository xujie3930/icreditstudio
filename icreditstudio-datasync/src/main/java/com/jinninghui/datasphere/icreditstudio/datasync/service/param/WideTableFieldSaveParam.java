package com.jinninghui.datasphere.icreditstudio.datasync.service.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Peng
 */
@Data
public class WideTableFieldSaveParam {
    /**
     * 主键ID
     */
    private String id;

    private String userId;
    /**
     * 宽表ID
     */
    @NotBlank(message = "60000019")
    private String wideTableId;
    /**
     * 排序字段
     */
    private Integer sort;
    /**
     * 字段名称
     */
    private String name;
    /**
     * 字段类型
     */
    private String type;
    /**
     * 所属表
     */
    private String sourceTable;
    /**
     * 字段中文名
     */
    private String chineseName;
    /**
     * 关联字典key
     */
    private String dictKey;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 备注
     */
    private String remark;
    /**
     * 数据库
     */
    private String databaseName;
}
