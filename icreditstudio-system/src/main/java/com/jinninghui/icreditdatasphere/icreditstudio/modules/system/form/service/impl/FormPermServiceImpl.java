package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.Query;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.entity.FormPermEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.mapper.FormPermMapper;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.service.FormPermService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.service.param.FormPermEntityConditionParam;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.web.request.FormPermEntityPageRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service("formPermService")
public class FormPermServiceImpl extends ServiceImpl<FormPermMapper, FormPermEntity> implements FormPermService {

    @Autowired
    private FormPermMapper formPermMapper;

    @Override
    public BusinessPageResult queryPage(FormPermEntityPageRequest pageRequest) {
        IPage<FormPermEntity> page = this.page(
                new Query<FormPermEntity>().getPage(pageRequest),
                new QueryWrapper<FormPermEntity>()

        );

        return BusinessPageResult.build(page, pageRequest);
    }

    @Override
    public Set<String> getIdsByFormId(String formId) {
        Set<String> results = null;
        FormPermEntityConditionParam build = FormPermEntityConditionParam.builder().formId(formId).build();
        QueryWrapper<FormPermEntity> wrapper = queryWrapper(build);
        List<FormPermEntity> list = list(wrapper);
        results = list.parallelStream()
                .filter(Objects::nonNull)
                .map(FormPermEntity::getId)
                .collect(Collectors.toSet());
        return Optional.ofNullable(results).orElse(Sets.newHashSet());
    }

    @Override
    public List<FormPermEntity> getPermList(FormPermEntityConditionParam param) {
        List<FormPermEntity> results = null;
        if (Objects.nonNull(param) && StringUtils.isNotBlank(param.getFormId())) {
            QueryWrapper<FormPermEntity> wrapper = queryWrapper(param);
            results = list(wrapper);
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    private QueryWrapper<FormPermEntity> queryWrapper(FormPermEntityConditionParam param) {
        QueryWrapper<FormPermEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getFormId())) {
            wrapper.eq(FormPermEntity.FORM_ID, param.getFormId());
        }
        return wrapper;
    }
}
