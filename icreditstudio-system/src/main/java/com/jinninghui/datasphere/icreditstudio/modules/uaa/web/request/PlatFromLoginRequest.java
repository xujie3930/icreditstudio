package com.jinninghui.datasphere.icreditstudio.modules.uaa.web.request;

/**
 *
 * @author EDZ
 */
public class PlatFromLoginRequest {
    /**
     * 登录名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
