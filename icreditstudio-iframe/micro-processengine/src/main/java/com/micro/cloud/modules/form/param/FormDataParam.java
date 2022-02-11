package com.micro.cloud.modules.form.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * 〈表单数据〉
 *
 * @author roy
 * @create 2021/12/3
 * @since 1.0.0
 */
@ApiModel(value = "表单数据")
public class FormDataParam implements Serializable {

  private static final long serialVersionUID = -789886467687360382L;

  @ApiModelProperty(
      value = "流程key",
      required = true,
      example = "Example:holiday",
      notes = "流程定义: 新建流程界面可获取每条流程对应流程key")
  @NotBlank(message = "流程key不能为空")
  private String processKey;

  @ApiModelProperty(value = "表单数据", required = true, example = "", notes = "流程表单数据")
  @NotNull(message = "流程表单数据不能为空")
  private Map<String, Object> formData;

  public String getProcessKey() {
    return processKey;
  }

  public void setProcessKey(String processKey) {
    this.processKey = processKey;
  }

  public Map<String, Object> getFormData() {
    return formData;
  }

  public void setFormData(Map<String, Object> formData) {
    this.formData = formData;
  }

  @Override
  public String toString() {
    return "FormDataParam{" + "processKey='" + processKey + '\'' + ", formData=" + formData + '}';
  }
}
