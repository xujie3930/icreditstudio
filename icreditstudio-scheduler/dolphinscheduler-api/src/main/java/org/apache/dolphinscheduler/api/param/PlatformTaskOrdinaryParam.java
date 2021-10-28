package org.apache.dolphinscheduler.api.param;

import lombok.Data;
import org.apache.dolphinscheduler.common.enums.ReleaseState;

/**
 * @author Peng
 */
@Data
public class PlatformTaskOrdinaryParam {
    /**
     * 启用/停用
     */
    private Integer enable;
    /**
     * 流程名称
     */
    private String name;
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
    /**
     * 超时时间
     */
    private int timeOut;

    public ReleaseState getReleaseState() {
        if (enable == null) {
            return ReleaseState.OFFLINE;
        }
        return ReleaseState.getEnum(enable);
    }
}