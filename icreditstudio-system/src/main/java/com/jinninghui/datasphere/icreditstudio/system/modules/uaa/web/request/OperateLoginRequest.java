package com.jinninghui.datasphere.icreditstudio.system.modules.uaa.web.request;

/**
 * @Author: jidonglin
 * @Date: 2019/8/5 10:42
 */
public class OperateLoginRequest {
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 密码
     */
    private String password;

    private String applicationId;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}
