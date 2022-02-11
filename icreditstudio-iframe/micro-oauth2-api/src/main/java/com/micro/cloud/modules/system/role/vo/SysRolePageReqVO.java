package com.micro.cloud.modules.system.role.vo;

import com.micro.cloud.api.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;

/** @author roy */
@ApiModel("角色分页 Request VO")
public class SysRolePageReqVO extends PageParam {

  @ApiModelProperty(value = "角色名称", example = "系统管理员", notes = "模糊匹配")
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "SysRolePageReqVO{" + "name='" + name + '\'' + '}';
  }
}
