package com.jinninghui.datasphere.icreditstudio.datasource.service.factory.pojo;

import lombok.Data;

/**
 * @author xujie
 * @description 表同步信息
 * @create 2021-09-01 11:43
 **/
@Data
public class ColumnSyncInfo {
    private String field;//字段
    private String type;//字段类型
    private String remark;//字段描述
}
