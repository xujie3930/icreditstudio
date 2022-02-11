package com.micro.cloud.modules.system.resource.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;

/**
 * @author roy
 */
@ApiModel("菜单更新 Request VO")
public class SysResourceUpdateReqVO extends SysResourceBaseVO {

  @ApiModelProperty(value = "菜单编号", required = true, example = "1024")
  @NotNull(message = "菜单编号不能为空")
  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "SysResourceUpdateReqVO{" + "id=" + id + '}';
  }
}
