package com.micro.cloud.modules.system.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

/**
 * 用户手机号校验参数
 *
 * @author roy
 */
@ApiModel(value = "用户手机号校验")
public class PhoneValidateReqVo implements Serializable {

  private static final long serialVersionUID = -5610974785527188221L;

  @ApiModelProperty(value = "用户id", required = true, example = "18553440906")
  @NotBlank(message = "用户id")
  private String userId;

  @ApiModelProperty(value = "用户名", required = true, example = "18553440906")
  @NotBlank(message = "手机号不能为空")
  private String phone;

  @ApiModelProperty(value = "图片验证码", required = true)
  @Length(min = 4, max = 5, message = "图片验证码必须为至少4位")
  private String captchaCode;

  @ApiModelProperty(value = "短信验证码", required = true, example = "123456")
  @NotEmpty(message = "验证码不能为空")
  private String messageCode;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getCaptchaCode() {
    return captchaCode;
  }

  public void setCaptchaCode(String captchaCode) {
    this.captchaCode = captchaCode;
  }

  public String getMessageCode() {
    return messageCode;
  }

  public void setMessageCode(String messageCode) {
    this.messageCode = messageCode;
  }

  @Override
  public String toString() {
    return "PhoneValidateReqVo{"
        + "userId='"
        + userId
        + '\''
        + ", phone='"
        + phone
        + '\''
        + ", captchaCode='"
        + captchaCode
        + '\''
        + ", messageCode='"
        + messageCode
        + '\''
        + '}';
  }
}
