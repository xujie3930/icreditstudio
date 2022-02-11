package com.micro.cloud.modules.system.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 * 用户名密码校验
 *
 * @author roy
 */
@ApiModel(value = "用户名密码校验")
public class UserValidateReqVo implements Serializable {

  private static final long serialVersionUID = -5610974785527188221L;

  @NotBlank(message = "用户名不能为空")
  @ApiModelProperty(value = "用户名", required = true, example = "xinqi")
  private String username;

  @NotBlank(message = "用户密码不能为空")
  @ApiModelProperty(value = "密码", required = true, example = "123456")
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "UserValidateReqVo{"
        + "username='"
        + username
        + '\''
        + ", password='"
        + password
        + '\''
        + '}';
  }
}