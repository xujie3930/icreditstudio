package org.apache.dolphinscheduler.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

import java.util.Date;

/**
 * @author xujie
 * @description 调度中心首页，请求参数
 * @create 2021-10-08 14:49
 **/
@Data
public class SchedulerHomepageRequest extends BusinessBasePageForm {

    private String workspaceId;//工作空间id

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date schedulerStartTime;//调度开始时间

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date schedulerEndTime;//调度结束时间

    private Integer scheduleType = 1;//0-数据同步，1-数据开发，2-数据治理
}
