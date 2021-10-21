package com.jinninghui.datasphere.icreditstudio.datasync.feign.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Peng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
