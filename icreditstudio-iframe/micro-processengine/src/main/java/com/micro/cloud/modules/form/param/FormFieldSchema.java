package com.micro.cloud.modules.form.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 〈表单字段Schema〉
 *
 * @author roy
 * @create 2021/12/3
 * @since 1.0.0
 */
@ApiModel(value = "表单字段Schema")
public class FormFieldSchema implements Serializable {

  private static final long serialVersionUID = -3182436179267379771L;

  @ApiModelProperty(value = "数据库字段名称", required = true, example = "field_name")
  private String fieldName;

  @ApiModelProperty(value = "字段显示名称", required = true, example = "字段名称", notes = "用于前端展示")
  private String fieldLabel;

  @ApiModelProperty(value = "字段数据库类型", required = true, example = "varchar(255)", notes = "下拉框选择")
  private String fieldDbType;

  @ApiModelProperty(
      value = "前端字段展示类型",
      required = true,
      example = "1 -> 单行文本框 2 -> 多行文本框 3 -> 浏览按钮 ...")
  private int viewType;

  @ApiModelProperty(value = "字段长度", required = true, example = "255", notes = "数据库字段长度")
  private Integer fieldLength;

  @ApiModelProperty(value = "主表/从表", required = true, example = "true", notes = "是否为主表字段")
  private Boolean isMain;

  @ApiModelProperty(value = "是否允许为空", required = true, example = "false", notes = "用于判断字段是否为空")
  private Boolean isNullable;

  @ApiModelProperty(value = "是否可编辑", required = true, example = "false", notes = "用于前端判断字段是否可编辑")
  private Boolean isEditable;

  @ApiModelProperty(value = "是否显示", required = true, example = "true", notes = "用于前端判断字段是否展示")
  private Boolean isShowable;

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getFieldLabel() {
    return fieldLabel;
  }

  public void setFieldLabel(String fieldLabel) {
    this.fieldLabel = fieldLabel;
  }

  public String getFieldDbType() {
    return fieldDbType;
  }

  public void setFieldDbType(String fieldDbType) {
    this.fieldDbType = fieldDbType;
  }

  public int getViewType() {
    return viewType;
  }

  public void setViewType(int viewType) {
    this.viewType = viewType;
  }

  public Integer getFieldLength() {
    return fieldLength;
  }

  public void setFieldLength(Integer fieldLength) {
    this.fieldLength = fieldLength;
  }

  public Boolean getMain() {
    return isMain;
  }

  public void setMain(Boolean main) {
    isMain = main;
  }

  public Boolean getNullable() {
    return isNullable;
  }

  public void setNullable(Boolean nullable) {
    isNullable = nullable;
  }

  public Boolean getEditable() {
    return isEditable;
  }

  public void setEditable(Boolean editable) {
    isEditable = editable;
  }

  public Boolean getShowable() {
    return isShowable;
  }

  public void setShowable(Boolean showable) {
    isShowable = showable;
  }

  @Override
  public String toString() {
    return "FormFieldSchema{"
        + "fieldName='"
        + fieldName
        + '\''
        + ", fieldLabel='"
        + fieldLabel
        + '\''
        + ", fieldDbType='"
        + fieldDbType
        + '\''
        + ", viewType='"
        + viewType
        + '\''
        + ", fieldLength="
        + fieldLength
        + ", isMain="
        + isMain
        + ", isNullable="
        + isNullable
        + ", isEditable="
        + isEditable
        + ", isShowable="
        + isShowable
        + '}';
  }
}
