package com.micro.cloud.modules.process.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 〈流程实例返回值〉
 *
 * @author roy
 * @create 2021/12/7
 * @since 1.0.0
 */
public class ProcessInstanceVO implements Serializable {

  @ApiModelProperty(
      value = "流程实例id",
      example = "0938cfca-563b-11ec-ac65-8e8590975466",
      required = true,
      notes = "流程实例id")
  private String processInstanceId;

  @ApiModelProperty(
      value = "流程定义id",
      example = "Process_huiqian:4:81bee215-41fb-11ec-a133-5e5331362596",
      required = true,
      notes = "definition_key:version:processDefinitionId")
  private String processDefinitionId;

  @ApiModelProperty(
      value = "流程节点id",
      example = "Activity_0aite94",
      required = true,
      notes = "用于获取当前节点所属步骤")
  private String activityId;

  @ApiModelProperty(value = "业务id", example = "910626036754939904", required = true, notes = "业务id")
  private String businessId;

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

  public String getProcessDefinitionId() {
    return processDefinitionId;
  }

  public void setProcessDefinitionId(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
  }

  public String getBusinessId() {
    return businessId;
  }

  public void setBusinessId(String businessId) {
    this.businessId = businessId;
  }

  @Override
  public String toString() {
    return "ProcessInstanceVO{"
        + "processInstanceId='"
        + processInstanceId
        + '\''
        + ", processDefinitionId='"
        + processDefinitionId
        + '\''
        + ", activityId='"
        + activityId
        + '\''
        + ", businessId='"
        + businessId
        + '\''
        + '}';
  }
}
