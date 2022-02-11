package com.micro.cloud.modules.system.role.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** @author roy */
@ApiModel("角色信息 Response VO")
public class SysRoleRespVO extends SysRoleBaseVO {

  private static final long serialVersionUID = -8766283306786203850L;

  @ApiModelProperty(value = "角色id", required = true, example = "1")
  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "SysRoleRespVO{" + "id='" + id + '\'' + '}';
  }
}
