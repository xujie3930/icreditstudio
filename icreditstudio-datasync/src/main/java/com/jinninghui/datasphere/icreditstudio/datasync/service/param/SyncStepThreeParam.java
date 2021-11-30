package com.jinninghui.datasphere.icreditstudio.datasync.service.param;

import com.jinninghui.datasphere.icreditstudio.datasync.web.request.CronParam;
import lombok.Data;

/**
 * @author Peng
 */
@Data
public class SyncStepThreeParam {
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 最大并发数
     */
    private Integer maxThread;
    /**
     * 同步速率【0：限流，1：不限流】
     */
    private Integer syncRate;
    /**
     * 限流速率 XXX条/s
     */
    private Integer limitRate;
    /**
     * 调度类型【0：周期，1：手动】
     */
    private Integer scheduleType;
    /**
     * 同步方式【0：增量同步，1：全量同步】
     */
    private Integer syncMode;
    /**
     * 定时参数
     */
    private CronParam cronParam;
}
