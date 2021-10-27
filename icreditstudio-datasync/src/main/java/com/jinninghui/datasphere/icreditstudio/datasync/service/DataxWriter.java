package com.jinninghui.datasphere.icreditstudio.datasync.service;

import com.jinninghui.datasphere.icreditstudio.datasync.service.result.Column;

import java.util.List;
import java.util.Map;

/**
 * @author Peng
 */
public interface DataxWriter {

    Map<String,Object> getWriterEntity(List<Column> columns);
}
