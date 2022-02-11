package com.micro.cloud.modules.form.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 〈表单字段校验数据类型〉
 *
 * @author roy
 * @create 2021/12/22
 * @since 1.0.0
 */
public class FormFieldValidateDto implements Serializable {

  private static final long serialVersionUID = -7057556656844871578L;

  private String fieldName;

  private Object fileValue;

  @ApiModelProperty(value = "字段数据库类型", required = true, example = "varchar(255)", notes = "下拉框选择")
  private String fieldDbType;

  @ApiModelProperty(value = "字段长度", required = true, example = "10", notes = "配合fieldDbType使用")
  private Integer fieldLength;

  @ApiModelProperty(
      value = "前端字段展示类型",
      required = true,
      example = "1 -> 单行文本框 2 -> 多行文本框 3 -> 浏览按钮 ...")
  private int viewType;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getFileValue() {
        return fileValue;
    }

    public void setFileValue(Object fileValue) {
        this.fileValue = fileValue;
    }

    public String getFieldDbType() {
        return fieldDbType;
    }

    public void setFieldDbType(String fieldDbType) {
        this.fieldDbType = fieldDbType;
    }

    public Integer getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(Integer fieldLength) {
        this.fieldLength = fieldLength;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public String toString() {
        return "FormFieldValidateDto{" +
            "fieldName='" + fieldName + '\'' +
            ", fileValue=" + fileValue +
            ", fieldDbType='" + fieldDbType + '\'' +
            ", fieldLength=" + fieldLength +
            ", viewType=" + viewType +
            '}';
    }
}
