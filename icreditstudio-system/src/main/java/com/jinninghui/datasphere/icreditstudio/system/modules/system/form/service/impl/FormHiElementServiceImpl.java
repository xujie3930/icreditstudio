package com.jinninghui.datasphere.icreditstudio.system.modules.system.form.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.entity.FormHiElementEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.mapper.FormHiElementMapper;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.service.FormHiElementService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request.FormHiElementEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("formHiElementService")
public class FormHiElementServiceImpl extends ServiceImpl<FormHiElementMapper, FormHiElementEntity> implements FormHiElementService {

    @Autowired
    private FormHiElementMapper formHiElementMapper;

    @Override
    public BusinessPageResult queryPage(FormHiElementEntityPageRequest pageRequest) {
        IPage<FormHiElementEntity> page = this.page(
                new Query<FormHiElementEntity>().getPage(pageRequest),
                new QueryWrapper<FormHiElementEntity>()

        );

        return BusinessPageResult.build(page, pageRequest);
    }

}
