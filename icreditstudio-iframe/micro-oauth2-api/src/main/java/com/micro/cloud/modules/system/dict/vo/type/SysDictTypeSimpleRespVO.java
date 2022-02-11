package com.micro.cloud.modules.system.dict.vo.type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author roy
 */
@ApiModel("字典类型精简信息 Response VO")
public class SysDictTypeSimpleRespVO {

  @ApiModelProperty(value = "字典类型编号", required = true, example = "1024")
  private String id;

  @ApiModelProperty(value = "字典类型名称", required = true, example = "行政区划")
  private String name;

  @ApiModelProperty(value = "字典类型", required = true, example = "sys_common_dict")
  private String type;

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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "SysDictTypeSimpleRespVO{"
        + "id='"
        + id
        + '\''
        + ", name='"
        + name
        + '\''
        + ", type='"
        + type
        + '\''
        + '}';
  }
}
