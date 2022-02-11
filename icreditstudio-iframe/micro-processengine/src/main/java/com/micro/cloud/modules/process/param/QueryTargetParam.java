package com.micro.cloud.modules.process.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/**
 * 〈获取流程下一节点请求参数〉
 *
 * @author roy
 * @create 2021/12/11
 * @since 1.0.0
 */
public class QueryTargetParam implements Serializable {

  private static final long serialVersionUID = 3269200858695955595L;

  @ApiModelProperty(value = "流程节点id", required = true, example = "activity_cbcsjbr")
  @NotBlank(message = "流程节点id不能为空")
  private String activityId;

  @ApiModelProperty(
      value = "流程定义id",
      required = true,
      example = "Process_zbxqb:10:3ffd3a93-5a26-11ec-82d9-02dfb123d3d3")
  @NotBlank(message = "流程定义id不能为空")
  private String processDefinitionId;

  @ApiModelProperty(
      value = "流程实例id",
      required = true,
      example = "0938cfca-563b-11ec-ac65-8e8590975466",
      notes = "后续待办可能会根据流程类型进行分类树形展示")
  private String processInstanceId;

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

  @Override
  public String toString() {
    return "QueryTargetParam{"
        + "activityId='"
        + activityId
        + '\''
        + ", processDefinitionId='"
        + processDefinitionId
        + '\''
        + ", processInstanceId='"
        + processInstanceId
        + '\''
        + '}';
  }
}
