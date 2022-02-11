package com.micro.cloud.modules.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.micro.cloud.validation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

/**
 * 用户数据保存Dto
 *
 * @author EDZ
 */
public class SysUserBaseVO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "用户名", required = true, example = "roy")
  @NotBlank(message = "用户名不能为空")
  @Length(min = 5, max = 25, message = "用户账号长度为 5-25 个字符")
  private String username;

  @ApiModelProperty(value = "用户邮箱", example = "roy@xxxx.cn")
  @Email(message = "邮箱格式不正确")
  @Length(max = 50, message = "邮箱长度不能超过 50 个字符")
  private String email;

  @ApiModelProperty(value = "手机号码", example = "15601691300")
  @Length(min = 11, max = 11, message = "手机号长度必须 11 位")
  @Mobile
  private String mobile;

  @ApiModelProperty(value = "用户性别", example = "1", notes = "参见 SysSexEnum 枚举类")
  private Integer sex;

  @ApiModelProperty(value = "是否启用", example = "true", notes = "参见 SysCommonStatusEnum 枚举类")
  private Boolean status;

  @ApiModelProperty(value = "用户类型", example = "1", notes = "参见 UserTypeEnum 枚举类")
  private Integer type;

  @ApiModelProperty(value = "创建时间", example = "2021-09-10")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;

  @ApiModelProperty(value = "真实姓名", required = false, example = "张三")
  private String realName;


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public Integer getSex() {
    return sex;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  @Override
  public String toString() {
    return "SysUserBaseVO{" +
            "username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", mobile='" + mobile + '\'' +
            ", sex=" + sex +
            ", status=" + status +
            ", type=" + type +
            ", createTime=" + createTime +
            ", realName='" + realName + '\'' +
            '}';
  }
}
