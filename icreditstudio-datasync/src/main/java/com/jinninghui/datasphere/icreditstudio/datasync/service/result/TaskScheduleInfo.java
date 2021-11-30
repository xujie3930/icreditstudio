package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import com.jinninghui.datasphere.icreditstudio.datasync.enums.SyncRateEnum;
import com.jinninghui.datasphere.icreditstudio.datasync.web.request.CronParam;
import lombok.Data;

/**
 * @author Peng
 */
@Data
public class TaskScheduleInfo {
    /**
     * 最大并发数
     */
    private Integer maxThread;
    /**
     * 同步速率【0：限流，1：不限流】
     */
    private Integer syncRate;
    /**
     * 调度类型【0：周期执行，1：手动执行】
     */
    private Integer scheduleType;

    private Integer limitRate;
    /**
     * cron表达式
     */
    private String cron;

    private CronParam cronParam;

    public Integer getSyncRate() {
        if (syncRate == null) {
            return 1;
        }
        return syncRate;
    }

    public boolean isLimit() {
        return getSyncRate().equals(SyncRateEnum.LIMIT);
    }
}
