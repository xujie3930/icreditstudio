package org.apache.dolphinscheduler.api.param;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class PlatformTaskOrdinaryParam {

    /**
     * 所属项目码
     */
    private String projectCode;
    /**
     * 流程定义名称
     */
    private String platformTaskId;
    /**
     * 任务json
     */
    private String taskJson;
}
