package com.jinninghui.datasphere.icreditstudio.datasource.service.result;

import lombok.Data;

import java.util.List;

/**
 * @author xujie
 * @description 返回数据源表结构
 * @create 2021-11-09 17:18
 **/
@Data
public class DatasourceStructureResult {
    private Integer tableCount;
    private List<String> tableOptions;
    private List<ColumnListResult> columnList;
}
