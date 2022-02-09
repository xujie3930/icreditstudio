package com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.entity.DictEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.result.AssociatedDictInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.result.DictColumnResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.result.DictQueryResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.result.DictResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.param.AssociatedDictParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.param.DictQueryParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.param.DictSaveParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DictService {

    BusinessResult<Boolean> save(DictSaveParam param);

    BusinessResult<Boolean> del(String id);

    BusinessResult<DictResult> getInfoById(String id);

    BusinessResult<List<DictColumnResult>> lookInfo(String dictId);

    BusinessResult<Boolean> update(DictSaveParam param);

    BusinessResult<BusinessPageResult<DictQueryResult>> pageList(DictQueryParam param);

    BusinessResult<Boolean> importDict(MultipartFile file, String param);

    /**
     * 根据名称获得字典信息
     *
     * @param param
     * @return
     */
    BusinessResult<List<AssociatedDictInfoResult>> associatedDict(AssociatedDictParam param);

    /**
     * 根据ID查询字典信息
     *
     * @param id
     * @return
     */
    DictEntity getDictById(String id);
}
