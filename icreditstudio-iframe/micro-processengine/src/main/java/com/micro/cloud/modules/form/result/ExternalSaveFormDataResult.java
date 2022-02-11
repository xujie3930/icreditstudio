package com.micro.cloud.modules.form.result;

import io.swagger.annotations.ApiModelProperty;

/**
 * 〈外部表单保存返回结果〉
 *
 * @author roy
 * @create 2021/12/11
 * @since 1.0.0
 */
public class ExternalSaveFormDataResult {

  @ApiModelProperty(value = "流程识别码")
  private String processKey;

  @ApiModelProperty(value = "业务id")
  private String businessId;

  @ApiModelProperty(value = "流程实例id")
  private String processInstanceId;

  public ExternalSaveFormDataResult() {}

  public ExternalSaveFormDataResult(
      String processKey, String businessId, String processInstanceId) {
    this.processKey = processKey;
    this.businessId = businessId;
    this.processInstanceId = processInstanceId;
  }

  public String getProcessKey() {
    return processKey;
  }

  public void setProcessKey(String processKey) {
    this.processKey = processKey;
  }

  public String getBusinessId() {
    return businessId;
  }

  public void setBusinessId(String businessId) {
    this.businessId = businessId;
  }

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  @Override
  public String toString() {
    return "ExternalSaveFormDataResult{"
        + "processKey='"
        + processKey
        + '\''
        + ", businessId='"
        + businessId
        + '\''
        + ", processInstanceId='"
        + processInstanceId
        + '\''
        + '}';
  }
}
