package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DataSyncDispatchTaskPageResult implements Serializable {

    //任务ID
    private String taskId;
    //任务名称
    private String taskName;
    //调度类型
    private String dispatchType;
    //任务版本
    private String taskVersion;
    //任务状态
    private Integer taskStatus;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    //最近一次调度时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastDispatchTime;
    //执行周期
    private String dispatchPeriod;
    //最近一次调度执行状态
    private Integer dispatchStatus;

}
