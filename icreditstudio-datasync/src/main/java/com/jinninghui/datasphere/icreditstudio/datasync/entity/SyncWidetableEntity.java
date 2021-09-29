package com.jinninghui.datasphere.icreditstudio.datasync.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jinninghui.datasphere.icreditstudio.datasync.common.SyncBaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据同步目标表结构信息表
 *
 * @author peng
 * @TableName icredit_sync_widetable
 */
@TableName(value = "icredit_sync_widetable")
@Data
public class SyncWidetableEntity extends SyncBaseEntity implements Serializable {

    public static final String SYNC_TASK_ID = "sync_task_id";
    public static final String VERSION = "version";
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 任务ID
     */
    private String syncTaskId;

    /**
     * 查询sql
     */
    private String sqlStr;

    /**
     * 关联关系json
     */
    private String viewJson;

    /**
     * 数据源ID
     */
    private String datasourceId;
    /**
     * 资源类型【0：外部数据库，1：本地文件，2：区块链】
     */
    private Integer sourceType;

    /**
     * 目标源地址
     */
    private String targetUrl;
    /**
     * 目标库名
     */
    private String targetSource;

    /**
     * 分区字段
     */
    private String syncCondition;
    /**
     * 可视化创建的表信息
     */
    private String sourceTables;

    /**
     * 表名称
     */
    private String name;

    /**
     * 版本号
     */
    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}