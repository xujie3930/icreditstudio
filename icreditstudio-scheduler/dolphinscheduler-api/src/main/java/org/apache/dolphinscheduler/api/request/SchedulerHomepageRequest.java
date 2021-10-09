package org.apache.dolphinscheduler.api.request;

import lombok.Data;

/**
 * @author xujie
 * @description 调度中心首页，请求参数
 * @create 2021-10-08 14:49
 **/
@Data
public class SchedulerHomepageRequest {

    private String workspaceId;//工作空间id

    private String shcedulerStartTime;//调度开始时间

    private String shcedulerEndTime;//调度结束时间

    private Integer scheduleType;//0-周期实例，1-手动实例
}
