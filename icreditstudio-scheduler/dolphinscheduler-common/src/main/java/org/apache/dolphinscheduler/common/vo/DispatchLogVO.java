package org.apache.dolphinscheduler.common.vo;

import lombok.Data;

@Data
public class DispatchLogVO {

    //task任务实例ID
    private Integer taskInstanceId;
    //task任务实例名称
    private String taskInstanceName;
    //task任务实例状态
    private Integer taskInstanceState;
    //task任务执行时间
    private String taskInstanceExecTime;
    //task任务执行时长
    private Long taskInstanceExecDuration;
    //task任务执行 同步数据量
    private Long totalSyncInstanceNum;
    //task任务执行 质检数据量
    private Long totalQualityCheckInstanceNum;

}
