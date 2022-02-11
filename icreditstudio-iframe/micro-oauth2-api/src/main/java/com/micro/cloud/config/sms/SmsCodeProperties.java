package com.micro.cloud.config.sms;

import java.time.Duration;
import javax.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/** @author roy */
@ConfigurationProperties(prefix = "micro.sms-code")
@Validated
public class SmsCodeProperties {

  /** 过期时间 */
  @NotNull(message = "过期时间不能为空")
  private Duration expireTimes;
  /** 短信发送频率 */
  @NotNull(message = "短信发送频率不能为空")
  private Duration sendFrequency;
  /** 每日发送最大数量 */
  @NotNull(message = "每日发送最大数量不能为空")
  private Integer sendMaximumQuantityPerDay;
  /** 验证码最小值 */
  @NotNull(message = "验证码最小值不能为空")
  private Integer beginCode;
  /** 验证码最大值 */
  @NotNull(message = "验证码最大值不能为空")
  private Integer endCode;

  public Duration getExpireTimes() {
    return expireTimes;
  }

  public void setExpireTimes(Duration expireTimes) {
    this.expireTimes = expireTimes;
  }

  public Duration getSendFrequency() {
    return sendFrequency;
  }

  public void setSendFrequency(Duration sendFrequency) {
    this.sendFrequency = sendFrequency;
  }

  public Integer getSendMaximumQuantityPerDay() {
    return sendMaximumQuantityPerDay;
  }

  public void setSendMaximumQuantityPerDay(Integer sendMaximumQuantityPerDay) {
    this.sendMaximumQuantityPerDay = sendMaximumQuantityPerDay;
  }

  public Integer getBeginCode() {
    return beginCode;
  }

  public void setBeginCode(Integer beginCode) {
    this.beginCode = beginCode;
  }

  public Integer getEndCode() {
    return endCode;
  }

  public void setEndCode(Integer endCode) {
    this.endCode = endCode;
  }

  @Override
  public String toString() {
    return "SmsCodeProperties{"
        + "expireTimes="
        + expireTimes
        + ", sendFrequency="
        + sendFrequency
        + ", sendMaximumQuantityPerDay="
        + sendMaximumQuantityPerDay
        + ", beginCode="
        + beginCode
        + ", endCode="
        + endCode
        + '}';
  }
}
