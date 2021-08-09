package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.entity.FormDefinitionEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.service.param.*;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.service.result.FormDefinitionResult;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;

/**
 * 表单定义模板表
 *
 * @author 1
 */
public interface FormDefinitionService extends IService<FormDefinitionEntity> {

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    BusinessResult<BusinessPageResult> queryPage(FormDefinitionPageParam param);

    BusinessResult<Boolean> saveDef(FormDefinitionSaveParam param);

    BusinessResult<Boolean> publish(FormDefinitionPublishParam param);

    BusinessResult<FormDefinitionResult> formDetail(FormDetailQueryParam param);

    BusinessResult<Boolean> deleteFormByIds(FormDefinitionEntityDelParam param);

    BusinessResult<Boolean> disable(FormDisableParam param);
}

