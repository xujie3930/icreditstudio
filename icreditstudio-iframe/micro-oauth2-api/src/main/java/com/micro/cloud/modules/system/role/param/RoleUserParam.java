package com.micro.cloud.modules.system.role.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/**
 * 〈角色相关用户查询参数〉
 *
 * @author roy
 * @create 2021/12/10
 * @since 1.0.0
 */
public class RoleUserParam implements Serializable {

  private static final long serialVersionUID = 4300363416243705481L;

  @ApiModelProperty(value = "角色id", required = true)
  @NotBlank(message = "角色id不能为空")
  private String roleId;

  @ApiModelProperty(value = "用户名", required = false)
  private String userName;

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Override
  public String toString() {
    return "RoleUserParam{" + "roleId='" + roleId + '\'' + ", userName='" + userName + '\'' + '}';
  }
}
