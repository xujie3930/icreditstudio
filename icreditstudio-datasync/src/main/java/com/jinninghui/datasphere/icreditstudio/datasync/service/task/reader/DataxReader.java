package com.jinninghui.datasphere.icreditstudio.datasync.service.task.reader;

import java.util.Map;

/**
 * @author Peng
 */
public interface DataxReader {

    /**
     * datax reader插件参数
     *
     * @return
     */
    Map<String, Object> getReaderEntity();
}
