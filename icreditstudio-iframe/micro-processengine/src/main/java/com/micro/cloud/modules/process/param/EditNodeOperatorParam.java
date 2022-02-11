package com.micro.cloud.modules.process.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

/**
 * 〈编辑节点操作者请求参数〉
 *
 * @author roy
 * @create 2021/12/2
 * @since 1.0.0
 */
@ApiModel(value = "节点操作者编辑请求入参")
public class EditNodeOperatorParam implements Serializable {

  private static final long serialVersionUID = 6599875349526701262L;

  @ApiModelProperty(value = "节点操作者")
  private String assignee;

  @ApiModelProperty(value = "节点操作者")
  private List<String> operator;

  @ApiModelProperty(
      value = "流程实例id",
      required = true,
      example = "Process_1m8om1k:1:537c19a7-4125-11ec-acbf-7aa4c59a6bca")
  private String processInstanceId;

  @ApiModelProperty(
      value = "会签节点activityId",
      required = true,
      example = "0f21ecfc-5277-11ec-b2b2-8e8590975466")
  private String activityId;

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }

  public List<String> getOperator() {
    return operator;
  }

  public void setOperator(List<String> operator) {
    this.operator = operator;
  }

  @Override
  public String toString() {
    return "EditNodeOperatorParam{" +
        "assignee='" + assignee + '\'' +
        ", operator=" + operator +
        ", processInstanceId='" + processInstanceId + '\'' +
        ", activityId='" + activityId + '\'' +
        '}';
  }
}
