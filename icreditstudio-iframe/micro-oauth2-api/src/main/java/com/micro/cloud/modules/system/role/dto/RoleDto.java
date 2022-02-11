package com.micro.cloud.modules.system.role.dto;

/**
 * 〈角色信息数据传输工具类〉
 *
 * @author roy
 * @create 2021/11/18
 * @since 1.0.0
 */
public class RoleDto {

    String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "RoleDto{" +
            "roleId='" + roleId + '\'' +
            '}';
    }
}