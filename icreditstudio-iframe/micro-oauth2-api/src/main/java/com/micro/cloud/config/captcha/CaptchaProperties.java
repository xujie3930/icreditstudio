package com.micro.cloud.config.captcha;

import javax.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "micro.captcha")
@Validated
public class CaptchaProperties {

  /** 验证码的过期时间 */
  @NotNull(message = "验证码的过期时间不为空")
  private Long timeout;
  /** 验证码的高度 */
  @NotNull(message = "验证码的高度不能为空")
  private Integer height;
  /** 验证码的宽度 */
  @NotNull(message = "验证码的宽度不能为空")
  private Integer width;

  public Long getTimeout() {
    return timeout;
  }

  public void setTimeout(Long timeout) {
    this.timeout = timeout;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  @Override
  public String toString() {
    return "CaptchaProperties{"
        + "timeout="
        + timeout
        + ", height="
        + height
        + ", width="
        + width
        + '}';
  }
}
