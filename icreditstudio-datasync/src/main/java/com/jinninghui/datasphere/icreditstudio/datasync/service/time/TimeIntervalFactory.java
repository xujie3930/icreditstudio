package com.jinninghui.datasphere.icreditstudio.datasync.service.time;

import com.jinninghui.datasphere.icreditstudio.datasync.service.result.SyncCondition;

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
    SyncTimeInterval getSyncTimeInterval(SyncCondition condition, Predicate<SyncTimeInterval> predicate);
}
