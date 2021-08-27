package com.jinninghui.datasphere.icreditstudio.datasync.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 宽表字段表
 *
 * @author peng
 * @TableName icredit_sync_widetable_field_hi
 */
@TableName(value = "icredit_sync_widetable_field_hi")
@Data
@Builder
public class SyncWidetableFieldHiEntity implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

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
    private Boolean type;

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

    /**
     *
     */
    private String remark;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private String createBy;

    /**
     *
     */
    private Date updateTime;

    /**
     *
     */
    private String updateBy;

    /**
     *
     */
    private Boolean delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}