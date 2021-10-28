package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import com.jinninghui.datasphere.icreditstudio.datasync.service.DataxReader;
import com.jinninghui.datasphere.icreditstudio.datasync.service.DataxWriter;
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

    public Map<String, Object> buildDataxJson() {
        Map<String, Object> dataxMap = new HashMap<>(1);
        Map<String, Object> job = new HashMap<>(2);
        List<Map<String, Object>> contents = new ArrayList<>();
        Map<String, Object> content = new HashMap<>();
        content.put("reader", reader.getReaderEntity());
        content.put("writer", writer.getWriterEntity());
        contents.add(content);
        job.put("content", contents);
        job.put("setting", setting);
        dataxMap.put("job", job);
        return dataxMap;
    }
}
