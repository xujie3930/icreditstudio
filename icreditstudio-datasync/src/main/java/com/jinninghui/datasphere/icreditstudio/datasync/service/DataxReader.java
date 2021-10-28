package com.jinninghui.datasphere.icreditstudio.datasync.service;

import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictInfo;

import java.util.List;
import java.util.Map;

/**
 * @author Peng
 */
public interface DataxReader {

    Map<String,Object> getReaderEntity(Map<String, Object> needTransferColumns, List<DictInfo> transferDict);
}
