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
    private Date shcedulerStartTime;//调度开始时间

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date shcedulerEndTime;//调度结束时间

    private Integer scheduleType = 1;//0-周期实例，1-手动实例
}
