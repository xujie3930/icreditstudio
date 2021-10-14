package org.apache.dolphinscheduler.api.request;

import lombok.Data;
import org.apache.dolphinscheduler.dao.entity.User;

/**
 * @author Peng
 */
@Data
public class CreatePlatformTaskRequest {
    /**
     * 访问用户
     */
    private User accessUser;
    /**
     * 所属项目码
     */
    private String projectCode;
    /**
     * 流程定义名称
     */
    private String name;
    /**
     * 流程定义json参数
     */
    private String processDefinitionJson;
    /**
     * 流程定义描述
     */
    private String desc;
}
