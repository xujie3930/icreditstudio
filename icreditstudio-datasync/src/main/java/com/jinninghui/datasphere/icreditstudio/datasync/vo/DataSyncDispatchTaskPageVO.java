package com.jinninghui.datasphere.icreditstudio.datasync.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DataSyncDispatchTaskPageVO implements Serializable {

    //任务ID
    private String taskId;
    //任务名称
    private String taskName;
    //任务类型
    private String taskType;
    //任务版本
    private String taskVersion;
    //任务状态
    private String taskStatus;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    //最近一次调度时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastDispatchTime;
    //执行周期
    private String dispatchPeriod;
    //总调度次数
    private Long totalDispatchNum;

}
