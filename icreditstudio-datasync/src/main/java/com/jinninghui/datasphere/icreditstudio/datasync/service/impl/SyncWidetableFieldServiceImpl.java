package com.jinninghui.datasphere.icreditstudio.datasync.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncWidetableFieldEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.mapper.SyncWidetableFieldMapper;
import com.jinninghui.datasphere.icreditstudio.datasync.service.SyncWidetableFieldService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.SyncWideTableFieldConditionParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author peng
 */
@Service
public class SyncWidetableFieldServiceImpl extends ServiceImpl<SyncWidetableFieldMapper, SyncWidetableFieldEntity> implements SyncWidetableFieldService {
    @Resource
    private SyncWidetableFieldMapper mapper;

    @Override
    public List<SyncWidetableFieldEntity> getWideTableFields(String wideTableId) {
        SyncWideTableFieldConditionParam build = SyncWideTableFieldConditionParam.builder()
                .wideTableId(wideTableId)
                .build();
        return list(queryWrapper(build));
    }

    @Override
    public boolean deleteByWideTableIds(Set<String> ids) {
        return mapper.deleteByWideTableIds(ids);
    }

    private QueryWrapper<SyncWidetableFieldEntity> queryWrapper(SyncWideTableFieldConditionParam param) {
        QueryWrapper<SyncWidetableFieldEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getWideTableId())) {
            wrapper.eq(SyncWidetableFieldEntity.WIDE_TABLE_ID, param.getWideTableId());
        }
        return wrapper;
    }
}




