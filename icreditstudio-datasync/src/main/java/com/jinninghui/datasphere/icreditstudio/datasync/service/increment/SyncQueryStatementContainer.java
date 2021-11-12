package com.jinninghui.datasphere.icreditstudio.datasync.service.increment;

import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractMapContainer;

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
