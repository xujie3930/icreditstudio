package com.jinninghui.datasphere.icreditstudio.datasync.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncWidetableEntity;

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
    SyncWidetableEntity getWideTableField(String taskId, Integer version);

    /**
     * 通过任务ID取得宽表信息
     *
     * @param taskId
     * @return
     */
    SyncWidetableEntity getWideTableByTaskId(String taskId);
}
