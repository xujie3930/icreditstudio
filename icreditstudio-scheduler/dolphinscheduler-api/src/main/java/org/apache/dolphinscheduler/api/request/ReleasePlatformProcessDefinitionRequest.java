package org.apache.dolphinscheduler.api.request;

import lombok.Data;
import org.apache.dolphinscheduler.dao.entity.User;

/**
 * @author Peng
 */
@Data
public class ReleasePlatformProcessDefinitionRequest {
    /**
     * 访问用户
     */
    private User accessUser;
    /**
     * 项目编码
     */
    String projectCode;
    /**
     * 流程定义ID
     */
    String processDefinitionId;
    /**
     * 上下线状态
     */
    int releaseState;
}
