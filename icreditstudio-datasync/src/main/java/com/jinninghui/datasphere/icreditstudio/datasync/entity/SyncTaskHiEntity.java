package com.jinninghui.datasphere.icreditstudio.datasync.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 历史数据同步任务表
 *
 * @author peng
 * @TableName icredit_sync_task_hi
 */
@TableName(value = "icredit_sync_task_hi")
@Data
@Builder
public class SyncTaskHiEntity implements Serializable {
    /**
     *
     */
    @TableId
    private String id;

    /**
     *
     */
    private String taskId;

    /**
     * 所属工作空间ID
     */
    private String workspaceId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务状态【0：启用，1：停用，2：草稿】
     */
    private Boolean taskStatus;

    /**
     * 创建方式【0：可视化，1：SQL】
     */
    private Boolean createMode;

    /**
     *
     */
    private Boolean collectMode;

    /**
     *
     */
    private Boolean syncMode;

    /**
     *
     */
    private Boolean execStatus;

    /**
     *
     */
    private Date lastSchedulingTime;

    /**
     *
     */
    private String taskDescribe;

    /**
     *
     */
    private String taskParamJson;

    /**
     *
     */
    private Integer version;

    /**
     *
     */
    private String scheduleId;

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