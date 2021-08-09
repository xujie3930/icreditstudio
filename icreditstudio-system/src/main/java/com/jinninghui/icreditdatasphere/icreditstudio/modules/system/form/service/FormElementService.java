package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.entity.FormElementEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.service.param.FormElementConditionParam;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.web.request.FormElementEntityPageRequest;
import com.hashtech.businessframework.result.BusinessPageResult;

import java.util.List;

/**
 * 
 *
 * @author 1
 */
public interface FormElementService extends IService<FormElementEntity> {

    /**
    *  分页查询
    * @param pageRequest
    * @return
    */
    BusinessPageResult queryPage(FormElementEntityPageRequest pageRequest);

    List<FormElementEntity> getElementList(FormElementConditionParam param);
}

