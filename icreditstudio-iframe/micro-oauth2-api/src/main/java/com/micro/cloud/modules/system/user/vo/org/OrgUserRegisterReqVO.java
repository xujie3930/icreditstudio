package com.micro.cloud.modules.system.user.vo.org;

import com.micro.cloud.modules.system.org.vo.ExternalOrgCreateVO;
import com.micro.cloud.modules.system.org.vo.SysOrgBaseVO;
import com.micro.cloud.modules.system.user.vo.SysUserBaseVO;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 用户注册请求参数
 *
 * @author EDZ
 * @since 2021-11-05
 */
public class OrgUserRegisterReqVO extends SysUserBaseVO {

  @ApiModelProperty(value = "身份证号码", required = true, example = "321720199605023333")
  @NotEmpty(message = "身份证号码不能为空")
  @Length(min = 18, max = 18, message = "身份证号码号码必须为18位")
  private String certificateNum;

  @ApiModelProperty(value = "密码", required = true, example = "123456")
  @NotEmpty(message = "密码不能为空")
  private String password;

  @ApiModelProperty(value = "图片验证码", required = true, example = "123456")
  @NotEmpty(message = "图片验证码不能为空")
  @Length(min = 4, max = 5, message = "图片验证码必须至少4位")
  private String captchaCode;

  @ApiModelProperty(value = "短信验证码", required = true, example = "123456")
  @NotEmpty(message = "验证码不能为空")
//  @Length(min = 6, max = 6, message = "短信验证码必须为6位")
  private String messageCode;

  @ApiModelProperty(value = "用户真实姓名", required = true, example = "王xx")
  @NotBlank(message = "用户真实姓名不能为空")
  private String realName;

  @ApiModelProperty(value = "机构基本信息", required = true)
  @NotNull(message = "机构信息不能为空")
  private ExternalOrgCreateVO externalOrgCreateVO;

  public String getCertificateNum() {
    return certificateNum;
  }

  public void setCertificateNum(String certificateNum) {
    this.certificateNum = certificateNum;
  }

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

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public ExternalOrgCreateVO getExternalOrgCreateVO() {
    return externalOrgCreateVO;
  }

  public void setExternalOrgCreateVO(ExternalOrgCreateVO externalOrgCreateVO) {
    this.externalOrgCreateVO = externalOrgCreateVO;
  }

  @Override
  public String toString() {
    return "OrgUserRegisterReqVO{"
        + "certificateNum='"
        + certificateNum
        + '\''
        + ", password='"
        + password
        + '\''
        + ", captchaCode='"
        + captchaCode
        + '\''
        + ", messageCode='"
        + messageCode
        + '\''
        + ", realName='"
        + realName
        + '\''
        + ", externalOrgCreateVO="
        + externalOrgCreateVO
        + '}';
  }
}
