package com.micro.cloud.modules.process.result;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Map;

/**
 * 〈流程审批结果〉
 *
 * @author roy
 * @create 2021/12/18
 * @since 1.0.0
 */
public class ProcessCallbackResult implements Serializable {

  private static final long serialVersionUID = -8402422418527594245L;

  @ApiModelProperty(value = "审批状态", required = true)
  private String approveStatus;

  @ApiModelProperty(value = "业务id", required = true)
  private String bizId;

  @ApiModelProperty(value = "流程processKey", required = true)
  private String processKey;

  @ApiModelProperty(value = "审批数据", required = true)
  private Map<String, Object> processData;

  public String getApproveStatus() {
    return approveStatus;
  }

  public void setApproveStatus(String approveStatus) {
    this.approveStatus = approveStatus;
  }

  public String getBizId() {
    return bizId;
  }

  public void setBizId(String bizId) {
    this.bizId = bizId;
  }

  public String getProcessKey() {
    return processKey;
  }

  public void setProcessKey(String processKey) {
    this.processKey = processKey;
  }

  public Map<String, Object> getProcessData() {
    return processData;
  }

  public void setProcessData(Map<String, Object> processData) {
    this.processData = processData;
  }

  @Override
  public String toString() {
    return "ProcessCallbackResult{" +
        "approveStatus='" + approveStatus + '\'' +
        ", bizId='" + bizId + '\'' +
        ", processKey='" + processKey + '\'' +
        ", processData=" + processData +
        '}';
  }
}
