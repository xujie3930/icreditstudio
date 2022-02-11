package com.micro.cloud.modules.form.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.micro.cloud.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * 〈流程表单字段〉
 *
 * @author roy
 * @create 2021/12/3
 * @since 1.0.0
 */
@TableName(value = "workflow_bill_field", autoResultMap = true)
public class WorkflowBillField extends BaseDO implements Serializable {

  private static final long serialVersionUID = -2490191495733679164L;

  @ApiModelProperty(value = "流程表单字段id", required = true, example = "23456432134565432")
  private String workflowBillFieldId;

  @ApiModelProperty(value = "数据库字段名称", required = true, example = "field_name")
  private String fieldName;

  @ApiModelProperty(value = "字段显示名称", required = true, example = "字段名称", notes = "用于前端展示")
  private String fieldLabel;

  @ApiModelProperty(value = "字段数据库类型", required = true, example = "varchar(255)", notes = "下拉框选择")
  private String fieldDbType;

  @ApiModelProperty(value = "字段长度", required = true, example = "10", notes = "配合fieldDbType使用")
  private Integer fieldLength;

  @ApiModelProperty(
      value = "前端字段展示类型",
      required = true,
      example = "1 -> 单行文本框 2 -> 多行文本框 3 -> 浏览按钮 ...")
  private int viewType;

  @ApiModelProperty(value = "主表/从表", required = true, example = "true", notes = "是否为主表字段")
  private Integer isMain;

  @ApiModelProperty(value = "对应单据id", required = true, example = "true", notes = "对应单据id")
  private String workflowBillId;

  public String getWorkflowBillFieldId() {
    return workflowBillFieldId;
  }

  public String getFieldName() {
    return fieldName;
  }

  public String getFieldLabel() {
    return fieldLabel;
  }

  public String getFieldDbType() {
    return fieldDbType;
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

  public Integer getIsMain() {
    return isMain;
  }

  public String getWorkflowBillId() {
    return workflowBillId;
  }

  public void setWorkflowBillFieldId(String workflowBillFieldId) {
    this.workflowBillFieldId = workflowBillFieldId;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public void setFieldLabel(String fieldLabel) {
    this.fieldLabel = fieldLabel;
  }

  public void setFieldDbType(String fieldDbType) {
    this.fieldDbType = fieldDbType;
  }

  public void setViewType(int viewType) {
    this.viewType = viewType;
  }

  public void setIsMain(Integer isMain) {
    this.isMain = isMain;
  }

  public void setWorkflowBillId(String workflowBillId) {
    this.workflowBillId = workflowBillId;
  }

  @Override
  public String toString() {
    return "WorkflowBillField{" +
        "workflowBillFieldId='" + workflowBillFieldId + '\'' +
        ", fieldName='" + fieldName + '\'' +
        ", fieldLabel='" + fieldLabel + '\'' +
        ", fieldDbType='" + fieldDbType + '\'' +
        ", fieldLength=" + fieldLength +
        ", viewType='" + viewType + '\'' +
        ", isMain=" + isMain +
        ", workflowBillId='" + workflowBillId + '\'' +
        '}';
  }
}
