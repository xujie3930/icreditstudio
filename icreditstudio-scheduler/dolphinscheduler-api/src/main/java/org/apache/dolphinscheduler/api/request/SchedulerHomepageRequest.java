package org.apache.dolphinscheduler.api.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

/**
 * @author xujie
 * @description 调度中心首页，请求参数
 * @create 2021-10-08 14:49
 **/
@Data
public class SchedulerHomepageRequest extends BusinessBasePageForm {

    private String workspaceId;//工作空间id

    private Long schedulerStartTime;//调度开始时间

    private Long schedulerEndTime;//调度结束时间

    private Integer scheduleType = 0;//0-数据同步，1-数据开发，2-数据治理
}
