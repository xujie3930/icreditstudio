package com.jinninghui.datasphere.icreditstudio.modules.system.form.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.Query;
import com.jinninghui.datasphere.icreditstudio.modules.system.form.entity.FormElementEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.form.mapper.FormElementMapper;
import com.jinninghui.datasphere.icreditstudio.modules.system.form.service.FormElementService;
import com.jinninghui.datasphere.icreditstudio.modules.system.form.service.param.FormElementConditionParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.form.web.request.FormElementEntityPageRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service("formElementService")
public class FormElementServiceImpl extends ServiceImpl<FormElementMapper, FormElementEntity> implements FormElementService {

    @Autowired
    private FormElementMapper formElementMapper;

    @Override
    public BusinessPageResult queryPage(FormElementEntityPageRequest pageRequest) {
        IPage<FormElementEntity> page = this.page(
                new Query<FormElementEntity>().getPage(pageRequest),
                new QueryWrapper<FormElementEntity>()

        );

        return BusinessPageResult.build(page, pageRequest);
    }

    @Override
    public List<FormElementEntity> getElementList(FormElementConditionParam param) {
        List<FormElementEntity> results = null;
        if (Objects.nonNull(param) && StringUtils.isNotBlank(param.getFormId())) {
            QueryWrapper<FormElementEntity> wrapper = queryWrapper(param);
            results = list(wrapper);
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    private QueryWrapper<FormElementEntity> queryWrapper(FormElementConditionParam param) {
        QueryWrapper<FormElementEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getFormId())) {
            wrapper.eq(FormElementEntity.FORM_ID, param.getFormId());
        }
        return wrapper;
    }
}
