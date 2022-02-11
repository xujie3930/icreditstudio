package com.micro.cloud.modules.process.param;

import com.micro.cloud.api.PageParam;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 〈待办列表请求参数〉
 *
 * @author roy
 * @create 2021/12/7
 * @since 1.0.0
 */
public class ProcessPageParam extends PageParam implements Serializable {

  private static final long serialVersionUID = -795218911348203536L;

  @ApiModelProperty(
      value = "流程实例id",
      required = false,
      example = "0938cfca-563b-11ec-ac65-8e8590975466",
      notes = "后续待办可能会根据流程类型进行分类树形展示")
  private String processInstanceId;

  @ApiModelProperty(value = "处理人id", required = true, example = "admin", notes = "默认为当前登录人员id")
  private String assignee;

  @ApiModelProperty(value = "处理人", required = true, example = "admin", notes = "默认为当前登录人员")
  private String assigneeName;

  @ApiModelProperty(value = "流程名称", required = true, example = "资格预审流程", notes = "流程名称")
  private String processDefinitionName;

  public String getAssigneeName() {
    return assigneeName;
  }

  public void setAssigneeName(String assigneeName) {
    this.assigneeName = assigneeName;
  }

  public String getProcessDefinitionName() {
    return processDefinitionName;
  }

  public void setProcessDefinitionName(String processDefinitionName) {
    this.processDefinitionName = processDefinitionName;
  }

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  @Override
  public String toString() {
    return "ProcessPageParam{" +
        "processInstanceId='" + processInstanceId + '\'' +
        ", assignee='" + assignee + '\'' +
        ", assigneeName='" + assigneeName + '\'' +
        ", processDefinitionName='" + processDefinitionName + '\'' +
        '}';
  }
}
