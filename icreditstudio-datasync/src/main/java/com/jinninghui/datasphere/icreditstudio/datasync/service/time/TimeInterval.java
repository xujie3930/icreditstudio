package com.jinninghui.datasphere.icreditstudio.datasync.service.time;

import com.jinninghui.datasphere.icreditstudio.datasync.enums.PartitionTypeEnum;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.SyncCondition;

import java.util.Date;
import java.util.function.Predicate;

/**
 * @author Peng
 */
public class TimeInterval implements TimeIntervalFactory {
    @Override
    public SyncTimeInterval getSyncTimeInterval(SyncCondition condition, Predicate<SyncTimeInterval> predicate) {
        String incrementalField = condition.getIncrementalField();
        String partition = condition.getPartition();
        Integer n = condition.getN();
        SyncTimeInterval interval = new SyncTimeInterval();
        interval.setIncrementalField(incrementalField);

        String name = PartitionTypeEnum.find(partition).getName();
        ITimeInterval iTimeInterval = TimeIntervalContainer.getInstance().find(name);
        Date date = iTimeInterval.beforeTime(new Date(), n);
        interval.setStartTime(iTimeInterval.getBeginTime(date));
        interval.setEndTime(iTimeInterval.getEndTime(date));
        interval.setTimeFormat(iTimeInterval.getFormat(date));

        interval.setExec(predicate.test(interval));
        return interval;
    }
}
