package com.jinninghui.datasphere.icreditstudio.datasync.container.widetable.outside;

/**
 * @author Peng
 */
public abstract class AbstractOutsideWideTableHandler implements OutsideGenerateWideTable {
    public AbstractOutsideWideTableHandler() {
        register();
    }

    @Override
    public void register() {
        OutsideGenerateWideTableContainer.getInstance().put(this.getDialect(), this);
    }
}
