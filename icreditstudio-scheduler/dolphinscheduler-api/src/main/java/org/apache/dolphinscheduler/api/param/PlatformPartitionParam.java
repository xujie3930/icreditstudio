package org.apache.dolphinscheduler.api.param;

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
    /**
     * 时间T+n
     */
    private Integer n;
}
