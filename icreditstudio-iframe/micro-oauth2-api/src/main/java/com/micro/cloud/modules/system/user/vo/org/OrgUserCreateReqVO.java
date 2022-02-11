package com.micro.cloud.modules.system.user.vo.org;

import com.micro.cloud.modules.system.org.vo.SysOrgBaseVO;
import com.micro.cloud.modules.system.user.vo.SysUserBaseVO;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

/**
 * 新增用户请求参数
 *
 * @author EDZ
 * @since 2021-11-05
 */
public class OrgUserCreateReqVO extends SysUserBaseVO {

  @ApiModelProperty(value = "密码", required = true, example = "123456")
  @NotEmpty(message = "密码不能为空")
  private String password;

  @ApiModelProperty(value = "用户组织机构信息")
  @NotNull(message = "用户组织机构信息不能为空")
  @Valid
  private SysOrgBaseVO orgBaseVO;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public SysOrgBaseVO getOrgBaseVO() {
    return orgBaseVO;
  }

  public void setOrgBaseVO(SysOrgBaseVO orgBaseVO) {
    this.orgBaseVO = orgBaseVO;
  }

  @Override
  public String toString() {
    return "OrgUserCreateReqVO{"
        + "password='"
        + password
        + '\''
        + ", orgBaseVO="
        + orgBaseVO
        + '}';
  }
}
