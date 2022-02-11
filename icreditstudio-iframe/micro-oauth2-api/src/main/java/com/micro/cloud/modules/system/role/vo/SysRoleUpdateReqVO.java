package com.micro.cloud.modules.system.role.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

@ApiModel("角色更新 Request VO")
public class SysRoleUpdateReqVO extends SysRoleBaseVO implements Serializable {

  @ApiModelProperty(value = "角色id", required = true, example = "1024")
  @NotNull(message = "角色id不能为空")
  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "SysRoleUpdateReqVO{" + "id='" + id + '\'' + '}';
  }
}
