package com.micro.cloud.modules.system.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

/**
 * 〈用户更改密码请求参数〉
 *
 * @author roy
 * @create 2021/11/15
 * @since 1.0.0
 */
@ApiModel("更改密码请求入参")
public class UpdatePasswordReqVO implements Serializable {

  private static final long serialVersionUID = 3153100225542773804L;

  @ApiModelProperty(value = "用户id", required = true, example = "123456")
  @NotBlank(message = "用户id不能为空")
  private String userId;

  @ApiModelProperty(value = "原始密码", required = true, example = "123456")
  @NotBlank(message = "原始密码不能为空")
  private String original;

  @ApiModelProperty(value = "密码", required = true, example = "654321")
  @NotBlank(message = "新建密码不能为空")
  private String newPassword;

  @ApiModelProperty(value = "密码", required = true, example = "123456")
  @NotBlank(message = "密码不能为空")
  private String confirmPassword;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getOriginal() {
    return original;
  }

  public void setOriginal(String original) {
    this.original = original;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  @Override
  public String toString() {
    return "UpdatePasswordReqVO{" +
        "userId='" + userId + '\'' +
        ", original='" + original + '\'' +
        ", newPassword='" + newPassword + '\'' +
        ", confirmPassword='" + confirmPassword + '\'' +
        '}';
  }
}
