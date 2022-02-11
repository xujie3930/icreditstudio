package com.micro.cloud.modules.system.user.vo.external;

import com.micro.cloud.modules.system.user.vo.SysUserBaseVO;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

/**
 * 用户注册请求参数
 *
 * @author EDZ
 * @since 2021-11-05
 */
public class ExternalUserRegisterReqVO extends SysUserBaseVO {

  @ApiModelProperty(value = "密码", required = true, example = "123456")
  @NotEmpty(message = "密码不能为空")
  private String password;

  @ApiModelProperty(value = "图片验证码", required = true, example = "123456")
  @NotEmpty(message = "图片验证码不能为空")
  @Length(min = 4, max = 5, message = "图片验证码必须至少4位")
  private String captchaCode;

  @ApiModelProperty(value = "短信验证码", required = true, example = "123456")
  @NotEmpty(message = "验证码不能为空")
  private String messageCode;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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
    return "ExternalUserRegisterReqVO{"
        + "password='"
        + password
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
