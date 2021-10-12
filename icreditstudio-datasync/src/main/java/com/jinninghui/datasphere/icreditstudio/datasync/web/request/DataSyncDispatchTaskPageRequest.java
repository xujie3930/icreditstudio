package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

@Data
public class DataSyncDispatchTaskPageRequest extends BusinessBasePageForm {

    //工作空间id
    private String workspaceId;
    //任务名称
    private String taskName;
    //任务状态
    private String taskStatus;
    //任务类型
    private String taskType;
    //任务执行时间 -- 开始
    private String taskStartTime;
    //任务执行时间 -- 结束
    private String taskEndTime;

}
