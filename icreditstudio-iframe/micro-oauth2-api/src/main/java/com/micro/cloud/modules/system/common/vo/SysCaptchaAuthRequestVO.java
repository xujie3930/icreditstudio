package com.micro.cloud.modules.system.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/** @author roy */
@ApiModel("验证码图片 Response VO")
public class SysCaptchaAuthRequestVO {

  @ApiModelProperty(
      value = "uuid",
      required = true,
      example = "1b3b7d00-83a8-4638-9e37-d67011855968",
      notes = "通过该 uuid 作为该验证码的标识")
  private String uuid;

  @ApiModelProperty(value = "图片验证码", required = true)
  @Length(min = 6, max = 6, message = "图片验证码必须为6位")
  private String captchaCode;

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getCaptchaCode() {
    return captchaCode;
  }

  public void setCaptchaCode(String captchaCode) {
    this.captchaCode = captchaCode;
  }

  @Override
  public String toString() {
    return "SysCaptchaAuthRequestVO{"
        + "uuid='"
        + uuid
        + '\''
        + ", captchaCode='"
        + captchaCode
        + '\''
        + '}';
  }
}
