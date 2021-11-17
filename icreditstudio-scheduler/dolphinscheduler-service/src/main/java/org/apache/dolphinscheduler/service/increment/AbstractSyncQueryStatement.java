package org.apache.dolphinscheduler.service.increment;

/**
 * @author Peng
 */
public abstract class AbstractSyncQueryStatement implements SyncQueryStatement {

    public AbstractSyncQueryStatement() {
        register();
    }

    @Override
    public void register() {
        SyncQueryStatementContainer.getInstance().put(getDialect(), this);
    }
}
