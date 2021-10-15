package com.jinninghui.datasphere.icreditstudio.datasource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDdlSyncEntity;
import com.jinninghui.datasphere.icreditstudio.datasource.mapper.IcreditDdlSyncMapper;
import com.jinninghui.datasphere.icreditstudio.datasource.service.IcreditDdlSyncService;
import com.jinninghui.datasphere.icreditstudio.datasource.service.param.IcreditDdlConditionParam;
import com.jinninghui.datasphere.icreditstudio.framework.utils.HDFSUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2021-08-25
 */
@Slf4j
@Service
public class IcreditDdlSyncServiceImpl extends ServiceImpl<IcreditDdlSyncMapper, IcreditDdlSyncEntity> implements IcreditDdlSyncService {
    @Override
    public Map<String, Optional<IcreditDdlSyncEntity>> categoryLatelyDdlSyncs(Collection<String> datasourceIds) {
        Map<String, Optional<IcreditDdlSyncEntity>> results = null;
        IcreditDdlConditionParam build = IcreditDdlConditionParam.builder()
                .datasourceIds(datasourceIds)
                .build();
        List<IcreditDdlSyncEntity> list = list(queryWrapper(build));
        if (CollectionUtils.isNotEmpty(list)) {
            results = list.parallelStream()
                    .filter(Objects::nonNull)
                    .map(entity -> {
                        String columnsInfo = entity.getColumnsInfo();
                        if (StringUtils.isNotBlank(columnsInfo)) {
                            try {
                                String stringFromHDFS = HDFSUtils.getStringFromHDFS(columnsInfo);
                                entity.setColumnsInfo(stringFromHDFS);
                            } catch (Exception e) {
                                log.error("从hdfs获取数据源表信息失败", e);
                            }
                        }
                        return entity;
                    })
                    .collect(Collectors.groupingBy(IcreditDdlSyncEntity::getDatasourceId, Collectors.maxBy(Comparator.comparing(IcreditDdlSyncEntity::getVersion))));
        }
        return Optional.ofNullable(results).orElse(Maps.newHashMap());
    }

    private QueryWrapper<IcreditDdlSyncEntity> queryWrapper(IcreditDdlConditionParam param) {
        QueryWrapper<IcreditDdlSyncEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getDatasourceIds())) {
            wrapper.in(IcreditDdlSyncEntity.DATASOURCE_ID, param.getDatasourceIds());
        }
        return wrapper;
    }
}
