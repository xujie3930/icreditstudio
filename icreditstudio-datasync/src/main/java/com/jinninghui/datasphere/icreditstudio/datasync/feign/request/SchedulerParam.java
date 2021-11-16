package com.jinninghui.datasphere.icreditstudio.datasync.feign.request;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Peng
 */
@Data
@AllArgsConstructor
public class SchedulerParam {
    /**
     * 调度类型
     */
    private Integer schedulerType;
    /**
     * 定时表达试
     */
    private String cron;
}
