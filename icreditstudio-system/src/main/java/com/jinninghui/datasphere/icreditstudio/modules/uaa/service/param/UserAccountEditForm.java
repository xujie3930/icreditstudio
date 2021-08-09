package com.jinninghui.datasphere.icreditstudio.modules.uaa.service.param;

import java.io.Serializable;

/**
 * @author liyanhui
 */
public class UserAccountEditForm implements Serializable {
    private static final long serialVersionUID = 5953232257043628792L;
    private String id;
    private String userId;
    private String credential;
    private Long credentialExpired;
    private Long accountExpired;
    private Long lastLoginTime;

    public UserAccountEditForm() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getCredential() {
        return this.credential;
    }

    public Long getCredentialExpired() {
        return this.credentialExpired;
    }

    public Long getAccountExpired() {
        return this.accountExpired;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public void setCredentialExpired(Long credentialExpired) {
        this.credentialExpired = credentialExpired;
    }

    public void setAccountExpired(Long accountExpired) {
        this.accountExpired = accountExpired;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
