package org.apache.dolphinscheduler.api.service.result;

import lombok.Data;

@Data
public class DispatchTaskPageResult {

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
    private String createTime;
    //最近一次调度时间
    private String lastDispatchTime;
    //执行周期
    private String dispatchPeriod;
    //最近一次调度执行状态
    private Integer dispatchStatus;

}
