package com.micro.cloud.domian.dto;

import java.io.Serializable;
import java.util.List;

/** Created by xulei on 2021/11/3 */
public class UserDTO implements Serializable {

  private static final long serialVersionUID = -7076876488570714383L;
  private Long id;
  private String username;
  private String realName;
  private String phone;
  private String email;
  private String departId;
  private String departName;
  private Integer userType;
  private String clientId;
  private String password;
  private Boolean status;
  private List<UserRoles> roles;

  public UserDTO(
      Long id,
      String username,
      String realName,
      String phone,
      String email,
      String departId,
      String clientId,
      String departName,
      Integer userType,
      String password,
      Boolean status,
      List<UserRoles> roles) {
    this.id = id;
    this.username = username;
    this.realName = realName;
    this.phone = phone;
    this.email = email;
    this.departId = departId;
    this.departName = departName;
    this.userType = userType;
    this.clientId = clientId;
    this.password = password;
    this.status = status;
    this.roles = roles;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
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

  public String getDepartId() {
    return departId;
  }

  public void setDepartId(String departId) {
    this.departId = departId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public String getDepartName() {
    return departName;
  }

  public void setDepartName(String departName) {
    this.departName = departName;
  }

  public Integer getUserType() {
    return userType;
  }

  public void setUserType(Integer userType) {
    this.userType = userType;
  }

  public List<UserRoles> getRoles() {
    return roles;
  }

  public void setRoles(List<UserRoles> roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return "UserDTO{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", realName='" + realName + '\'' +
        ", phone='" + phone + '\'' +
        ", email='" + email + '\'' +
        ", departId='" + departId + '\'' +
        ", departName='" + departName + '\'' +
        ", userType=" + userType +
        ", clientId='" + clientId + '\'' +
        ", password='" + password + '\'' +
        ", status=" + status +
        ", roles=" + roles +
        '}';
  }
}
