package com.jinninghui.datasphere.icreditstudio.datasync.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncWidetableEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.mapper.SyncWidetableMapper;
import com.jinninghui.datasphere.icreditstudio.datasync.service.SyncWidetableService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.SyncWideTableConditionParam;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author peng
 */
@Service
public class SyncWidetableServiceImpl extends ServiceImpl<SyncWidetableMapper, SyncWidetableEntity> implements SyncWidetableService {
    @Override
    public SyncWidetableEntity getWideTableField(String taskId, Integer version) {
        SyncWideTableConditionParam build = SyncWideTableConditionParam.builder()
                .taskId(taskId)
                .version(version)
                .build();
        List<SyncWidetableEntity> list = list(queryWrapper(build));
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public SyncWidetableEntity getWideTableByTaskId(String taskId) {
        SyncWideTableConditionParam build = SyncWideTableConditionParam.builder()
                .taskId(taskId)
                .build();
        List<SyncWidetableEntity> list = list(queryWrapper(build));
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    private QueryWrapper<SyncWidetableEntity> queryWrapper(SyncWideTableConditionParam param) {
        QueryWrapper<SyncWidetableEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getTaskId())) {
            wrapper.eq(SyncWidetableEntity.SYNC_TASK_ID, param.getTaskId());
        }
        if (Objects.nonNull(param.getVersion())) {
            wrapper.eq(SyncWidetableEntity.VERSION, param.getVersion());
        }
        return wrapper;
    }

}