package com.micro.cloud.modules.system.role.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 〈角色绑定人员请求信息〉
 *
 * @author roy
 * @create 2021/11/11
 * @since 1.0.0
 */
@ApiModel("角色绑定人员请求信息")
public class RoleBindResourcesReqVO implements Serializable {

  private static final long serialVersionUID = -2250125493795638553L;

  @ApiModelProperty(value = "角色id", required = true, example = "12345", notes = "角色设置人员时使用")
  @NotBlank(message = "角色id不能为空")
  private String roleId;

  @ApiModelProperty(value = "菜单资源id", required = true, notes = "角色设置权限列表时使用")
  @NotNull(message = "权限列表不能为空")
  private List<ResourceBindInfoVo> resourceIds;

  @ApiModelProperty(value = "菜单半选列表", required = false, notes = "角色设置权限列表时使用")
  private List<String> halfCheckedKeys;

  public RoleBindResourcesReqVO() {}

  public RoleBindResourcesReqVO(String roleId, List<ResourceBindInfoVo> resourceIds) {
    this.roleId = roleId;
    this.resourceIds = resourceIds;
  }

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public List<ResourceBindInfoVo> getResourceIds() {
    return resourceIds;
  }

  public void setResourceIds(List<ResourceBindInfoVo> resourceIds) {
    this.resourceIds = resourceIds;
  }

  public List<String> getHalfCheckedKeys() {
    return halfCheckedKeys;
  }

  public void setHalfCheckedKeys(List<String> halfCheckedKeys) {
    this.halfCheckedKeys = halfCheckedKeys;
  }

  @Override
  public String toString() {
    return "RoleBindResourcesReqVO{"
        + "roleId='"
        + roleId
        + '\''
        + ", resourceIds="
        + resourceIds
        + ", halfCheckedKeys="
        + halfCheckedKeys
        + '}';
  }
}
