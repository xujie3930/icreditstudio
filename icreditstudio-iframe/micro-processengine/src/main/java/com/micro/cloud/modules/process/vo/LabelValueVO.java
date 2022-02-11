package com.micro.cloud.modules.process.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class LabelValueVO implements Serializable {

  private static final long serialVersionUID = -2759591119678316084L;

  @ApiModelProperty(value = "节点名称")
  private String label;

  @ApiModelProperty(value = "变量值")
  private String value;

  @ApiModelProperty(value = "节点id")
  private String activityId;

  @ApiModelProperty(value = "是否为多实例节点(会签)")
  private Boolean isMultiNode;

  public LabelValueVO() {}

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }

  public Boolean getMultiNode() {
    return isMultiNode;
  }

  public void setMultiNode(Boolean multiNode) {
    isMultiNode = multiNode;
  }

  @Override
  public String toString() {
    return "LabelValueVO{" +
        "label='" + label + '\'' +
        ", value='" + value + '\'' +
        ", activityId='" + activityId + '\'' +
        ", isMultiNode=" + isMultiNode +
        '}';
  }
}
