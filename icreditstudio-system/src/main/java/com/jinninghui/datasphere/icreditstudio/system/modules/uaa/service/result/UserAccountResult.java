package com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.result;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @author liyanhui
 */
public class UserAccountResult implements Serializable {
    private static final long serialVersionUID = 1234891504985522440L;
    private String id;
    private String userId;
    private String identityType;
    private String identifier;
    private String credential;
    private Integer loginMode;
    private String lastLoginIp;
    private Long lastLoginTime;
    private Short status;
    private Short accountLocked;
    private Long credentialExpired;
    private Long accountExpired;
    private Long createTime;
    private Long lastUpdateTime;

    public UserAccountResult() {
    }

    public boolean isAccountExpired() {
        if (this.accountExpired != null && this.accountExpired != 0L) {
            return System.currentTimeMillis() > this.accountExpired;
        } else {
            return false;
        }
    }

    public boolean isCredentialExpired() {
        if (this.credentialExpired != null && this.credentialExpired != 0L) {
            return System.currentTimeMillis() > this.credentialExpired;
        } else {
            return false;
        }
    }

    @JsonIgnore
    public boolean isEnabled() {
        return this.status != null && this.status == 0;
    }

    @JsonIgnore
    public boolean isAccountNonLocked() {
        return this.accountLocked != null && this.accountLocked == 0;
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getIdentityType() {
        return this.identityType;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getCredential() {
        return this.credential;
    }

    public Integer getLoginMode() {
        return this.loginMode;
    }

    public String getLastLoginIp() {
        return this.lastLoginIp;
    }

    public Long getLastLoginTime() {
        return this.lastLoginTime;
    }

    public Long getCredentialExpired() {
        return this.credentialExpired;
    }

    public Long getAccountExpired() {
        return this.accountExpired;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public Long getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public void setLoginMode(Integer loginMode) {
        this.loginMode = loginMode;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public void setCredentialExpired(Long credentialExpired) {
        this.credentialExpired = credentialExpired;
    }

    public void setAccountExpired(Long accountExpired) {
        this.accountExpired = accountExpired;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Short getStatus() {
        return this.status;
    }

    public Short getAccountLocked() {
        return this.accountLocked;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public void setAccountLocked(Short accountLocked) {
        this.accountLocked = accountLocked;
    }
}
