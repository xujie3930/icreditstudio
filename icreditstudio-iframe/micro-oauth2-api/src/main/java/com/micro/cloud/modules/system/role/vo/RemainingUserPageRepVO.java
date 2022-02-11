package com.micro.cloud.modules.system.role.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/**
 * 〈角色添加人员分页请求参数〉
 *
 * @author roy
 * @create 2021/11/20
 * @since 1.0.0
 */
public class RemainingUserPageRepVO implements Serializable {

  @ApiModelProperty(value = "人员id", required = true, example = "321234567")
  private String key;

  @ApiModelProperty(value = "人员id", required = true, example = "321234567")
  private String userId;

  @ApiModelProperty(value = "人员姓名", required = true, example = "321234567")
  private String username;

  @ApiModelProperty(value = "人员状态", required = true, example = "true")
  private Boolean status;

  @ApiModelProperty(value = "角色id", required = true, example = "321234567")
  @NotBlank(message = "角色id不能为空")
  private String orgId;

  @ApiModelProperty(value = "角色id", required = true, example = "321234567")
  @NotBlank(message = "角色id不能为空")
  private String orgName;

  public String getKey() {
    return userId;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
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

  @Override
  public String toString() {
    return "RemainingUserPageRepVO{"
        + "key='"
        + key
        + '\''
        + ", userId='"
        + userId
        + '\''
        + ", username='"
        + username
        + '\''
        + ", status="
        + status
        + ", orgId='"
        + orgId
        + '\''
        + ", orgName='"
        + orgName
        + '\''
        + '}';
  }
}
