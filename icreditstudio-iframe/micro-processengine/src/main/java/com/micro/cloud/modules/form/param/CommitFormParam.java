package com.micro.cloud.modules.form.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 〈提交表单参数〉
 *
 * @author roy
 * @create 2021/12/3
 * @since 1.0.0
 */
@ApiModel(value = "提交表单参数")
public class CommitFormParam implements Serializable {

  private static final long serialVersionUID = 1110891169823648241L;

  @ApiModelProperty(
      value = "流程定义",
      required = true,
      example = "Process_holiday",
      notes = "用于查询流程对应表单字段")
  @NotBlank(message = "流程processKey不能为空")
  private String processKey;

  @ApiModelProperty(value = "表单参数", required = true, example = "json:", notes = "表单参数")
  @NotNull(message = "表单参数不能为空")
  private Map<String, Object> params;

  public String getProcessKey() {
    return processKey;
  }

  public void setProcessKey(String processKey) {
    this.processKey = processKey;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public void setParams(Map<String, Object> params) {
    this.params = params;
  }

  @Override
  public String toString() {
    return "CommitFormParam{" + "processKey='" + processKey + '\'' + ", params=" + params + '}';
  }
}
