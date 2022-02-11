package com.micro.cloud.modules.system.user.vo;

import com.micro.cloud.validation.Mobile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

/**
 * 〈重制密码请求参数〉
 *
 * @author roy
 * @create 2021/11/15
 * @since 1.0.0
 */
@ApiModel("重制密码请求入参")
public class PasswordResetReqVO implements Serializable {

  private static final long serialVersionUID = 3153100225542773804L;

  @ApiModelProperty(value = "手机号码", example = "15601691300")
  @Length(min = 11, max = 11, message = "手机号长度必须 11 位")
  @Mobile
  private String mobile;

  @ApiModelProperty(value = "图片验证码", required = true)
  @Length(min = 4, max = 5, message = "图片验证码必须至少4位")
  private String captchaCode;

  @ApiModelProperty(value = "短信验证码", required = true, example = "123456")
  @NotEmpty(message = "验证码不能为空")
  private String messageCode;

  @ApiModelProperty(value = "密码", required = true, example = "123456")
  @NotEmpty(message = "密码不能为空")
  private String password;

  @ApiModelProperty(value = "密码", required = true, example = "123456")
  @NotEmpty(message = "密码不能为空")
  private String confirmPassword;

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  @Override
  public String toString() {
    return "PasswordResetReqVO{"
        + "mobile='"
        + mobile
        + '\''
        + ", captchaCode='"
        + captchaCode
        + '\''
        + ", messageCode='"
        + messageCode
        + '\''
        + ", password='"
        + password
        + '\''
        + ", confirmPassword='"
        + confirmPassword
        + '\''
        + '}';
  }
}
