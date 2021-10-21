package org.apache.dolphinscheduler.api.param;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class ChannelControlParam {
    /**
     * 最大并发数
     */
    private int maxConcurrent;
    /**
     * 是否限流
     */
    private boolean isLimit;
    /**
     * 每秒条数
     */
    private int size;
}
