package com.micro.cloud.modules.system.resource.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/** @author roy */
@ApiModel("组织机构树形查询 Request VO")
public class SysResourceTreeReqVO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "角色id", example = "2345", notes = "点击角色查看权限配置时需传递给后端作为查询条件")
  private String roleId;

  @ApiModelProperty(value = "父级菜单id", example = "2345", notes = "没有上级部门则为空(root节点)")
  private String parentId;

  @ApiModelProperty(value = "菜单名称", example = "用户管理", notes = "模糊匹配")
  private String name;

  @ApiModelProperty(value = "展示状态", example = "1", notes = "参见 SysCommonStatusEnum 枚举类")
  @JsonProperty("status")
  private Boolean status;

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "SysResourceTreeReqVO{"
        + "roleId='"
        + roleId
        + '\''
        + ", parentId='"
        + parentId
        + '\''
        + ", name='"
        + name
        + '\''
        + ", status="
        + status
        + '}';
  }
}
