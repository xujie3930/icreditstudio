package com.jinninghui.datasphere.icreditstudio.metadata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.metadata.entity.WorkspaceTableEntity;

/**
 * @author Peng
 */
public interface WorkspaceTableService extends IService<WorkspaceTableEntity> {
    /**
     * 将hive表授权给用户
     *
     * @return
     */
    boolean authTable();
}
