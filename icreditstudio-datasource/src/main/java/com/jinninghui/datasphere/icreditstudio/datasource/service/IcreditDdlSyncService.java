package com.jinninghui.datasphere.icreditstudio.datasource.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDdlSyncEntity;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.pojo.TableISyncnfo;
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
        List<TableISyncnfo> tableISyncnfos = JSONArray.parseArray(columnsInfo).toJavaList(TableISyncnfo.class);
        if (CollectionUtils.isNotEmpty(tableISyncnfos)) {
            results = tableISyncnfos.parallelStream()
                    .filter(Objects::nonNull)
                    .map(TableISyncnfo::getTableName)
                    .collect(Collectors.toList());
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }
}
