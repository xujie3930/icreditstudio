package com.jinninghui.datasphere.icreditstudio.datasync.model;

import lombok.Data;

import java.util.Date;

@Data
public class TaskCallBackModel {

    private String processDefinitionId;
    private int taskStatus;
    private Date execTime;

}
