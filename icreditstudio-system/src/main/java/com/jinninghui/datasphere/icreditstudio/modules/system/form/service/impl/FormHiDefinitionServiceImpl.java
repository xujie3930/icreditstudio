package com.jinninghui.datasphere.icreditstudio.modules.system.form.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.common.enums.FormStatusEnum;
import com.jinninghui.datasphere.icreditstudio.modules.system.form.entity.FormHiDefinitionEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.form.mapper.FormHiDefintionMapper;
import com.jinninghui.datasphere.icreditstudio.modules.system.form.service.FormHiDefinitionService;
import com.jinninghui.datasphere.icreditstudio.modules.system.form.web.request.FormHiDefinitionEntityPageRequest;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("formHiDefintionService")
public class FormHiDefinitionServiceImpl extends ServiceImpl<FormHiDefintionMapper, FormHiDefinitionEntity> implements FormHiDefinitionService {

    @Autowired
    private FormHiDefintionMapper formHiDefintionMapper;

    @Override
    public BusinessPageResult queryPage(FormHiDefinitionEntityPageRequest pageRequest) {
        IPage<FormHiDefinitionEntity> page = this.page(
                new Query<FormHiDefinitionEntity>().getPage(pageRequest),
                new QueryWrapper<FormHiDefinitionEntity>()
            
        );

        return BusinessPageResult.build(page, pageRequest);
    }

    @Override
    public BusinessResult<Boolean> deleteHiFormById(String id){
        formHiDefintionMapper.updateHiFormStatusById(id, FormStatusEnum.C.getCode());
        return BusinessResult.success(true);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatusByFormDefiIds(List<String> formIds){
        formHiDefintionMapper.updateStatusByFormDefiIds(formIds,FormStatusEnum.C.getCode());
    }

}
