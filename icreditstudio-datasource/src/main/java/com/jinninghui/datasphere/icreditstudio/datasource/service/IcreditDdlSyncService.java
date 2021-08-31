package com.jinninghui.datasphere.icreditstudio.datasource.service;

import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDdlSyncEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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
     * @param datasourceId
     * @return
     */
    Map<String, List<IcreditDdlSyncEntity>> categoryLatelyDdlSyncs(String datasourceId);
}
