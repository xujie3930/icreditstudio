package com.jinninghui.datasphere.icreditstudio.datasync.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.datasync.common.ResourceCodeBean;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.DictColumnEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.DeleteFlagEnum;
import com.jinninghui.datasphere.icreditstudio.datasync.mapper.DictColumnMapper;
import com.jinninghui.datasphere.icreditstudio.datasync.service.DictColumnService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DictColumnSaveParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictColumnResult;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DictColumnServiceImpl extends ServiceImpl<DictColumnMapper, DictColumnEntity> implements DictColumnService {

    private static final String COLUMN_KEY_VALUE_REGEX = "[0-9a-zA-Z\u4e00-\u9fa5]{0,40}";

    @Autowired
    private DictColumnMapper dictColumnMapper;

    @Override
    public void delBatchByDictId(Integer delFlag, String dictId) {
        dictColumnMapper.delBatchByDictId(delFlag, dictId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(String dictId, List<DictColumnSaveParam> saveParams) {
        int size = saveParams.size();
        if(size < 1){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000087.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000087.message);
        }
        List<DictColumnEntity> dictColumns = new ArrayList<>();
        DictColumnEntity dictColumn;
        List<String> stringList = saveParams.stream().map(DictColumnSaveParam::getColumnKey)
                .collect(Collectors.toList());
        long count = stringList.stream().distinct().count();
        if(size > count){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000078.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000078.message);
        }
        for (int i = 0; i < size; i++) {
            if (StringUtils.isEmpty(saveParams.get(i).getColumnKey())) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000077.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000077.message);
            }
            if (!saveParams.get(i).getColumnKey().matches(COLUMN_KEY_VALUE_REGEX)) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000084.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000084.message);
            }
            if (!saveParams.get(i).getColumnValue().matches(COLUMN_KEY_VALUE_REGEX)) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000085.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000085.message);
            }
            if (StringUtils.isNotEmpty(saveParams.get(i).getRemark()) && saveParams.get(i).getRemark().length() > 200) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000086.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000086.message);
            }
            dictColumn = new DictColumnEntity();
            dictColumn = BeanCopyUtils.copyProperties(saveParams.get(i), dictColumn);
            dictColumn.setDictId(dictId);
            dictColumn.setDelFlag(DeleteFlagEnum.NOT_DELETED.getCode());
            dictColumns.add(dictColumn);
        }
        saveBatch(dictColumns);
    }

    @Override
    public List<DictColumnResult> getColumnListByDictId(String dictId) {
        return dictColumnMapper.getColumnListByDictId(dictId);
    }

    @Override
    public void truthDelBatchByDictId(String dictId) {
        dictColumnMapper.truthDelBatchByDictId(dictId);
    }

    @Override
    public List<DictColumnEntity> getDictInfoByIds(Collection<String> ids) {
        List<DictColumnEntity> results = null;
        if (CollectionUtils.isNotEmpty(ids)) {
            QueryWrapper<DictColumnEntity> wrapper = new QueryWrapper<>();
            wrapper.in(DictColumnEntity.DICT_ID, ids);
            results = list(wrapper);
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }
}
