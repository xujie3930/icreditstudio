package org.apache.dolphinscheduler.service.time;

import org.apache.dolphinscheduler.service.AbstractMapContainer;

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

