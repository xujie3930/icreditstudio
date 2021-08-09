package com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.result;

import com.hashtech.businessframework.result.CommonOuterResponse;

import java.util.List;

/**
 * @Author: jidonglin
 * @Date: 2019/8/5 10:33
 */
public class OperateLoginResponse extends CommonOuterResponse {
    private String userId;
    private String userAccountId;
    private String token;

    private UserEntityResult userInfoResult;

    private List<RoleEntityResult> roleInfoResultList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(String userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserEntityResult getUserInfoResult() {
        return userInfoResult;
    }

    public void setUserInfoResult(UserEntityResult userInfoResult) {
        this.userInfoResult = userInfoResult;
    }

    public List<RoleEntityResult> getRoleInfoResultList() {
        return roleInfoResultList;
    }

    public void setRoleInfoResultList(List<RoleEntityResult> roleInfoResultList) {
        this.roleInfoResultList = roleInfoResultList;
    }
}
