package com.micro.cloud.modules.process.param;

import com.micro.cloud.modules.form.param.FormDataParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;

/**
 * 〈提交流程所需参数〉
 *
 * @author roy
 * @create 2021/12/2
 * @since 1.0.0
 */
@ApiModel(value = "提交流程所需参数")
public class CommitTaskParam implements Serializable {

  private static final long serialVersionUID = 1207013490102187667L;

  @ApiModelProperty(
      value = "流程实例id",
      required = true,
      example = "Process_1m8om1k:1:537c19a7-4125-11ec-acbf-7aa4c59a6bca")
  @NotBlank(message = "流程实例id不能为空")
  private String processInstanceId;

  @ApiModelProperty(value = "流程表单数据", required = true, example = "")
  private FormDataParam formDataParam;

  @ApiModelProperty(value = "流程出口", required = false, example = "")
  private String lineValue;

  @ApiModelProperty(
      value = "流程表单所需变量",
      required = true,
      example = "creator:[admin,user1,user2...]",
      notes = "用于必要的业务表单参数,方便后续代办已办中的条件查询")
  private Map<String, Object> processVariables;

  @ApiModelProperty(
      value = "下一节点id",
      required = true,
      example = "hq_activity_gccsYb",
      notes = "用于指定节点流转")
  private String nextActivityId;

  @ApiModelProperty(
      value = "流程下一节点操作者",
      required = true,
      example = "nextOperator:[admin_ID,user1_ID,user2_ID...]",
      notes = "用于设置流程下一节点审批人")
  private List<String> nextNodeOperators;

  @ApiModelProperty(value = "签字意见", required = true, example = "同意")
  private String comment;

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public FormDataParam getFormDataParam() {
    return formDataParam;
  }

  public void setFormDataParam(FormDataParam formDataParam) {
    this.formDataParam = formDataParam;
  }

  public Map<String, Object> getProcessVariables() {
    return processVariables;
  }

  public void setProcessVariables(Map<String, Object> processVariables) {
    this.processVariables = processVariables;
  }

  public String getLineValue() {
    return lineValue;
  }

  public void setLineValue(String lineValue) {
    this.lineValue = lineValue;
  }

  public String getNextActivityId() {
    return nextActivityId;
  }

  public void setNextActivityId(String nextActivityId) {
    this.nextActivityId = nextActivityId;
  }

  public List<String> getNextNodeOperators() {
    return nextNodeOperators;
  }

  public void setNextNodeOperators(List<String> nextNodeOperators) {
    this.nextNodeOperators = nextNodeOperators;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  @Override
  public String toString() {
    return "CommitTaskParam{"
        + "processInstanceId='"
        + processInstanceId
        + '\''
        + ", formDataParam="
        + formDataParam
        + ", lineValue='"
        + lineValue
        + '\''
        + ", processVariables="
        + processVariables
        + ", nextNodeOperators="
        + nextNodeOperators
        + ", comment='"
        + comment
        + '\''
        + '}';
  }
}
