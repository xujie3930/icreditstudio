package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

@Data
public class DataSyncDispatchTaskPageRequest extends BusinessBasePageForm {

    //工作空间id
    private String workspaceId;
    //当前登陆人ID
    private String currLoginUserId;
    //任务名称
    private String taskName;
    //任务状态
    private Integer taskStatus;
    //调度执行状态
    private Integer dispatchStatus;
    //调度类型
    private Integer dispatchType;
    //调度执行时间 -- 开始
    private Long dispatchStartTime;
    //调度执行时间 -- 结束
    private Long dispatchEndTime;

}
