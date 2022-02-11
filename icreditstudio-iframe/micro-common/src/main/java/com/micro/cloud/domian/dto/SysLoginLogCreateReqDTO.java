package com.micro.cloud.domian.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

/**
 * 登录日志创建 Request DTO
 *
 * @author roy
 */
public class SysLoginLogCreateReqDTO {
  /** 用户账号 */
  @NotBlank(message = "用户账号不能为空")
  @Length(max = 30, message = "用户账号长度不能超过30个字符")
  private String username;

  @NotBlank(message = "用户IP")
  @Length(max = 30, message = "用户IP")
  private String userIp;

  /** 登录结果 */
  @NotNull(message = "登录结果不能为空")
  private Integer result;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUserIp() {
    return userIp;
  }

  public void setUserIp(String userIp) {
    this.userIp = userIp;
  }

  public Integer getResult() {
    return result;
  }

  public void setResult(Integer result) {
    this.result = result;
  }

  @Override
  public String toString() {
    return "SysLoginLogCreateReqDTO{"
        + "username='"
        + username
        + '\''
        + ", userIp='"
        + userIp
        + '\''
        + ", result="
        + result
        + '}';
  }
}
