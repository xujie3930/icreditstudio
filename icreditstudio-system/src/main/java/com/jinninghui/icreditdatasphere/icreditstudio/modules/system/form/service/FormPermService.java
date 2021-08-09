package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.entity.FormPermEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.service.param.FormPermEntityConditionParam;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.web.request.FormPermEntityPageRequest;
import com.hashtech.businessframework.result.BusinessPageResult;

import java.util.List;
import java.util.Set;

/**
 * 
 *
 * @author 1
 */
public interface FormPermService extends IService<FormPermEntity> {

    /**
    *  分页查询
    * @param pageRequest
    * @return
    */
    BusinessPageResult queryPage(FormPermEntityPageRequest pageRequest);

    Set<String> getIdsByFormId(String formId);

    List<FormPermEntity> getPermList(FormPermEntityConditionParam param);
}

