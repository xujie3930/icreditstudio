package com.jinninghui.datasphere.icreditstudio.datasource.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDdlSyncEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
//        JSONArray.parseArray(columnsInfo).toJavaList()
        return Lists.newArrayList();
    }
}
