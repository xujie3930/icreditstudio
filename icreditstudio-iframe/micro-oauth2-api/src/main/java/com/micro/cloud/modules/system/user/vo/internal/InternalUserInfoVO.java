package com.micro.cloud.modules.system.user.vo.internal;

import com.micro.cloud.modules.system.org.vo.SysOrgBaseVO;
import com.micro.cloud.modules.system.user.vo.SysUserBaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @ApiModel(value = "系统用户分页时的信息 Response VO", description = "相比个人用户基本信息来说，会多角色、所属组织")
 *
 * @author roy
 */
@ApiModel
public class InternalUserInfoVO extends SysUserBaseVO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "用户id", example = "234543212345643")
  private String userId;

  @ApiModelProperty(value = "用户真实姓名", example = "张三")
  private String realName;

  @ApiModelProperty(value = "用户key", example = "234543212345643")
  private String key;

  @ApiModelProperty(value = "组织机构id", required = true, example = "21023")
  private String orgId;

  @ApiModelProperty(value = "组织机构名称", required = true, example = "南京金宁汇科技有限公司")
  private String title;

  @ApiModelProperty(value = "组织机构类型", required = true, example = "1", notes = "参见 OrgTypeEnum 枚举类")
  private Integer orgType;

  @ApiModelProperty(
      value = "组织机构状态",
      required = true,
      example = "1",
      notes = "参见 SysCommonStatus 枚举类")
  private Boolean orgStatus;

  @ApiModelProperty(value = "用户角色id", required = true, example = "298382")
  private String roleId;

  @ApiModelProperty(value = "用户角色名称", required = true, example = "江北新区大数据中心管理员")
  private String role;

  @ApiModelProperty(value = "人员描述信息", required = true, example = "")
  private String remark;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public Boolean getOrgStatus() {
    return orgStatus;
  }

  public void setOrgStatus(Boolean orgStatus) {
    this.orgStatus = orgStatus;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getOrgType() {
    return orgType;
  }

  public void setOrgType(Integer orgType) {
    this.orgType = orgType;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  @Override
  public String toString() {
    return "InternalUserInfoVO{" +
        "userId='" + userId + '\'' +
        ", realName='" + realName + '\'' +
        ", key='" + key + '\'' +
        ", orgId='" + orgId + '\'' +
        ", title='" + title + '\'' +
        ", orgType=" + orgType +
        ", orgStatus=" + orgStatus +
        ", roleId='" + roleId + '\'' +
        ", role='" + role + '\'' +
        ", remark='" + remark + '\'' +
        '}';
  }
}
