package com.micro.cloud.modules.system.user.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.micro.cloud.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * @author EDZ
 * @since 2021-11-05
 */
@TableName("sys_user")
@ApiModel(value = "SysUser对象", description = "")
public class SysUser extends BaseDO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("个人用户id")
  @TableId("sys_user_id")
  private String sysUserId;

  @ApiModelProperty("用户名")
  @TableField("user_name")
  private String userName;

  @ApiModelProperty("用户真实姓名")
  @TableField("real_name")
  private String realName;

  @ApiModelProperty("工号")
  @TableField("work_code")
  private String workCode;

  @ApiModelProperty("身份证号")
  @TableField("certificate_num")
  private String certificateNum;

  @ApiModelProperty("手机号")
  @TableField("phone")
  private String phone;

  @ApiModelProperty("邮箱")
  @TableField("email")
  private String email;

  @ApiModelProperty("用户状态是否启用: 0->否 1->是")
  @TableField("status")
  private Boolean status;

  @ApiModelProperty("用户类型: 1-> 个人用户 2 ->机构用户 3-> 系统用户")
  @TableField("type")
  private Integer type;

  @ApiModelProperty("是否已实名认证: 0->否 1->是")
  @TableField("is_auth")
  private Integer isAuth;

  @ApiModelProperty("数据来源: 1-> 内部系统 2->第三方系统")
  @TableField("data_source")
  private Integer dataSource;

  @ApiModelProperty("用户描述")
  @TableField("remark")
  private String remark;

  @ApiModelProperty("用户职业")
  @TableField("profession")
  private Integer profession;

  @ApiModelProperty("用户行业")
  @TableField("industry")
  private Integer industry;

  @ApiModelProperty("所属应用id")
  @TableField("application_id")
  private String applicationId;

  @ApiModelProperty("创建人名称")
  @TableField("creator_name")
  private String creatorName;

  @ApiModelProperty("创建人部门id")
  @TableField("creator_depart_id")
  private String creatorDepartId;

  @ApiModelProperty("创建人部门名称")
  @TableField("creator_depart_name")
  private String creatorDepartName;

  @ApiModelProperty("更新人名称")
  @TableField("updater_name")
  private String updaterName;

  @ApiModelProperty("更新人部门id")
  @TableField("updater_depart_id")
  private String updaterDepartId;

  @ApiModelProperty("更新人部门名称")
  @TableField("updater_depart_name")
  private String updaterDepartName;

  public String getSysUserId() {
    return this.sysUserId;
  }

  public void setSysUserId(String sysUserId) {
    this.sysUserId = sysUserId;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getWorkCode() {
    return this.workCode;
  }

  public void setWorkCode(String workCode) {
    this.workCode = workCode;
  }

  public String getCertificateNum() {
    return this.certificateNum;
  }

  public void setCertificateNum(String certificateNum) {
    this.certificateNum = certificateNum;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Boolean getStatus() {
    return this.status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Integer getIsAuth() {
    return isAuth;
  }

  public void setIsAuth(Integer isAuth) {
    this.isAuth = isAuth;
  }

  public Integer getDataSource() {
    return dataSource;
  }

  public void setDataSource(Integer dataSource) {
    this.dataSource = dataSource;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
  }

  public Integer getProfession() {
    return profession;
  }

  public void setProfession(Integer profession) {
    this.profession = profession;
  }

  public Integer getIndustry() {
    return industry;
  }

  public void setIndustry(Integer industry) {
    this.industry = industry;
  }

  public String getCreatorName() {
    return this.creatorName;
  }

  public void setCreatorName(String creatorName) {
    this.creatorName = creatorName;
  }

  public String getCreatorDepartId() {
    return this.creatorDepartId;
  }

  public void setCreatorDepartId(String creatorDepartId) {
    this.creatorDepartId = creatorDepartId;
  }

  public String getCreatorDepartName() {
    return this.creatorDepartName;
  }

  public void setCreatorDepartName(String creatorDepartName) {
    this.creatorDepartName = creatorDepartName;
  }

  public String getUpdaterName() {
    return this.updaterName;
  }

  public void setUpdaterName(String updaterName) {
    this.updaterName = updaterName;
  }

  public String getUpdaterDepartId() {
    return this.updaterDepartId;
  }

  public void setUpdaterDepartId(String updaterDepartId) {
    this.updaterDepartId = updaterDepartId;
  }

  public String getUpdaterDepartName() {
    return this.updaterDepartName;
  }

  public void setUpdaterDepartName(String updaterDepartName) {
    this.updaterDepartName = updaterDepartName;
  }

  @Override
  public String toString() {
    return "SysUser{"
        + "sysUserId='"
        + sysUserId
        + '\''
        + ", userName='"
        + userName
        + '\''
        + ", realName='"
        + realName
        + '\''
        + ", workCode='"
        + workCode
        + '\''
        + ", certificateNum='"
        + certificateNum
        + '\''
        + ", phone='"
        + phone
        + '\''
        + ", email='"
        + email
        + '\''
        + ", status="
        + status
        + ", type="
        + type
        + ", isAuth="
        + isAuth
        + ", dataSource="
        + dataSource
        + ", remark='"
        + remark
        + '\''
        + ", profession="
        + profession
        + ", industry='"
        + industry
        + '\''
        + ", applicationId='"
        + applicationId
        + '\''
        + ", creatorName='"
        + creatorName
        + '\''
        + ", creatorDepartId='"
        + creatorDepartId
        + '\''
        + ", creatorDepartName='"
        + creatorDepartName
        + '\''
        + ", updaterName='"
        + updaterName
        + '\''
        + ", updaterDepartId='"
        + updaterDepartId
        + '\''
        + ", updaterDepartName='"
        + updaterDepartName
        + '\''
        + '}';
  }
}
