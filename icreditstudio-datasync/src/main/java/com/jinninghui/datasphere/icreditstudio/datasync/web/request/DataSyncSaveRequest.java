package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import com.jinninghui.datasphere.icreditstudio.datasync.service.result.FileAssociated;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.WideTableFieldInfo;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "工作空间不能为空")
    private String workspaceId;
    //=============同步任务定义===========
    private String taskId;
    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称")
    private String taskName;
    /**
     * 【0：未启用，1：启用】
     */
    @NotNull(message = "任务启用状态")
    private Integer enable;
    /**
     * 【0：可视化，1：sql】
     */
    @NotNull(message = "任务创建方式")
    private Integer createMode;
    /**
     * 任务描述
     */
    @Length(max = 255, message = "不能大于255个字符")
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
     * 源库ID
     */
    private String datasourceId;
    /**
     * 关联关系
     */
    private List<FileAssociated> view;
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
    //    private CronInfo cronInfo;
    /**
     * cron表达式
     */
    private String cron;
    //==============end==================
}
