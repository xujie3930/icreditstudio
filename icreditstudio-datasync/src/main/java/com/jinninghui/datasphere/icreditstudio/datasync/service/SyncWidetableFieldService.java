package com.jinninghui.datasphere.icreditstudio.datasync.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncWidetableFieldEntity;

import java.util.List;

/**
 * @author peng
 */
public interface SyncWidetableFieldService extends IService<SyncWidetableFieldEntity> {

    /**
     * 宽表字段列表
     *
     * @param wideTableId
     * @return
     */
    List<SyncWidetableFieldEntity> getWideTableFields(String wideTableId);
}
