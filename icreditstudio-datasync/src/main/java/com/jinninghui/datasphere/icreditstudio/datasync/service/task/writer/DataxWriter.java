package com.jinninghui.datasphere.icreditstudio.datasync.service.task.writer;

import java.util.Map;

/**
 * @author Peng
 */
public interface DataxWriter {

    /**
     * datax writer插件参数
     * @return
     */
    Map<String, Object> getWriterEntity();
}
