package com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service;

import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.entity.DictColumnEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.result.DictColumnResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.param.DictColumnSaveParam;

import java.util.Collection;
import java.util.List;

public interface DictColumnService {


    void delBatchByDictId(Integer code, String id);

    void saveBatch(String dictId, List<DictColumnSaveParam> saveParams);

    List<DictColumnResult> getColumnListByDictId(String dictId);

    void truthDelBatchByDictId(String id);

    List<DictColumnEntity> getDictInfoByIds(Collection<String> keys);
}
