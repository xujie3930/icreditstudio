package org.apache.dolphinscheduler.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TaskCallBackModel {

    private String processDefinitionId;
    private int taskStatus;
    private Date execTime;

}
