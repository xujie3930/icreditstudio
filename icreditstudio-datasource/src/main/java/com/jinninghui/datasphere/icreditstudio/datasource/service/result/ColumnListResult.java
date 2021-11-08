package com.jinninghui.datasphere.icreditstudio.datasource.service.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xujie
 * @description 返回数据源表结构
 * @create 2021-11-05 11:42
 **/
@Data
public class ColumnListResult {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 来源库
     */
    private List<Object> fieldType;

    private String fieldChineseName;
}
