package com.micro.cloud.modules.system.role.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;

public class SysRoleResourceExternalVo {


    /**
     * 角色ID
     */
    @TableField(value = "sys_role_id")
    @ApiModelProperty(value="角色ID ")
    private String roleId;

    /**
     * 菜单项ID
     */
    @TableField(value = "sys_resource_id")
    @ApiModelProperty(value="菜单项ID")
    private String menuId;

    @TableField(value = "idx")
    private String idx;
    /**
     * left 0 right 1
     */
    @TableField(value = "layout")
    @ApiModelProperty(value="left 0 right 1")
    private String leftRight;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    @ApiModelProperty(value="角色名称")
    private String roleName;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getLeftRight() {
        return leftRight;
    }

    public void setLeftRight(String leftRight) {
        this.leftRight = leftRight;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
