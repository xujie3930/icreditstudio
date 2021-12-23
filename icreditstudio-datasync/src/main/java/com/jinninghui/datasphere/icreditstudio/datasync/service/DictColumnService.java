package com.jinninghui.datasphere.icreditstudio.datasync.service;

import com.jinninghui.datasphere.icreditstudio.datasync.entity.DictColumnEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DictColumnSaveParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictColumnResult;

import java.util.Collection;
import java.util.List;

public interface DictColumnService {


    void delBatchByDictId(Integer code, String id);

    void saveBatch(String dictId, List<DictColumnSaveParam> saveParams);

    List<DictColumnResult> getColumnListByDictId(String dictId);

    void truthDelBatchByDictId(String id);

    List<DictColumnEntity> getDictInfoByIds(Collection<String> keys);
}
