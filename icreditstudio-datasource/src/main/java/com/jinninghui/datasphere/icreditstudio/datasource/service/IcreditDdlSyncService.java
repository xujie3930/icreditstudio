package com.jinninghui.datasphere.icreditstudio.datasource.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDdlSyncEntity;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.pojo.TableSyncInfo;
import com.jinninghui.datasphere.icreditstudio.datasource.service.result.DatasourceStructureResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xujie
 * @since 2021-08-25
 */
public interface IcreditDdlSyncService extends IService<IcreditDdlSyncEntity> {
    /**
     * 最新的ddl
     *
     * @param datasourceIds
     * @return
     */
    Map<String, Optional<IcreditDdlSyncEntity>> categoryLatelyDdlSyncs(Collection<String> datasourceIds);

    /**
     * 解析表名称
     *
     * @param columnsInfo
     * @return
     */
    static List<String> parseColumnsTableName(String columnsInfo) {
        List<String> results = null;
        List<TableSyncInfo> tableSyncInfos = JSONArray.parseArray(columnsInfo).toJavaList(TableSyncInfo.class);
        if (CollectionUtils.isNotEmpty(tableSyncInfos)) {
            results = tableSyncInfos.parallelStream()
                    .filter(Objects::nonNull)
                    .map(TableSyncInfo::getTableName)
                    .collect(Collectors.toList());
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    /**
     * 解析表信息字段
     *
     * @param columnsInfo
     * @return
     */
    static List<TableSyncInfo> parseTableSyncInfo(String columnsInfo) {
        return JSONArray.parseArray(columnsInfo).toJavaList(TableSyncInfo.class);
    }

    BusinessResult<List<DatasourceStructureResult>> getDatasourceStructure(String id);
}
