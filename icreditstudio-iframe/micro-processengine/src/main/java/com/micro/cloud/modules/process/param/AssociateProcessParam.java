package com.micro.cloud.modules.process.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/**
 * 〈关联流程请求参数〉
 *
 * @author roy
 * @create 2021/12/18
 * @since 1.0.0
 */
public class AssociateProcessParam implements Serializable {

  private static final long serialVersionUID = 5017246875419831816L;

  @ApiModelProperty(value = "被关联流程识别码", required = true, notes = "被关联流程详情查询参数")
  @NotBlank(message = "被关联流程processKey不能为空")
  private String processKey;

  @ApiModelProperty(value = "流程实例id", required = true, notes = "被关联流程详情查询参数")
  @NotBlank(message = "被关联流程实例id不能为空")
  private String processInstanceId;

  @ApiModelProperty(value = "流程名称", required = true, notes = "关联流程数据展示")
  @NotBlank(message = "流程名称不能为空")
  private String processDefinitionName;

  @ApiModelProperty(value = "流程名称", required = true, notes = "被关联流程详情查询参数")
  @NotBlank(message = "processDefinitionId不能为空")
  private String processDefinitionId;

  @ApiModelProperty(value = "业务主键id", required = true, notes = "被关联流程详情查询参数")
  @NotBlank(message = "被关联业务数据id不能为空")
  private String businessId;

  public String getProcessKey() {
    return processKey;
  }

  public void setProcessKey(String processKey) {
    this.processKey = processKey;
  }

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getBusinessId() {
    return businessId;
  }

  public void setBusinessId(String businessId) {
    this.businessId = businessId;
  }

  public String getProcessDefinitionName() {
    return processDefinitionName;
  }

  public void setProcessDefinitionName(String processDefinitionName) {
    this.processDefinitionName = processDefinitionName;
  }

  public String getProcessDefinitionId() {
    return processDefinitionId;
  }

  public void setProcessDefinitionId(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
  }

  @Override
  public String toString() {
    return "AssociateProcessParam{" +
        "processKey='" + processKey + '\'' +
        ", processInstanceId='" + processInstanceId + '\'' +
        ", processDefinitionName='" + processDefinitionName + '\'' +
        ", processDefinitionId='" + processDefinitionId + '\'' +
        ", businessId='" + businessId + '\'' +
        '}';
  }
}
