package org.apache.dolphinscheduler.api.request;

import lombok.Data;
import org.apache.dolphinscheduler.dao.entity.User;

/**
 * @author Peng
 */
@Data
public class DeletePlatformProcessDefinitionRequest {

    /**
     * 访问用户
     */
    private User accessUser;
    /**
     * 流程定义ID
     */
    private String processDefinitionId;
}
