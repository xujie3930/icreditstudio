package com.micro.cloud.modules.process.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.micro.cloud.util.Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * 〈可退回节点〉
 *
 * @author roy
 * @create 2021/12/7
 * @since 1.0.0
 */
@ApiModel(value = "历史节点")
public class HistoricNode implements Serializable {

  private static final long serialVersionUID = -4870973247208190387L;

  @ApiModelProperty(value = "操作步骤:1,2,3,4...")
  private Integer step;

  @ApiModelProperty(value = "流程实例id")
  private String processInstanceId;

  @ApiModelProperty(value = "流程任务id")
  private String taskId;

  @ApiModelProperty(value = "流程节点id")
  private String activityId;

  @ApiModelProperty(value = "流程节点名称")
  private String activityName;

  @ApiModelProperty(value = "节点操作者id")
  private String assignee;

  @ApiModelProperty(value = "节点操作者名称")
  private String assigneeName;

  @ApiModelProperty(value = "操作者所属部门")
  private String assigneeDept;

  @ApiModelProperty(value = "操作日期")
  @JsonFormat(pattern = Util.YEAR_MON_DAY_HOUR_MIN_SEC_FORMAT, timezone = "GMT+8")
  private Date operateDate;

  public Integer getStep() {
    return step;
  }

  public void setStep(Integer step) {
    this.step = step;
  }

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }

  public String getActivityName() {
    return activityName;
  }

  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public String getAssigneeDept() {
    return assigneeDept;
  }

  public String getAssigneeName() {
    return assigneeName;
  }

  public void setAssigneeName(String assigneeName) {
    this.assigneeName = assigneeName;
  }

  public void setAssigneeDept(String assigneeDept) {
    this.assigneeDept = assigneeDept;
  }

  public Date getOperateDate() {
    return operateDate;
  }

  public void setOperateDate(Date operateDate) {
    this.operateDate = operateDate;
  }

  @Override
  public String toString() {
    return "HistoricNode{" +
        "step=" + step +
        ", processInstanceId='" + processInstanceId + '\'' +
        ", taskId='" + taskId + '\'' +
        ", activityId='" + activityId + '\'' +
        ", activityName='" + activityName + '\'' +
        ", assignee='" + assignee + '\'' +
        ", assigneeName='" + assigneeName + '\'' +
        ", assigneeDept='" + assigneeDept + '\'' +
        ", operateDate=" + operateDate +
        '}';
  }
}
