package com.jinninghui.datasphere.icreditstudio.modules.uaa.service.param;

/**
 * @Author: jidonglin
 * @Date: 2020/1/3 17:33
 */
public class UserRoleResRequest {
    private String userAccountId;
    private String userId;
    private String applicationId;

    public String getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(String userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}

