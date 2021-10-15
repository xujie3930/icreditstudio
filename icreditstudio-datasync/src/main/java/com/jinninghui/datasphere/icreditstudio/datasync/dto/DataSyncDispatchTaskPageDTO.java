package com.jinninghui.datasphere.icreditstudio.datasync.dto;

import lombok.Data;

@Data
public class DataSyncDispatchTaskPageDTO {

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
    //任务执行时间 -- 开始
    private String dispatchStartTime;
    //任务执行时间 -- 结束
    private String dispatchEndTime;
    //页数
    private int pageNum = 1;
    //一页显示数量
    private int pageSize = 10;
    //用来排序的字段
    private String orderBy;
    //排序顺序 DESC & ASC
    private String order;

}
