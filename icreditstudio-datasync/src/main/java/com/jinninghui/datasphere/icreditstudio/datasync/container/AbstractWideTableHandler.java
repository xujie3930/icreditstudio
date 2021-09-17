package com.jinninghui.datasphere.icreditstudio.datasync.container;

import com.jinninghui.datasphere.icreditstudio.datasync.container.impl.GenerateWideTableContainer;

/**
 * @author Peng
 */
public abstract class AbstractWideTableHandler implements GenerateWideTable {
    public AbstractWideTableHandler() {
        register();
    }

    @Override
    public void register() {
        GenerateWideTableContainer.getInstance().put(this.getDialect(), this);
    }
}
