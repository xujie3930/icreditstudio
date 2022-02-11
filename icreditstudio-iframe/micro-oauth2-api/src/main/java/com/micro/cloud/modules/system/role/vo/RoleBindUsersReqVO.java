package com.micro.cloud.modules.system.role.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

/**
 * 〈角色绑定人员请求信息〉
 *
 * @author roy
 * @create 2021/11/11
 * @since 1.0.0
 */
@ApiModel("角色绑定人员请求信息")
public class RoleBindUsersReqVO implements Serializable {

  private static final long serialVersionUID = -2250125493795638553L;

  @ApiModelProperty(value = "角色id", required = true, example = "12345", notes = "角色设置人员时使用")
  private String roleId;

  @ApiModelProperty(value = "用户id", required = true, example = "2345", notes = "角色设置人员时设置")
  private List<String> userIds;

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public List<String> getUserIds() {
    return userIds;
  }

  public void setUserIds(List<String> userIds) {
    this.userIds = userIds;
  }

  @Override
  public String toString() {
    return "RoleBindUsersReqVO{" + "roleId='" + roleId + '\'' + ", userIds=" + userIds + '}';
  }
}
