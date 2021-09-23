package com.jinninghui.datasphere.icreditstudio.datasync.service.time;

import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractMapContainer;

/**
 * @author Peng
 */
public final class TimeIntervalContainer extends AbstractMapContainer<String, ITimeInterval> {
    private static final TimeIntervalContainer instance = new TimeIntervalContainer();

    private TimeIntervalContainer() {
    }

    public static TimeIntervalContainer getInstance() {
        return instance;
    }
}

