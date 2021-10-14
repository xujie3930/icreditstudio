package org.apache.dolphinscheduler.api.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

@Data
public class DispatchTaskPageRequest extends BusinessBasePageForm {

    //工作空间id
    private String workspaceId;
    //任务名称
    private String taskName;
    //任务状态
    private String taskStatus;
    //调度执行状态
    private String dispatchStatus;
    //调度类型
    private String dispatchType;
    //调度执行时间 -- 开始
    private String dispatchStartTime;
    //调度执行时间 -- 结束
    private String dispatchEndTime;

}
