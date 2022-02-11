package com.micro.cloud.info;

import com.micro.cloud.domian.dto.UserRoles;
import java.io.Serializable;
import java.util.List;

/**
 * 〈用户信息〉
 *
 * @author roy
 * @create 2021/11/23
 * @since 1.0.0
 */
public class UserInfo implements Serializable {

  private static final long serialVersionUID = 5015888359043860572L;

  private String userId;

  private String phone;

  private String email;

  private String userName;

  private Integer userType;

  private String orgId;

  private String orgName;

  private List<UserRoles> roles;

  public UserInfo(
      String userId,
      String userName,
      String phone,
      String email,
      Integer userType,
      String orgId,
      String orgName,
      List<UserRoles> roles) {
    this.userId = userId;
    this.userName = userName;
    this.phone = phone;
    this.email = email;
    this.userType = userType;
    this.orgId = orgId;
    this.orgName = orgName;
    this.roles = roles;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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

  public List<UserRoles> getRoles() {
    return roles;
  }

  public void setRoles(List<UserRoles> roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return "UserInfo{"
        + "userId='"
        + userId
        + '\''
        + ", phone='"
        + phone
        + '\''
        + ", email='"
        + email
        + '\''
        + ", userName='"
        + userName
        + '\''
        + ", userType="
        + userType
        + ", orgId='"
        + orgId
        + '\''
        + ", orgName='"
        + orgName
        + '\''
        + ", roles="
        + roles
        + '}';
  }
}
