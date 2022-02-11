package com.micro.cloud.modules.system.role.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/** @author roy */
@ApiModel("角色信息 Response VO")
public class RoleInfoDto implements Serializable {

  @ApiModelProperty(value = "角色id", required = true, example = "1")
  private String id;

  @ApiModelProperty(value = "角色名称", required = true, example = "系统用户")
  private String name;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "SysRoleRespVO{" + "id='" + id + '\'' + '}';
  }
}
