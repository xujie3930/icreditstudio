package com.jinninghui.datasphere.icreditstudio.datasync.service.task.writer.hdfs;

import com.google.common.collect.Maps;
import com.jinninghui.datasphere.icreditstudio.datasync.service.task.writer.DataxWriter;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Peng
 */
@Data
@AllArgsConstructor
public class HdfsWriterEntity implements DataxWriter {
    /**
     * hive表列信息
     */
    private List<Column> columns;
    /**
     * hdfswriter配置信息
     */
    private HdfsWriterConfigParam configParam;

    @Override
    public Map<String, Object> getWriterEntity() {
        Map<String, Object> writer = Maps.newHashMap();
        writer.put("name", "hdfswriter");
        Map<String, Object> parameter = Maps.newHashMap();

        Map<String, Object> hadoopConfig = Maps.newHashMap();
        hadoopConfig.put("dfs.client.use.datanode.hostname", true);
        parameter.put("hadoopConfig", hadoopConfig);

        parameter.put("defaultFS", configParam.getDefaultFs());
        parameter.put("fileType", configParam.getFileType());
        parameter.put("path", configParam.getPath());
        parameter.put("fileName", configParam.getFileName());
        parameter.put("column", columns);
        parameter.put("writeMode", configParam.getWriteMode());
        parameter.put("fieldDelimiter", configParam.getFieldDelimiter());
        parameter.put("partition", configParam.getPartition());
        parameter.put("user", configParam.getUser());
        parameter.put("password", configParam.getPassWord());
        parameter.put("thriftUrl", configParam.getThriftUrl());
        parameter.put("compress", configParam.getCompress());
        writer.put("parameter", parameter);
        return writer;
    }
}
