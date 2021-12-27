package org.apache.dolphinscheduler.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DispatchLogVO {

    private Integer version;
    //task任务实例ID
    private String taskInstanceId;
    //流程实例ID
    private String processInstanceId;
    //task任务实例名称
    private String taskInstanceName;
    //task任务实例状态
    private Integer taskInstanceState;
    //task任务执行时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date taskInstanceExecTime;
    //task任务执行时长
    private Long taskInstanceExecDuration;
    //task任务执行 同步数据量
    private Long totalSyncInstanceNum;
    //task任务执行 质检数据量
    private Long totalQualityCheckInstanceNum;
    //task任务执行 开始时间
    private Date startTime;
    //task任务执行 结束时间
    private Date endTime;

}
