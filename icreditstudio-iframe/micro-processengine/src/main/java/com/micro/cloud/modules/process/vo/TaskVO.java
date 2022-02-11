package com.micro.cloud.modules.process.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.micro.cloud.util.Util;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * 待办，已办视图展示
 *
 * @author roy
 */
public class TaskVO implements Serializable {

  private static final long serialVersionUID = 6017214132080595945L;

  @ApiModelProperty(value = "流程当前节点id")
  private String activityId;

  @ApiModelProperty(value = "流程当前节点名称")
  private String activityName;

  @ApiModelProperty(value = "项目名称")
  private String projectName;

  @ApiModelProperty(value = "流程类型")
  private String processKey;

  @ApiModelProperty(value = "创建人")
  private String creator;

  @ApiModelProperty(value = "处理人")
  private String assignee;

  @ApiModelProperty(value = "处理人姓名")
  private String assigneeName;

  @ApiModelProperty(value = "组织机构信息")
  private String orgName;

  @ApiModelProperty(value = "业务主键id")
  private String businessId;

  @ApiModelProperty(value = "创建时间")
  @JsonFormat(pattern = Util.YEAR_MON_DAY_HOUR_MIN_SEC_FORMAT, timezone = "GMT+8")
  private Date createTime;

  @ApiModelProperty(value = "描述信息")
  private String description;

  @ApiModelProperty(value = "流程定义id")
  private String processDefinitionId;

  @ApiModelProperty(value = "流程实例id")
  private String processInstanceId;

  @ApiModelProperty(value = "流程名称")
  private String processDefinitionName;

  @ApiModelProperty(value = "任务识别码")
  private String taskDefinitionKey;

  @ApiModelProperty(value = "前端页面地址")
  private String formUrl;

  @ApiModelProperty(value = "标段名称", notes = "标段名称(关联流程时展示字段)")
  private String bdmc;

  @ApiModelProperty(value = "标段划分", notes = "标段划分(关联流程时展示字段)")
  private String bdhf;

  @ApiModelProperty(
      value = "选择类型",
      required = false,
      example = "1:重大 2:一般 3:限额",
      notes = "针对变更立项流程单独设置,前端依此判断页面路由地址")
  private String type;

  @ApiModelProperty(value = "结束时间")
  @JsonFormat(pattern = Util.YEAR_MON_DAY_HOUR_MIN_SEC_FORMAT, timezone = "GMT+8")
  private Date endTime;

  @ApiModelProperty(value = "流程状态")
  private Boolean isCompleted;

  public String getProcessKey() {
    return processKey;
  }

  public void setProcessKey(String processKey) {
    this.processKey = processKey;
  }

  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
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

  public String getAssigneeName() {
    return assigneeName;
  }

  public void setAssigneeName(String assigneeName) {
    this.assigneeName = assigneeName;
  }

  public String getOrgName() {
    return orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public String getBusinessId() {
    return businessId;
  }

  public void setBusinessId(String businessId) {
    this.businessId = businessId;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getProcessDefinitionId() {
    return processDefinitionId;
  }

  public void setProcessDefinitionId(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
  }

  public String getProcessDefinitionName() {
    return processDefinitionName;
  }

  public void setProcessDefinitionName(String processDefinitionName) {
    this.processDefinitionName = processDefinitionName;
  }

  public String getTaskDefinitionKey() {
    return taskDefinitionKey;
  }

  public void setTaskDefinitionKey(String taskDefinitionKey) {
    this.taskDefinitionKey = taskDefinitionKey;
  }

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getBdmc() {
    return bdmc;
  }

  public void setBdmc(String bdmc) {
    this.bdmc = bdmc;
  }

  public String getBdhf() {
    return bdhf;
  }

  public void setBdhf(String bdhf) {
    this.bdhf = bdhf;
  }

  public String getFormUrl() {
    return formUrl;
  }

  public void setFormUrl(String formUrl) {
    this.formUrl = formUrl;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Boolean getCompleted() {
    return isCompleted;
  }

  public void setCompleted(Boolean completed) {
    isCompleted = completed;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  @Override
  public String toString() {
    return "TaskVO{" +
        "activityId='" + activityId + '\'' +
        ", activityName='" + activityName + '\'' +
        ", projectName='" + projectName + '\'' +
        ", processKey='" + processKey + '\'' +
        ", creator='" + creator + '\'' +
        ", assignee='" + assignee + '\'' +
        ", assigneeName='" + assigneeName + '\'' +
        ", orgName='" + orgName + '\'' +
        ", businessId='" + businessId + '\'' +
        ", createTime=" + createTime +
        ", description='" + description + '\'' +
        ", processDefinitionId='" + processDefinitionId + '\'' +
        ", processInstanceId='" + processInstanceId + '\'' +
        ", processDefinitionName='" + processDefinitionName + '\'' +
        ", taskDefinitionKey='" + taskDefinitionKey + '\'' +
        ", formUrl='" + formUrl + '\'' +
        ", bdmc='" + bdmc + '\'' +
        ", bdhf='" + bdhf + '\'' +
        ", type='" + type + '\'' +
        ", endTime=" + endTime +
        ", isCompleted=" + isCompleted +
        '}';
  }
}
