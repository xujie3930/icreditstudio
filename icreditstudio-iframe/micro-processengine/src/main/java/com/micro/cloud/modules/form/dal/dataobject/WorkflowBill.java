package com.micro.cloud.modules.form.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.micro.cloud.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 〈流程表单字段〉
 *
 * @author roy
 * @create 2021/12/3
 * @since 1.0.0
 */
@TableName(value = "workflow_bill", autoResultMap = true)
public class WorkflowBill extends BaseDO implements Serializable {

  private static final long serialVersionUID = -2490191495733679164L;

  @ApiModelProperty(value = "流程表单信息id", required = true, example = "23456432134565432")
  private String workflowBillId;

  @ApiModelProperty(value = "流程定义key", required = true, example = "Process_holiday")
  private String processKey;

  @ApiModelProperty(value = "流程对应主表名称", required = true, example = "business_form_lx")
  private String tableName;

  @ApiModelProperty(value = "创建请求页面路由", required = true, example = "创建请求页面路由")
  private String createUrl;

  @ApiModelProperty(value = "管理请求页面路由", required = true, example = "管理请求页面路由")
  private String manageUrl;

  @ApiModelProperty(value = "查看请求页面路由", required = true, example = "查看请求页面路由")
  private String viewUrl;

  @ApiModelProperty(value = "流程对应明细表名称", required = true, example = "255")
  private String detailTableName;

  @ApiModelProperty(value = "主表-从表外建关键字", required = true, example = "主表-从表外建关键字")
  private String relateKey;

  @ApiModelProperty(value = "后台管理请求页面路由", required = true, example = "后台管理请求页面路由")
  private String operationUrl;

  @ApiModelProperty(value = "表单描述信息", required = true, example = "表单描述信息")
  private String formDesc;

  @ApiModelProperty(value = "版本", required = true, example = "6")
  private Integer version;

  @ApiModelProperty(value = "是否启用", required = true, example = "0.是 1.否")
  private Integer status;

  public String getWorkflowBillId() {
    return workflowBillId;
  }

  public String getProcessKey() {
    return processKey;
  }

  public String getTableName() {
    return tableName;
  }

  public String getCreateUrl() {
    return createUrl;
  }

  public String getManageUrl() {
    return manageUrl;
  }

  public String getViewUrl() {
    return viewUrl;
  }

  public String getDetailTableName() {
    return detailTableName;
  }

  public String getRelateKey() {
    return relateKey;
  }

  public String getOperationUrl() {
    return operationUrl;
  }

  public String getFormDesc() {
    return formDesc;
  }

  public Integer getVersion() {
    return version;
  }

  public Integer getStatus() {
    return status;
  }

  public void setWorkflowBillId(String workflowBillId) {
    this.workflowBillId = workflowBillId;
  }

  public void setProcessKey(String processKey) {
    this.processKey = processKey;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public void setCreateUrl(String createUrl) {
    this.createUrl = createUrl;
  }

  public void setManageUrl(String manageUrl) {
    this.manageUrl = manageUrl;
  }

  public void setViewUrl(String viewUrl) {
    this.viewUrl = viewUrl;
  }

  public void setDetailTableName(String detailTableName) {
    this.detailTableName = detailTableName;
  }

  public void setRelateKey(String relateKey) {
    this.relateKey = relateKey;
  }

  public void setOperationUrl(String operationUrl) {
    this.operationUrl = operationUrl;
  }

  public void setFormDesc(String formDesc) {
    this.formDesc = formDesc;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "WorkflowBill{" +
        "workflowBillId='" + workflowBillId + '\'' +
        ", processKey='" + processKey + '\'' +
        ", tableName='" + tableName + '\'' +
        ", createUrl='" + createUrl + '\'' +
        ", manageUrl='" + manageUrl + '\'' +
        ", viewUrl='" + viewUrl + '\'' +
        ", detailTableName='" + detailTableName + '\'' +
        ", relateKey='" + relateKey + '\'' +
        ", operationUrl='" + operationUrl + '\'' +
        ", formDesc='" + formDesc + '\'' +
        ", version=" + version +
        ", status=" + status +
        '}';
  }
}
