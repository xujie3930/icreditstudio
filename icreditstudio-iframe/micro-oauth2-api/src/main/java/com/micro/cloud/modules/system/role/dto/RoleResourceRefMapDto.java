package com.micro.cloud.modules.system.role.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 〈资源-角色映射关系〉
 *
 * @author roy
 * @create 2021/11/17
 * @since 1.0.0
 */
public class RoleResourceRefMapDto {

  private String url;

  private List<String> roleIds;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public List<String> getRoleIds() {
    return roleIds;
  }

  public void setRoleIds(List<String> roleIds) {
    this.roleIds = roleIds;
  }

  @Override
  public String toString() {
    return "RoleResourceRefMapDto{" + "url='" + url + '\'' + ", roleIds=" + roleIds + '}';
  }
}
