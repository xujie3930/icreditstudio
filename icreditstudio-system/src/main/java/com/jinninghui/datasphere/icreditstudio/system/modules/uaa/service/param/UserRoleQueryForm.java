package com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.param;

import java.io.Serializable;

/**
 * @author liyanhui
 */
public class UserRoleQueryForm implements Serializable {

    private static final long serialVersionUID = -7485135356674520338L;
    private String applicationId;
    private String userId;

    public UserRoleQueryForm() {
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
