package org.apache.dolphinscheduler.api.request;

import lombok.Data;

@Data
public class ExecInstanceRequest {

    private String processInstanceId;
    private String execType;

}
