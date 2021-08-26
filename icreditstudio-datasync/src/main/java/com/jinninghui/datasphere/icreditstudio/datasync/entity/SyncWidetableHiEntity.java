package com.jinninghui.datasphere.icreditstudio.datasync.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据同步目标表结构信息表
 *
 * @author peng
 * @TableName icredit_sync_widetable_hi
 */
@TableName(value = "icredit_sync_widetable_hi")
@Data
@Builder
public class SyncWidetableHiEntity implements Serializable {
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
     * 数据源ID
     */
    private String datasourceId;

    /**
     * 目标源地址
     */
    private String targetUrl;

    /**
     * 分区字段
     */
    private String partitionStr;

    /**
     *
     */
    private String name;

    /**
     * 版本号
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
     * 删除标识【0：未删除，1：已删除】
     */
    private Boolean delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}