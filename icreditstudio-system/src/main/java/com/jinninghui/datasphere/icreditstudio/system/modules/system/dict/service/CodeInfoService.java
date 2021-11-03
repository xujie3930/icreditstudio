package com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.entity.CodeInfoEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.param.*;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.result.AssociatedDictInfo;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.result.CodeInfoEntityResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.result.CodeInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.result.DictInfo;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author 1
 */
public interface CodeInfoService extends IService<CodeInfoEntity> {

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    BusinessPageResult queryPage(CodeInfoEntityPageParam param);

    BusinessResult<Boolean> status(CodeInfoEntityStatusParam param);

    BusinessResult<Boolean> deleteByIds(CodeInfoEntityDelParam param);

    BusinessResult<CodeInfoEntityResult> updateEntity(CodeInfoEntitySaveParam param);

    BusinessResult<Boolean> addEntity(CodeInfoEntitySaveParam param);

    List<CodeInfoResult> getInfoByKey(String key);

    BusinessResult<List<AssociatedDictInfo>> associatedDict(CodeInfoAssociatedDictParam param);

    BusinessResult<List<DictInfo>> getDictInfos(Collection<String> types);
}

