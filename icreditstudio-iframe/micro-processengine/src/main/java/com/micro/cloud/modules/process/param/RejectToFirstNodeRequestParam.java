package com.micro.cloud.modules.process.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/**
 * 〈退回至第一节点请求参数〉
 *
 * @author roy
 * @create 2021/12/7
 * @since 1.0.0
 */
public class RejectToFirstNodeRequestParam implements Serializable {

  private static final long serialVersionUID = 1996753625922486214L;

  @ApiModelProperty(
      value = "流程实例id",
      required = true,
      example = "0938cfca-563b-11ec-ac65-8e8590975466",
      notes = "当前流程实例id")
  @NotBlank(message = "流程实例id不能为空")
  private String processInstanceId;

  @ApiModelProperty(value = "流程节点id", required = false, notes = "指定节点退回时从历史节点列表中获取")
  private String activityId;

  @ApiModelProperty(value = "操作人", required = true, example = "admin的userid", notes = "默认当前登录人id")
  private String assignee;

  @ApiModelProperty(value = "退回意见", required = false, example = "请核实金额后重新提交")
  private String comment;

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

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  @Override
  public String toString() {
    return "RejectToFirstNodeRequestParam{"
        + "processInstanceId='"
        + processInstanceId
        + '\''
        + ", activityId='"
        + activityId
        + '\''
        + ", assignee='"
        + assignee
        + '\''
        + ", comment='"
        + comment
        + '\''
        + '}';
  }
}
