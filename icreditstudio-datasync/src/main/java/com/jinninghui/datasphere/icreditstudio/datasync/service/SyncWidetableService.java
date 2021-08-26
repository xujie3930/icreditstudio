package com.jinninghui.datasphere.icreditstudio.datasync.service;

import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncWidetableEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.WideTableFieldInfo;

import java.util.List;

/**
 * @author peng
 */
public interface SyncWidetableService extends IService<SyncWidetableEntity> {

    /**
     * 宽表基本信息
     *
     * @param taskId
     * @param version
     * @return
     */
    SyncWidetableEntity getWideTableFields(String taskId, Integer version);
}
