package com.jinninghui.datasphere.icreditstudio.datasync.container.impl;

import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractMapContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.DataSyncQuery;

/**
 * @author Peng
 */
public class DataSyncQueryContainer extends AbstractMapContainer<String, DataSyncQuery> {

    private static DataSyncQueryContainer instance = new DataSyncQueryContainer();

    private DataSyncQueryContainer() {
    }

    public static DataSyncQueryContainer getInstance() {
        return instance;
    }

    public static DataSyncQuery matching(String dialect) {
        return getInstance().find(dialect);
    }
}
