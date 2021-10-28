package com.jinninghui.datasphere.icreditstudio.datasync.service.impl;

import com.google.common.collect.Maps;
import com.jinninghui.datasphere.icreditstudio.datasync.service.DataxWriter;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.Column;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Peng
 */
@Data
public class HdfsWriterEntity implements DataxWriter {

    @Override
    public Map<String, Object> getWriterEntity(List<Column> columns) {
        Map<String, Object> writer = Maps.newHashMap();
        writer.put("name", "hdfswriter");
        Map<String, Object> parameter = Maps.newHashMap();

        Map<String, Object> hadoopConfig = Maps.newHashMap();
        hadoopConfig.put("dfs.client.use.datanode.hostname", true);
        parameter.put("hadoopConfig", hadoopConfig);

        parameter.put("defaultFS", "hdfs://192.168.110.7:8020");
        parameter.put("fileType", "orc");
        parameter.put("path", "/usr/local/software/hive/warehouse/dfstest.db/test_addr/2021-10-64/");
        parameter.put("fileName", "test_addr");
        parameter.put("column", columns);
        parameter.put("writeMode", "append");
        parameter.put("fieldDelimiter", "\t");
        parameter.put("partition", "day");
        parameter.put("user", "root");
        parameter.put("password", "root");
        parameter.put("thriftUrl", "jdbc:hive2://192.168.110.7:10000/");
        parameter.put("compress", "NONE");
        writer.put("parameter", parameter);
        return writer;
    }
}
