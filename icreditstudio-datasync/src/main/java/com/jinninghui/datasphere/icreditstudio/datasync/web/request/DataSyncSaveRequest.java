package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.TableInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedData;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.WideTableFieldInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;
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
     * 任务ID
     */
    private String taskId;
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
     * 目标库名
     */
    private String targetSource;
    /**
     * 宽表名称
     */
    private String wideTableName;
    /**
     * 分区字段
     */
    private String partition;
    /**
     * 【0：外部数据库，1：本地文件，2：区块链数据】
     */
    private Integer sourceType;
    /**
     * 调用步骤
     */
    @NotNull(message = "60000021")
    private Integer callStep;
    /**
     * 数据源方言
     */
    private String dialect;

    /**
     * 连接表集合
     */
    private List<TableInfo> sourceTables;
    /**
     * 关联关系
     */
    private List<AssociatedData> view;
    /**
     * 宽表字段
     */
    private List<WideTableFieldInfo> fieldInfos;
    //=============end===================
    //=================同步任务调度============
    /**
     * 最大并发数
     */
    private Integer maxThread;
    /**
     * 同步速率【0：限流，1：不限流】
     */
    private Integer syncRate;
    /**
     * 限流速率 XXX条/s
     */
    private Integer limitRate;
    /**
     * 调度类型【0：周期，1：手动】
     */
    private Integer scheduleType;
    /**
     * cron表达式
     */
    private String cron;
    //==============end==================
}
