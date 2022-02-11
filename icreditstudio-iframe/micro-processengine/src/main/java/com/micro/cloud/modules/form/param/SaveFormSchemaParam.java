package com.micro.cloud.modules.form.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 〈保存表单字段请求参数〉
 *
 * @author roy
 * @create 2021/12/3
 * @since 1.0.0
 */
@ApiModel(value = "表单字段保存请求参数")
public class SaveFormSchemaParam implements Serializable {

  private static final long serialVersionUID = -441031791587113123L;

  @ApiModelProperty(
      value = "流程识别码",
      required = true,
      example = "Process_holiday",
      notes = "用于关联流程定义，方便流程表单详情查询")
  @NotBlank(message = "流程识别码不能为空")
  private String processKey;

  @ApiModelProperty(
      value = "表单数据结构",
      required = true,
      example = "business_form_xx:[field_1:xxxxx,field_2:xxxxx]",
      notes = "用于关表单字段，方便流程表单详情查询")
  @NotNull(message = "表单数据结构不能为空")
  private FormSchemaParam formSchema;

  public String getProcessKey() {
    return processKey;
  }

  public void setProcessKey(String processKey) {
    this.processKey = processKey;
  }

  public FormSchemaParam getFormSchema() {
    return formSchema;
  }

  public void setFormSchema(FormSchemaParam formSchema) {
    this.formSchema = formSchema;
  }

  @Override
  public String toString() {
    return "SaveFormSchemaParam{"
        + "processKey='"
        + processKey
        + '\''
        + ", formSchema="
        + formSchema
        + '}';
  }
}
