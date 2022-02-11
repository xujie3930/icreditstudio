package com.micro.cloud.sms.core.client.dto;

/**
 * 短信发送 Response DTO
 *
 * @author 芋道源码
 */
public class SmsSendRespDTO {

  /** 短信 API 发送返回的序号 */
  private String serialNo;

  public String getSerialNo() {
    return serialNo;
  }

  public void setSerialNo(String serialNo) {
    this.serialNo = serialNo;
  }

  @Override
  public String toString() {
    return "SmsSendRespDTO{" + "serialNo='" + serialNo + '\'' + '}';
  }
}
