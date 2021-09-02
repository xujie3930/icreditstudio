package com.jinninghui.datasphere.icreditstudio.system.modules.system.form.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.entity.FormHiDefinitionEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request.FormHiDefinitionEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;

import java.util.List;

/**
 * @author 1
 */
public interface FormHiDefinitionService extends IService<FormHiDefinitionEntity> {

    /**
     * 分页查询
     *
     * @param pageRequest
     * @return
     */
    BusinessPageResult queryPage(FormHiDefinitionEntityPageRequest pageRequest);

    /**
     * 根据表单定有 ID 更新表单定义历史 的状态
     *
     * @param ids
     */
    void updateStatusByFormDefiIds(List<String> ids);

    BusinessResult<Boolean> deleteHiFormById(String id);
}

