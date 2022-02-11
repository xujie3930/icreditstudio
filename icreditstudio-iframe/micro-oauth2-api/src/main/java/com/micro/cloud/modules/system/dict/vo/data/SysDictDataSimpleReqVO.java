package com.micro.cloud.modules.system.dict.vo.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/** @author roy */
@ApiModel("数据字典精简 Request VO")
public class SysDictDataSimpleReqVO implements Serializable {

  private static final long serialVersionUID = 6565514190964208666L;

  @ApiModelProperty(value = "字典数据id", required = true, example = "12")
  private String id;

  @ApiModelProperty(value = "字典类型", required = true, example = "1")
  private String type;

  @ApiModelProperty(value = "是否为叶子节点", required = true, example = "true")
  private Boolean isLeaf;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Boolean getLeaf() {
    return isLeaf;
  }

  public void setLeaf(Boolean leaf) {
    isLeaf = leaf;
  }

  @Override
  public String toString() {
    return "SysDictDataSimpleReqVO{"
        + "id='"
        + id
        + '\''
        + ", type='"
        + type
        + '\''
        + ", isLeaf="
        + isLeaf
        + '}';
  }
}
