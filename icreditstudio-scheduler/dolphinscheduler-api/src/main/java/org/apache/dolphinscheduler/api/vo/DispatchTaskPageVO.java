package org.apache.dolphinscheduler.api.vo;

import lombok.Data;

@Data
public class DispatchTaskPageVO {

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
    private String createTime;
    //最近一次调度时间
    private String lastDispatchTime;
    //执行周期
    private String dispatchPeriod;
    //总调度次数
    private Long totalDispatchNum;

}
