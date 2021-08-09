package com.jinninghui.datasphere.icreditstudio.modules.uaa.service.result;

import java.util.List;

/**
 * @Author: jidonglin
 * @Date: 2020/1/3 17:30
 */
public class UserRoleResResult {
    private List<UserEntityResult> userInfoResults;
    private List<RoleEntityResult> roleInfoResults;

    public List<UserEntityResult> getUserInfoResults() {
        return userInfoResults;
    }

    public void setUserInfoResults(List<UserEntityResult> userInfoResults) {
        this.userInfoResults = userInfoResults;
    }

    public List<RoleEntityResult> getRoleInfoResults() {
        return roleInfoResults;
    }

    public void setRoleInfoResults(List<RoleEntityResult> roleInfoResults) {
        this.roleInfoResults = roleInfoResults;
    }
}
