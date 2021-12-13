package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class WideTable {
    /**
     * 数据源ID
     */
    private String datasourceId;
    /**
     * 数据来源表方言
     */
    private String sourceDialect;
    /**
     * 字段列表
     */
    private List<WideTableFieldInfo> fields;
    /**
     * 分区字段列表
     */
    private List<WideTable.Select> partitions;
    /**
     * 增量字段
     */
    private List<WideTable.Select> incrementalFields;
    /**
     * 生成宽表sql
     */
    private String sql;

    @Data
    @AllArgsConstructor
    public static class Select {
        private String label;
        private String value;
    }
}
