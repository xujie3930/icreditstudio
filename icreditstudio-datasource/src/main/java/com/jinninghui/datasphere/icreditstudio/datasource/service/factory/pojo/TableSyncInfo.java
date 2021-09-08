package com.jinninghui.datasphere.icreditstudio.datasource.service.factory.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xujie
 * @description 表同步信息
 * @create 2021-09-01 11:43
 **/
@Data
public class TableSyncInfo {
    private String tableName;
    private List<ColumnSyncInfo> columnList = new ArrayList<>();
}
