package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import com.jinninghui.datasphere.icreditstudio.datasync.service.DataxReader;
import com.jinninghui.datasphere.icreditstudio.datasync.service.DataxWriter;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Peng
 */
@Data
public final class DataxJsonEntity {

    public static Map<String, Object> buildDataxJson(DataxReader reader, DataxWriter writer, Map<String, Object> setting) {
        Map<String, Object> dataxMap = new HashMap<>();
        Map<String, Object> job = new HashMap<>();
        List<Map<String, Object>> contents = new ArrayList<>();
        Map<String, Object> content = new HashMap<>();
        content.put("reader", reader);
        content.put("writer", writer);
        contents.add(content);
        job.put("content", contents);
        job.put("setting", setting);
        dataxMap.put("job", job);
        return dataxMap;
    }
}
