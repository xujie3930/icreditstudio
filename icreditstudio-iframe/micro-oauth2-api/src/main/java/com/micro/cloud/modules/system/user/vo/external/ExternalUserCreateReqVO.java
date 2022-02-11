package com.micro.cloud.modules.system.user.vo.external;

import com.micro.cloud.modules.system.user.vo.SysUserBaseVO;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

/**
 * 新增个人用户请求参数
 *
 * @author EDZ
 * @since 2021-11-05
 */
public class ExternalUserCreateReqVO extends SysUserBaseVO {

  @ApiModelProperty(value = "密码", required = true, example = "123456")
  @NotEmpty(message = "密码不能为空")
  private String password;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "ExternalUserCreateReqVO{" + "password='" + password + '\'' + '}';
  }
}
