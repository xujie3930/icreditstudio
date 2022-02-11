package com.micro.cloud.modules.system.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** @author roy */
@ApiModel("用户信息更新 Request VO")
public class SysUserUpdateReqVO extends SysUserBaseVO {

  @ApiModelProperty(value = "用户编号", required = true, example = "1024")
  @NotNull(message = "用户编号不能为空")
  private String userId;

  @ApiModelProperty(value = "用户组织机构信息")
  @NotBlank(message = "用户组织机构信息不能为空")
  private String orgId;

  @ApiModelProperty(value = "用户角色")
  @NotBlank(message = "用户角色信息不能为空")
  private String roleId;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  @Override
  public String toString() {
    return "SysUserUpdateReqVO{"
        + "userId='"
        + userId
        + '\''
        + ", orgId='"
        + orgId
        + '\''
        + ", roleId='"
        + roleId
        + '\''
        + '}';
  }
}
