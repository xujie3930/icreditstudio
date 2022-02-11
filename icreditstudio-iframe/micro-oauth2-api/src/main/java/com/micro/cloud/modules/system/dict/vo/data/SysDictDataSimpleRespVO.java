package com.micro.cloud.modules.system.dict.vo.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * @author roy
 */
@ApiModel("数据字典精简 Response VO")
public class SysDictDataSimpleRespVO implements Serializable {

    private static final long serialVersionUID = -5775879701801645283L;

    @ApiModelProperty(value = "字典s数据id", required = true, example = "12")
  private String id;

  @ApiModelProperty(value = "字典键值", required = true, example = "1")
  private String value;

  @ApiModelProperty(value = "上级id", required = true, example = "1")
  private String parentId;

  @ApiModelProperty(value = "是否为叶子节点", required = true, example = "true")
  private Boolean isLeaf;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

    public Boolean getLeaf() {
        return isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    @Override
    public String toString() {
        return "SysDictDataSimpleRespVO{" +
            "id='" + id + '\'' +
            ", value='" + value + '\'' +
            ", parentId='" + parentId + '\'' +
            ", isLeaf=" + isLeaf +
            '}';
    }
}
