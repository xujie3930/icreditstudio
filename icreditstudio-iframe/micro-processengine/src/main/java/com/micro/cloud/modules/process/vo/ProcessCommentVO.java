package com.micro.cloud.modules.process.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.micro.cloud.util.Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * 〈评论视图类对象〉
 *
 * @author roy
 * @create 2021/12/7
 * @since 1.0.0
 */
@ApiModel(value = "流程实例评论视图类")
public class ProcessCommentVO implements Serializable {

  private static final long serialVersionUID = 615649797019263773L;

  @ApiModelProperty(value = "流程实例id")
  private String processInstanceId;

  @ApiModelProperty(value = "用户id")
  private String userId;

  @ApiModelProperty(value = "任务id")
  private String taskId;

  @ApiModelProperty(value = "节点名称")
  private String activityName;

  @ApiModelProperty(value = "签字意见")
  private String comment;

  @ApiModelProperty(value = "处理时间")
  @JsonFormat(pattern = Util.YEAR_MON_DAY_HOUR_MIN_SEC_FORMAT, timezone = "GMT+8")
  private Date operateTime;

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getActivityName() {
    return activityName;
  }

  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Date getOperateTime() {
    return operateTime;
  }

  public void setOperateTime(Date operateTime) {
    this.operateTime = operateTime;
  }

  @Override
  public String toString() {
    return "ProcessCommentVO{" +
        "processInstanceId='" + processInstanceId + '\'' +
        ", userId='" + userId + '\'' +
        ", taskId='" + taskId + '\'' +
        ", activityName='" + activityName + '\'' +
        ", comment='" + comment + '\'' +
        ", operateTime=" + operateTime +
        '}';
  }
}
