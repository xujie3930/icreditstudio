package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class SyncCondition {
    /**
     * 增量字段
     */
    private String incrementalField;
    /**
     * 分区类型
     */
    private String partition;
    /**
     * 增量存储
     */
    private Boolean inc;

    public Boolean getInc() {
        if (inc == null) {
            return false;
        }
        return inc;
    }

    /**
     * 时间T+n
     */
    private Integer n;

    public Integer getN() {
        if (n == null || n <= 0) {
            return 1;
        }
        return n;
    }
}
