package com.micro.cloud.modules.system.role.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;

@ApiModel("角色更新状态 Request VO")
public class SysRoleUpdateStatusReqVO {

  @ApiModelProperty(value = "角色id", required = true, example = "1024")
  @NotNull(message = "角色id不能为空")
  private String id;

  @ApiModelProperty(
      value = "状态",
      required = true,
      example = "1",
      notes = "见 SysCommonStatusEnum 枚举")
  @NotNull(message = "状态不能为空")
  private Boolean status;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "SysRoleUpdateStatusReqVO{" + "id='" + id + '\'' + ", status=" + status + '}';
  }
}
