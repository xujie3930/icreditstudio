package com.micro.cloud.modules.system.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** @author roy */
@ApiModel("验证码图片 Response VO")
public class SysCaptchaImageRespVO {

  @ApiModelProperty(
      value = "uuid",
      required = true,
      example = "1b3b7d00-83a8-4638-9e37-d67011855968",
      notes = "通过该 uuid 作为该验证码的标识")
  private String uuid;

  @ApiModelProperty(value = "图片", required = true, notes = "验证码的图片内容，使用 Base64 编码")
  private String img;

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  @Override
  public String toString() {
    return "SysCaptchaImageRespVO{" + "uuid='" + uuid + '\'' + ", img='" + img + '\'' + '}';
  }
}
