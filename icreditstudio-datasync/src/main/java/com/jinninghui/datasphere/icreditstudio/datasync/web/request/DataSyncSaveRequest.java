package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class DataSyncSaveRequest {
    /**
     * 工作空间id
     */
    private String workspaceId;
    //=============同步任务定义===========
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 【0：未启用，1：启用】
     */
    private Integer enable;
    /**
     * 【0：可视化，1：sql】
     */
    private Integer createMode;
    /**
     * 任务描述
     */
    private String taskDescribe;
    //==============end=================
    //============同步任务构建=============
    /**
     * 目标库ID
     */
    private String targetSourceId;
    /**
     * 宽表名称
     */
    private String wideTableName;
    /**
     * 分区字段
     */
    private String partition;

    private Integer sourceType;
    /**
     * 关联关系
     */
    private List<FileAssociated> view;
    private List<WideTableFieldInfo> fieldInfos;
    //=============end===================
    //=================同步任务调度============
    private Integer maxThread;
    private Integer syncRate;
    private Integer scheduleType;
    //    private CronInfo cronInfo;
    private String cron;
    //==============end==================
}
