package org.apache.dolphinscheduler.service.time;

/**
 * @author Peng
 */
public abstract class AbstractITimeInterval implements ITimeInterval {

    public AbstractITimeInterval() {
        register();
    }

    public final void register() {
        TimeIntervalContainer.getInstance().put(this.getKey().getName(), this);
    }
}
