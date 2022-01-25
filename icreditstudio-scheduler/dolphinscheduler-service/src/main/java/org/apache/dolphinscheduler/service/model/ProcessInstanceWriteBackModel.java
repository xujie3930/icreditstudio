package org.apache.dolphinscheduler.service.model;

import lombok.Data;

@Data
public class ProcessInstanceWriteBackModel {

    private String fileName;
    private String processInstanceId;

}
