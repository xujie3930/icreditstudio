package com.jinninghui.datasphere.icreditstudio.datasync.container;

import com.jinninghui.datasphere.icreditstudio.datasync.container.impl.DataSyncQueryContainer;

/**
 * @author Peng
 */
public abstract class AbstractDataSyncQuery implements DataSyncQuery {

    public AbstractDataSyncQuery() {
        register();
    }

    @Override
    public void register() {
        DataSyncQueryContainer.getInstance().put(this.getDialect(), this);
    }
}
