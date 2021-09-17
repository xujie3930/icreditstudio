package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import com.jinninghui.datasphere.icreditstudio.datasync.web.request.DataSyncGenerateWideTableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class WideTable {
    /**
     * 宽表名称
     */
    private String tableName;
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
     * 不同主机的同名数据库
     */
    private List<DataSyncGenerateWideTableRequest.DatabaseInfo> sameNameDataBase;
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
