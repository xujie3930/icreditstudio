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

    private Boolean inc;
    /**
     * 时间T+n
     */
    private Integer n;
    /**
     * 数据源方言
     */
    private String dialect;

    /**
     * 周期首次是否同步以前数据
     */
    private Boolean firstFull;
}
