package com.jinninghui.datasphere.icreditstudio.system.modules.system.form.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.entity.FormHiElementEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request.FormHiElementEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;

/**
 * @author 1
 */
public interface FormHiElementService extends IService<FormHiElementEntity> {

    /**
     * 分页查询
     *
     * @param pageRequest
     * @return
     */
    BusinessPageResult queryPage(FormHiElementEntityPageRequest pageRequest);
}

