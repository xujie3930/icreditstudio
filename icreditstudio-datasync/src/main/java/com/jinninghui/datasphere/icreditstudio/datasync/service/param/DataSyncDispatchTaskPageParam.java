package com.jinninghui.datasphere.icreditstudio.datasync.service.param;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

import java.util.Date;

@Data
public class DataSyncDispatchTaskPageParam extends BusinessBasePageForm {

    //工作空间id
    private String workspaceId;
    //任务名称
    private String taskName;
    //任务状态
    private Integer taskStatus;
    //调度执行状态
    private Integer dispatchStatus;
    //调度类型
    private Integer dispatchType;
    //调度执行时间 -- 开始
    private Date dispatchStartTime;
    //调度执行时间 -- 结束
    private Date dispatchEndTime;

}
