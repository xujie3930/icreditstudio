package org.apache.dolphinscheduler.service.quartz;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class PlatformPartitionParam {
    /**
     * 增量字段
     */
    private String incrementalField;
    /**
     * 分区类型
     */
    private String partition;

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
