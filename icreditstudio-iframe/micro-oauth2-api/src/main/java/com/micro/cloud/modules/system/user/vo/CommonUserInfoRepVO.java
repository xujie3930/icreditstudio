package com.micro.cloud.modules.system.user.vo;

import com.micro.cloud.domian.dto.UserRoles;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

/**
 * 〈用户登录返回信息〉
 *
 * @author roy
 * @create 2021/11/19
 * @since 1.0.0
 */
public class CommonUserInfoRepVO implements Serializable {

  private static final long serialVersionUID = -292685485760779316L;

  @ApiModelProperty(value = "用户id", example = "234543212345643")
  private String id;

  @ApiModelProperty(value = "用户名", required = true, example = "roy")
  private String username;

  @ApiModelProperty(value = "用户真实姓名", required = true, example = "张三")
  private String realName;

  @ApiModelProperty(value = "用户类型", required = true, example = "用户类型: 1-> 个人用户 2 ->机构用户 3-> 系统用户")
  private Integer userType;

  @ApiModelProperty(value = "部门id", required = true, example = "2332434")
  private String orgId;

  @ApiModelProperty(value = "部门名称", required = true, example = "江北大数据中心")
  private String orgName;

  @ApiModelProperty(value = "组织机构id", required = true, example = "2332434")
  private String topOrgId;

  @ApiModelProperty(value = "组织机构名称", required = true, example = "江北大数据中心")
  private String topOrgName;

  @ApiModelProperty(value = "所属应用", required = true, example = "IPlatform")
  private String applicationCode;

  @ApiModelProperty(value = "用户角色列表", required = true, example = "roles")
  private List<UserRoles> roles;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public Integer getUserType() {
    return userType;
  }

  public void setUserType(Integer userType) {
    this.userType = userType;
  }

  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  public String getOrgName() {
    return orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public String getApplicationCode() {
    return applicationCode;
  }

  public void setApplicationCode(String applicationCode) {
    this.applicationCode = applicationCode;
  }

  public List<UserRoles> getRoles() {
    return roles;
  }

  public void setRoles(List<UserRoles> roles) {
    this.roles = roles;
  }

  public String getTopOrgId() {
    return topOrgId;
  }

  public void setTopOrgId(String topOrgId) {
    this.topOrgId = topOrgId;
  }

  public String getTopOrgName() {
    return topOrgName;
  }

  public void setTopOrgName(String topOrgName) {
    this.topOrgName = topOrgName;
  }

  @Override
  public String toString() {
    return "CommonUserInfoRepVO{" +
            "id='" + id + '\'' +
            ", username='" + username + '\'' +
            ", realName='" + realName + '\'' +
            ", userType=" + userType +
            ", orgId='" + orgId + '\'' +
            ", orgName='" + orgName + '\'' +
            ", topOrgId='" + topOrgId + '\'' +
            ", topOrgName='" + topOrgName + '\'' +
            ", applicationCode='" + applicationCode + '\'' +
            ", roles=" + roles +
            '}';
  }
}
