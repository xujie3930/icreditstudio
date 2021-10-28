package org.apache.dolphinscheduler.api.param;

import lombok.Data;
import org.apache.dolphinscheduler.api.enums.ScheduleType;

/**
 * @author Peng
 */
@Data
public class SchedulerParam {
    /**
     * 调度类型
     */
    private Integer schedulerType;

    public ScheduleType getSchedulerType() {
        return ScheduleType.find(schedulerType);
    }

    /**
     * 定时表达试
     */
    private String cron;
}
