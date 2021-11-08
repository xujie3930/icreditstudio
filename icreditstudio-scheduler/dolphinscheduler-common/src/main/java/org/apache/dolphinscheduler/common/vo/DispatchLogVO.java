package org.apache.dolphinscheduler.common.vo;

import lombok.Data;

import java.util.Date;

@Data
public class DispatchLogVO {

    //task任务实例ID
    private String taskInstanceId;
    //task任务实例名称
    private String taskInstanceName;
    //task任务实例状态
    private Integer taskInstanceState;
    //task任务执行时间
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
