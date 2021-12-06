package com.jinninghui.datasphere.icreditstudio.datasync.service.task.reader;

import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractMapContainer;

/**
 * @author Peng
 */
public class DataxReaderContainer extends AbstractMapContainer<String, DataxReader> {

    private static DataxReaderContainer instance = new DataxReaderContainer();

    private DataxReaderContainer() {
    }

    public static DataxReaderContainer getInstance() {
        return instance;
    }

    public static DataxReader get(String dialectKey) {
        return instance.find(dialectKey);
    }
}
