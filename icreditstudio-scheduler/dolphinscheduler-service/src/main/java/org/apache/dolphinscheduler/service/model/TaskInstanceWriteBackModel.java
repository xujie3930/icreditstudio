package org.apache.dolphinscheduler.service.model;

import lombok.Data;

@Data
public class TaskInstanceWriteBackModel {

    private Long writeSucceedRecords;
    private Long writeSucceedBytes;
    private String taskInstanceId;

}
