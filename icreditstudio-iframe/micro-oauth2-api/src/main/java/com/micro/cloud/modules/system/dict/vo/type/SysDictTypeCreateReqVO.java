package com.micro.cloud.modules.system.dict.vo.type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel("字典类型创建 Request VO")
public class SysDictTypeCreateReqVO extends SysDictTypeBaseVO {

  @ApiModelProperty(value = "字典类型", required = true, example = "sys_common_sex")
  @NotNull(message = "字典类型不能为空")
  @Size(max = 100, message = "字典类型类型长度不能超过100个字符")
  private String type;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "SysDictTypeCreateReqVO{" + "type='" + type + '\'' + '}';
  }
}
