package com.micro.cloud.modules.form.param;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 * 〈表单结构〉
 *
 * @author roy
 * @create 2021/12/3
 * @since 1.0.0
 */
@Api(value = "表单结构")
public class FormSchemaParam implements Serializable {

  private static final long serialVersionUID = -8608230133190950010L;

  @ApiModelProperty(
      value = "表单对应数据库表名称",
      required = true,
      example = "business_form_lx",
      notes = "数据库表名称全局唯一, 命名规则 business_form_表单名称首字母缩写")
  private String tableName;

  @ApiModelProperty(
      value = "表单Schema",
      required = true,
      example = "business_form_xx:[field_1:xxxxx,field_2:xxxxx]",
      notes = "用于关表单字段，方便流程表单详情查询")
  @NotNull(message = "表单结构不能为空")
  private List<FormFieldSchema> formSchema;

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public List<FormFieldSchema> getFormSchema() {
    return formSchema;
  }

  public void setFormSchema(List<FormFieldSchema> formSchema) {
    this.formSchema = formSchema;
  }

  @Override
  public String toString() {
    return "FormSchemaParam{"
        + "tableName='"
        + tableName
        + '\''
        + ", formSchema="
        + formSchema
        + '}';
  }
}
