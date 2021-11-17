package org.apache.dolphinscheduler.service.increment;

import org.apache.dolphinscheduler.service.AbstractMapContainer;

/**
 * @author Peng
 */
public class SyncQueryStatementContainer extends AbstractMapContainer<String, SyncQueryStatement> {
    private static SyncQueryStatementContainer instance = new SyncQueryStatementContainer();

    private SyncQueryStatementContainer() {
    }

    public static SyncQueryStatementContainer getInstance() {
        return instance;
    }

}
