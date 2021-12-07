package com.jinninghui.datasphere.icreditstudio.datasync.service.task.reader;

/**
 * @author Peng
 */
public abstract class AbstractDataxReaderHandler implements DataxReader {

    public AbstractDataxReaderHandler() {
        register();
    }

    @Override
    public void register() {
        DataxReaderContainer.getInstance().put(this.getDialect(), this);
    }
}
