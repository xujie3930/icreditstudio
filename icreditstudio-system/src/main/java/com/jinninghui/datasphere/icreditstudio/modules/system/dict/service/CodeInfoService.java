package com.jinninghui.datasphere.icreditstudio.modules.system.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.modules.system.dict.entity.CodeInfoEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.dict.service.param.CodeInfoEntityDelParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.dict.service.param.CodeInfoEntityPageParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.dict.service.param.CodeInfoEntitySaveParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.dict.service.param.CodeInfoEntityStatusParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.dict.service.result.CodeInfoEntityResult;
import com.jinninghui.datasphere.icreditstudio.modules.system.dict.service.result.CodeInfoResult;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;

import java.util.List;

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
}

