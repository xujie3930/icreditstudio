package com.jinninghui.datasphere.icreditstudio.metadata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.metadata.entity.WorkspaceTableEntity;
import com.jinninghui.datasphere.icreditstudio.metadata.service.param.UserPerm;

import java.sql.Connection;
import java.util.List;

/**
 * @author Peng
 */
public interface WorkspaceTableService extends IService<WorkspaceTableEntity> {
    /**
     * 将hive表授权给用户
     *
     * @return
     */
    boolean authTable(List<UserPerm> userPerms, Connection conn);

    /**
     * 将hive表接触授权
     *
     * @param userPerms
     * @param conn
     * @return
     */
    boolean unAuthTable(List<UserPerm> userPerms, Connection conn);
}
