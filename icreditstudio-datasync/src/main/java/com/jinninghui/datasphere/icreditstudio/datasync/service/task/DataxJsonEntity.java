package com.jinninghui.datasphere.icreditstudio.datasync.service.task;

import com.jinninghui.datasphere.icreditstudio.datasync.service.task.reader.DataxReader;
import com.jinninghui.datasphere.icreditstudio.datasync.service.task.writer.DataxWriter;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Peng
 */
@Data
@Builder
public final class DataxJsonEntity {

    private DataxReader reader;
    private DataxWriter writer;
    private Map<String, Object> setting;
    private Map<String, Object> core;

    public Map<String, Object> buildDataxJson() {
        Map<String, Object> job = new HashMap<>(2);
        List<Map<String, Object>> contents = new ArrayList<>();
        Map<String, Object> content = new HashMap<>();
        content.put("reader", reader.getReaderEntity());
        content.put("writer", writer.getWriterEntity());
        contents.add(content);
        job.put("content", contents);
        job.put("setting", setting);
        job.put("core", core);
        return job;
    }
}
