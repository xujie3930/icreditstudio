package com.micro.cloud.modules.system.common.dto;

import com.micro.cloud.common.core.KeyValue;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 * 短信发送消息
 *
 * @author 芋道源码
 */
public class SysSmsSendMessage {

  /** 短信日志编号 */
  @NotNull(message = "短信日志编号不能为空")
  private Long logId;
  /** 手机号 */
  @NotNull(message = "手机号不能为空")
  private String mobile;
  /** 短信渠道编号 */
  @NotNull(message = "短信渠道编号不能为空")
  private Long channelId;
  /** 短信 API 的模板编号 */
  @NotNull(message = "短信 API 的模板编号不能为空")
  private String apiTemplateId;
  /** 短信模板参数 */
  private List<KeyValue<String, Object>> templateParams;

  public Long getLogId() {
    return logId;
  }

  public void setLogId(Long logId) {
    this.logId = logId;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public Long getChannelId() {
    return channelId;
  }

  public void setChannelId(Long channelId) {
    this.channelId = channelId;
  }

  public String getApiTemplateId() {
    return apiTemplateId;
  }

  public void setApiTemplateId(String apiTemplateId) {
    this.apiTemplateId = apiTemplateId;
  }

  public List<KeyValue<String, Object>> getTemplateParams() {
    return templateParams;
  }

  public void setTemplateParams(List<KeyValue<String, Object>> templateParams) {
    this.templateParams = templateParams;
  }

  @Override
  public String toString() {
    return "SysSmsSendMessage{"
        + "logId="
        + logId
        + ", mobile='"
        + mobile
        + '\''
        + ", channelId="
        + channelId
        + ", apiTemplateId='"
        + apiTemplateId
        + '\''
        + ", templateParams="
        + templateParams
        + '}';
  }
}
