package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import com.jinninghui.datasphere.icreditstudio.datasync.web.request.WideTableFieldInfo;
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
    private List<String> partitions;
}
