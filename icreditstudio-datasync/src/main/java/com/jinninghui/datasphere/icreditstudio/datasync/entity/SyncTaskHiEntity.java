package com.jinninghui.datasphere.icreditstudio.datasync.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class SyncTaskHiEntity implements Serializable {
    public static final String WORKSPACE_ID = "workspace_id";
    public static final String TASK_NAME = "task_name";
    public static final String TASK_STATUS = "task_status";
    public static final String EXEC_STATUS = "exec_status";
    public static final String LAST_SCHEDULING_TIME = "last_scheduling_time";
    public static final String TASK_ID = "task_id";
    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 工作空间ID
     */
    private String workspaceId;

    /**
     * 同步任务名称
     */
    private String taskName;

    /**
     * 启用状态【0：启用，1：未启用】
     */
    private Integer enable;

    /**
     * 任务状态【0：启用，1：停用，2：草稿】
     */
    private Integer taskStatus;

    /**
     * cron参数
     */
    private String cronParam;

    /**
     * 创建方式【0：可视化，1：SQL】
     */
    private Integer createMode;

    /**
     * 采集方式【0：手动执行，1：周期执行】
     */
    private Integer collectMode;

    /**
     * 同步方式【0:增量同步，1：全量同步】
     */
    private Integer syncMode;

    /**
     * 执行状态【0：成功，1：失败，2：执行中】
     */
    private Integer execStatus;

    /**
     *
     */
    private Date lastSchedulingTime;

    /**
     * 任务描述
     */
    private String taskDescribe;

    /**
     * 任务参数
     */
    private String taskParamJson;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 调度任务ID
     */
    private String scheduleId;

    private String createUserId;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}