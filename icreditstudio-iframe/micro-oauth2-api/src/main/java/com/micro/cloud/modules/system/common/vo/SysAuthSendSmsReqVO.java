package com.micro.cloud.modules.system.common.vo;

import com.micro.cloud.validation.Mobile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;

@ApiModel("发送手机验证码 Response VO")
public class SysAuthSendSmsReqVO {

  @ApiModelProperty(value = "手机号", example = "15601691234")
  @NotNull(message = "手机号不能为空")
  @Mobile
  private String mobile;

  @ApiModelProperty(value = "发送场景", example = "1", notes = "对应 MbrSmsSceneEnum 枚举")
  @NotNull(message = "发送场景不能为空")
  private Integer scene;

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public Integer getScene() {
    return scene;
  }

  public void setScene(Integer scene) {
    this.scene = scene;
  }

  @Override
  public String toString() {
    return "SysAuthSendSmsReqVO{" + "mobile='" + mobile + '\'' + ", scene=" + scene + '}';
  }
}
