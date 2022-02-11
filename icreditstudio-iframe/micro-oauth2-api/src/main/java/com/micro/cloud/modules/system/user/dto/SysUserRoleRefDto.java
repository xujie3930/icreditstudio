package com.micro.cloud.modules.system.user.dto;

import com.micro.cloud.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * @author EDZ
 * @since 2021-11-05
 */
@ApiModel(value = "SysUserRoleRef对象", description = "")
public class SysUserRoleRefDto extends BaseDO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("用户id")
  private String userId;

  @ApiModelProperty("角色id")
  private String roleId;

  @ApiModelProperty("角色名称")
  private String roleName;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  @Override
  public String toString() {
    return "SysUserRoleRefDto{"
        + "userId='"
        + userId
        + '\''
        + ", roleName='"
        + roleName
        + '\''
        + '}';
  }
}
