package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.TableInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedData;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.SyncCondition;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.WideTableFieldRequest;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Peng
 */
@Data
public class  DataSyncSaveRequest {
    /**
     * 工作空间id
     */
    @NotBlank(message = "60000000")
    private String workspaceId;
    //=============同步任务定义===========
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 任务名称
     */
    @NotBlank(message = "60000009")
    @Length(max = 15, message = "60000022")
    private String taskName;
    /**
     * 【0：未启用，1：启用】
     */
    @Range(max = 1, message = "")
    @NotNull(message = "60000010")
    private Integer enable;

    public Integer getEnable() {
        if (enable == null) {
            return 1;
        }
        return enable;
    }

    /**
     * 【0：可视化，1：sql】
     */
    @NotNull(message = "60000011")
    private Integer createMode;
    /**
     * 任务描述
     */
    @Length(max = 255, message = "60000023")
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
     * 同步条件
     */
    private SyncCondition syncCondition;
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
     * 识别宽表的sql
     */
    private String sql;

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
    private List<WideTableFieldRequest> fieldInfos;
    //=============end===================
    //=================同步任务调度============
    /**
     * 最大并发数
     */
    private Integer maxThread;

    public Integer getMaxThread() {
        if (maxThread == null) {
            return 1;
        }
        return maxThread;
    }

    /**
     * 同步速率【0：限流，1：不限流】
     */
    private Integer syncRate;

    public Integer getSyncRate() {
        if (syncRate == null) {
            return 1;
        }
        return syncRate;
    }

    public boolean isLimit() {
        return getSyncRate().equals(0);
    }

    /**
     * 限流速率 XXX条/s
     */
    private Integer limitRate;

    public Integer getLimitRate() {
        if (limitRate == null) {
            return 100;
        }
        return limitRate;
    }

    /**
     * 调度类型【0：周期，1：手动】
     */
    private Integer scheduleType;

    public Integer getScheduleType() {
        if (scheduleType == null) {
            return 1;
        }
        return scheduleType;
    }

    /**
     * cron表达式
     */
    private String cron;

    private CronParam cronParam;
    //==============end==================
}
