package com.micro.cloud.modules.process.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/**
 * 〈流程详情请求参数〉
 *
 * @author roy
 * @create 2021/12/10
 * @since 1.0.0
 */
public class ProcessDetailParam implements Serializable {

  private static final long serialVersionUID = -5505240579874960427L;

  @ApiModelProperty(value = "流程实例id")
  @NotBlank(message = "流程实例id不能为空")
  private String processInstanceId;

  @ApiModelProperty(value = "流程识别码")
  @NotBlank(message = "流程processKey不能为空")
  private String processKey;

  @ApiModelProperty(value = "业务主键id")
  @NotBlank(message = "businessId不能为空")
  private String businessId;

  @ApiModelProperty(value = "当前节点id")
  @NotBlank(message = "当前节点id不能为空")
  private String activityId;

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getProcessKey() {
    return processKey;
  }

  public void setProcessKey(String processKey) {
    this.processKey = processKey;
  }

  public String getBusinessId() {
    return businessId;
  }

  public void setBusinessId(String businessId) {
    this.businessId = businessId;
  }

  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }

  @Override
  public String toString() {
    return "ProcessDetailParam{"
        + "processInstanceId='"
        + processInstanceId
        + '\''
        + ", processKey='"
        + processKey
        + '\''
        + ", businessId='"
        + businessId
        + '\''
        + ", activityId='"
        + activityId
        + '\''
        + '}';
  }
}
