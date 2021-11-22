package com.jinninghui.datasphere.icreditstudio.datasync.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jinninghui.datasphere.icreditstudio.datasync.common.SyncBaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 宽表字段表
 *
 * @author peng
 * @TableName icredit_sync_widetable_field
 */
@TableName(value = "icredit_sync_widetable_field")
@Data
public class SyncWidetableFieldEntity extends SyncBaseEntity implements Serializable {

    public static final String WIDE_TABLE_ID = "wide_table_id";
    /**
     * 主键
     */
    @TableId
    private String id;

    private Integer sort;
    /**
     *
     */
    private String wideTableId;

    /**
     * 字段名称
     */
    private String name;

    /**
     *
     */
    private String type;

    /**
     *
     */
    private String source;

    /**
     *
     */
    private String chinese;

    /**
     *
     */
    private String dictKey;

    /**
     *
     */
    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    private String databaseName;
}