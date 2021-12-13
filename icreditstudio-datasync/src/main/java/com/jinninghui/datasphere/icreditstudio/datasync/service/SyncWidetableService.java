package com.jinninghui.datasphere.icreditstudio.datasync.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncWidetableEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.WideTableInfoResult;

/**
 * @author peng
 */
public interface SyncWidetableService extends IService<SyncWidetableEntity> {

    /**
     * 宽表基本信息
     *
     * @param taskId
     * @return
     */
    SyncWidetableEntity getWideTableField(String taskId);

    WideTableInfoResult getWideTableInfoByTaskId(String taskId);
    /**
     * 通过任务ID取得宽表信息
     *
     * @param taskId
     * @return
     */
    SyncWidetableEntity getWideTableByTaskId(String taskId);
}
