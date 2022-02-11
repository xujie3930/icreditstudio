package com.micro.cloud.modules.system.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

/**
 * @author roy
 *
 */
@ApiModel("用户名+ 密码 + 验证码登录 Request VO")
public class SysUserLoginReqVO {

  @ApiModelProperty(value = "用户名", required = true, example = "wangliuliu")
  @NotEmpty(message = "用户名不能为空")
  private String username;

  @ApiModelProperty(value = "用户密码", required = true, example = "wangliuliu777")
  @NotEmpty(message = "用户名不能为空")
  private String password;

  @ApiModelProperty(value = "手机验证码", required = true, example = "1024")
  @NotEmpty(message = "手机验证码不能为空")
  @Length(min = 4, max = 6, message = "手机验证码长度为 4-6 位")
  @Pattern(regexp = "^[0-9]+$", message = "手机验证码必须都是数字")
  private String code;
}
