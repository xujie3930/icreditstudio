package com.micro.cloud.domain;

import com.micro.cloud.domian.dto.UserDTO;
import com.micro.cloud.domian.dto.UserRoles;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/** 登录用户信息 Created by xulei on 2021/11/3 */
public class SecurityUser implements UserDetails {

  private static final long serialVersionUID = 4791120503590720894L;
  /** ID */
  private Long id;
  /** 用户名 */
  private String username;
  /** 真实姓名 */
  private String realName;
  /** 手机号 */
  private String phone;
  /** 邮件账号 */
  private String email;
  /** 部门id */
  private String departId;

  private String departName;

  private Integer userType;
  /** 用户密码 */
  private String password;
  /** 用户状态 */
  private Boolean enabled;

  private List<UserRoles> roles;
  /** 权限数据 */
  private Collection<SimpleGrantedAuthority> authorities;

  public SecurityUser() {}

  public SecurityUser(UserDTO userDTO) {
    this.setId(userDTO.getId());
    this.setUsername(userDTO.getUsername());
    this.setRealName(userDTO.getRealName());
    this.setPhone(userDTO.getPhone());
    this.setEmail(userDTO.getEmail());
    this.setDepartId(userDTO.getDepartId());
    this.setPassword(userDTO.getPassword());
    this.setDepartName(userDTO.getDepartName());
    this.setUserType(userDTO.getUserType());
    this.setEnabled(userDTO.getStatus());
    this.setRoles(userDTO.getRoles());
    if (userDTO.getRoles() != null) {
      authorities = new ArrayList<>();
      userDTO.getRoles().stream()
          .filter(Objects::nonNull)
          .forEach(item -> authorities.add(new SimpleGrantedAuthority(item.getId())));
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  public Long getId() {
    return id;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public String getDepartId() {
    return departId;
  }

  public void setDepartId(String departId) {
    this.departId = departId;
  }

  public String getDepartName() {
    return departName;
  }

  public void setDepartName(String departName) {
    this.departName = departName;
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

  public void setAuthorities(Collection<SimpleGrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  public List<UserRoles> getRoles() {
    return roles;
  }

  public void setRoles(List<UserRoles> roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return "SecurityUser{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", realName='" + realName + '\'' +
        ", phone='" + phone + '\'' +
        ", email='" + email + '\'' +
        ", departId='" + departId + '\'' +
        ", departName='" + departName + '\'' +
        ", userType=" + userType +
        ", password='" + password + '\'' +
        ", enabled=" + enabled +
        ", roles=" + roles +
        ", authorities=" + authorities +
        '}';
  }
}
