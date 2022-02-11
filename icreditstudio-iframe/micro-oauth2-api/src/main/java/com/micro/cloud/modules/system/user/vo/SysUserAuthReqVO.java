package com.micro.cloud.modules.system.user.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/**
 * 〈用户实名认证请求参数〉
 *
 * @author roy
 * @create 2021/11/9
 * @since 1.0.0
 */
public class SysUserAuthReqVO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "个人用户id", required = true, example = "312342432432")
  @NotBlank(message = "用户id不能为空")
  private String userId;

  @ApiModelProperty(value = "用户名", required = true, example = "李四")
  @NotBlank(message = "用户名不能为空")
  private String username;

  @ApiModelProperty(value = "身份证号码", required = true, example = "320722199504231sss")
  @NotBlank(message = "身份证号不能为空")
  private String certificateNum;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getCertificateNum() {
    return certificateNum;
  }

  public void setCertificateNum(String certificateNum) {
    this.certificateNum = certificateNum;
  }

  @Override
  public String toString() {
    return "SysUserAuthReqVO{"
        + "userId='"
        + userId
        + '\''
        + ", username='"
        + username
        + '\''
        + ", certificateNum='"
        + certificateNum
        + '\''
        + '}';
  }
}
