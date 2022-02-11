package com.micro.cloud.modules.system.resource.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** @author roy */
@ApiModel("菜单列表 Request VO")
public class SysResourceListReqVO {

  @ApiModelProperty(value = "菜单名称", example = "用户管理", notes = "模糊匹配")
  private String name;

  @ApiModelProperty(value = "展示状态", example = "true", notes = "参见 SysCommonStatusEnum 枚举类")
  private Boolean status;

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
    return "SysResourceListReqVO{" + "name='" + name + '\'' + ", status=" + status + '}';
  }
}
