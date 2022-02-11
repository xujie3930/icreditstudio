package com.micro.cloud.modules.system.dict.vo.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author roy
 */
@ApiModel("字典数据更新 Request VO")
public class SysDictDataUpdateReqVO extends SysDictDataBaseVO {

  @ApiModelProperty(value = "字典数据编号", required = true, example = "1024")
  @NotBlank(message = "字典数据编号不能为空")
  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "SysDictDataUpdateReqVO{" + "id='" + id + '\'' + '}';
  }
}
