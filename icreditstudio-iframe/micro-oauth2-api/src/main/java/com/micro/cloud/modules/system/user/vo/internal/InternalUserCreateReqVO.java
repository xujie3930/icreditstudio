package com.micro.cloud.modules.system.user.vo.internal;

import com.micro.cloud.modules.system.user.vo.SysUserBaseVO;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

/**
 * 新增系统用户请求参数
 *
 * @author EDZ
 * @since 2021-11-05
 */
public class InternalUserCreateReqVO extends SysUserBaseVO {

  @ApiModelProperty(value = "密码", required = true, example = "123456")
  @NotEmpty(message = "密码不能为空")
  private String password;

  @ApiModelProperty(value = "用户组织机构信息")
  @NotEmpty(message = "用户组织机构信息不能为空")
  private String orgId;

  @ApiModelProperty(value = "用户角色")
  @NotEmpty(message = "用户角色信息不能为空")
  private String roleId;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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
    return "InternalUserCreateReqVO{"
        + "password='"
        + password
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
