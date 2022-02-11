package com.micro.cloud.sms.core.client.dto;

/**
 * 短信模板 Response DTO
 *
 * @author roy
 */
public class SmsTemplateRespDTO {

  /** 模板编号 */
  private String id;
  /** 短信内容 */
  private String content;
  /**
   * 审核状态
   *
   * <p>枚举
   */
  private Integer auditStatus;
  /** 审核未通过的理由 */
  private String auditReason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

    @Override
    public String toString() {
        return "SmsTemplateRespDTO{" +
            "id='" + id + '\'' +
            ", content='" + content + '\'' +
            ", auditStatus=" + auditStatus +
            ", auditReason='" + auditReason + '\'' +
            '}';
    }
}
