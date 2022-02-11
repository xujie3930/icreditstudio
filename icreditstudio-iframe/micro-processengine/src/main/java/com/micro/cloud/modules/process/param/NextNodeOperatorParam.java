package com.micro.cloud.modules.process.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

/**
 * 〈下一节点操作者请求参数〉
 *
 * @author roy
 * @create 2021/12/7
 * @since 1.0.0
 */
public class NextNodeOperatorParam implements Serializable {

  private static final long serialVersionUID = -7696046142084662980L;

  @ApiModelProperty(
      value = "下一节点操作者参数名称",
      example = "nextOperator",
      required = true,
      notes = "用于流程提交时指定下一节点操作者的流程变量名称")
  private String variableName;

  @ApiModelProperty(
      value = "下一节点操作者id集合",
      example = "123,345,456...",
      required = true,
      notes = "可用于多选，会签节点")
  private List<String> variableValues;

  public String getVariableName() {
    return variableName;
  }

  public void setVariableName(String variableName) {
    this.variableName = variableName;
  }

  public List<String> getVariableValues() {
    return variableValues;
  }

  public void setVariableValues(List<String> variableValues) {
    this.variableValues = variableValues;
  }

  @Override
  public String toString() {
    return "NextNodeOperatorParam{"
        + "variableName='"
        + variableName
        + '\''
        + ", variableValues="
        + variableValues
        + '}';
  }
}
