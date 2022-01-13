package com.jinninghui.datasphere.icreditstudio.metadata.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jinninghui.datasphere.icreditstudio.metadata.common.SyncBaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Peng
 */
@Data
@TableName("icredit_workspace_table")
public class WorkspaceTableEntity extends SyncBaseEntity implements Serializable {

    public static final String WORKSPACE_ID = "workspace_id";

    public static final String DATABASE_NAME = "database_name";

    public static final String TABLE_NAME = "table_name";

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    /**
     * 工作空间ID
     */
    private String workspaceId;
    /**
     * 数据库名称
     */
    private String databaseName;
    /**
     * 表名称
     */
    private String tableName;
}
