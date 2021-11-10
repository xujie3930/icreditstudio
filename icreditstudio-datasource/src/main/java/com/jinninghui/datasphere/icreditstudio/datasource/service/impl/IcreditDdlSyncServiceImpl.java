package com.jinninghui.datasphere.icreditstudio.datasource.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jinninghui.datasphere.icreditstudio.datasource.common.enums.HiveMapJdbcTypeEnum;
import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDdlSyncEntity;
import com.jinninghui.datasphere.icreditstudio.datasource.mapper.IcreditDatasourceMapper;
import com.jinninghui.datasphere.icreditstudio.datasource.mapper.IcreditDdlSyncMapper;
import com.jinninghui.datasphere.icreditstudio.datasource.service.IcreditDdlSyncService;
import com.jinninghui.datasphere.icreditstudio.datasource.service.param.IcreditDdlConditionParam;
import com.jinninghui.datasphere.icreditstudio.datasource.service.result.ColumnListResult;
import com.jinninghui.datasphere.icreditstudio.datasource.service.result.DatasourceStructureResult;
import com.jinninghui.datasphere.icreditstudio.datasource.service.result.TableList;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.utils.HDFSUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Autowired
    private IcreditDdlSyncMapper ddlSyncMapper;
    @Resource
    private IcreditDatasourceMapper datasourceMapper;
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

    @Override
    public BusinessResult<DatasourceStructureResult> getDatasourceStructure(String id) {
        DatasourceStructureResult structureResult = new DatasourceStructureResult();
        List<String> tableOptions = new ArrayList<>();
        Integer tableCount = 0;
        IcreditDdlSyncEntity entity = ddlSyncMapper.selectMaxVersionByDatasourceId(id);
        if (Objects.isNull(entity)){
            return BusinessResult.success(structureResult);
        }
        try {
            String datasourceJson = HDFSUtils.getStringFromHDFS(entity.getColumnsInfo());
            List<ColumnListResult> list = new ArrayList<>();
            List<TableList> tableLists = JSON.parseArray(datasourceJson, TableList.class);
            for (int i = 0; i < tableLists.size(); i++) {
                tableCount ++;
                String tableName  = tableLists.get(i).getTableName();
                tableOptions.add(tableName);
                for (TableList.ColumnList columnList : tableLists.get(i).getColumnList()) {
                    ColumnListResult result = new ColumnListResult();
                    result.setTableName(tableName);
                    result.setFieldName(columnList.getField());
                    HiveMapJdbcTypeEnum jdbcTypeEnum = HiveMapJdbcTypeEnum.find(columnList.getType());
                    result.setFieldType(Lists.newArrayList(jdbcTypeEnum.getCategoryEnum().getCode(), jdbcTypeEnum.getJdbcType()));
                    result.setRemark(columnList.getRemark());
                    result.setFieldChineseName(columnList.getRemark());
                    list.add(result);
                }
            }
            structureResult.setTableCount(tableCount);
            structureResult.setTableOptions(tableOptions);
            structureResult.setColumnList(list);
        } catch (Exception e) {
            log.error("查看数据源失败:{}", e.getMessage());
        }
        return BusinessResult.success(structureResult);
    }

    private QueryWrapper<IcreditDdlSyncEntity> queryWrapper(IcreditDdlConditionParam param) {
        QueryWrapper<IcreditDdlSyncEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getDatasourceIds())) {
            wrapper.in(IcreditDdlSyncEntity.DATASOURCE_ID, param.getDatasourceIds());
        }
        return wrapper;
    }
}
