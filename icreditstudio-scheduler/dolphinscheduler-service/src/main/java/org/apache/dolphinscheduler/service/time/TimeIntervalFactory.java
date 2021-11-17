package org.apache.dolphinscheduler.service.time;

import org.apache.dolphinscheduler.service.quartz.PlatformPartitionParam;

import java.util.function.Predicate;

/**
 * @author Peng
 */
public interface TimeIntervalFactory {
    /**
     * 取得增量时间
     *
     * @param condition
     * @param predicate
     * @return
     */
    SyncTimeInterval getSyncTimeInterval(PlatformPartitionParam condition, Predicate<SyncTimeInterval> predicate);
}
