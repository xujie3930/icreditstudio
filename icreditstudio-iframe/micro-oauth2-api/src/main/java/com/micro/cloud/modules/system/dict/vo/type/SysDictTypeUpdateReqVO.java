package com.micro.cloud.modules.system.dict.vo.type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("字典类型更新 Request VO")
public class SysDictTypeUpdateReqVO extends SysDictTypeBaseVO {

  @ApiModelProperty(value = "字典类型编号", required = true, example = "1024")
  @NotNull(message = "字典类型编号不能为空")
  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "SysDictTypeUpdateReqVO{" + "id='" + id + '\'' + '}';
  }
}
