package com.micro.cloud.modules.system.user.vo.org;

import com.micro.cloud.modules.system.org.vo.SysOrgBaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** @author roy */
@ApiModel("用户分页 Request VO")
public class OrgUserPageRepVO extends SysOrgBaseVO {

  @ApiModelProperty(value = "用户id", example = "roy", notes = "模糊匹配")
  private String userId;

  @ApiModelProperty(value = "用户名")
  private String userName;

  @ApiModelProperty(value = "用户真实姓名")
  private String realName;

  @ApiModelProperty(value = "组织机构id", example = "roy", notes = "模糊匹配")
  private String orgId;

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

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  @Override
  public String toString() {
    return "OrgUserPageRepVO{" +
        "userId='" + userId + '\'' +
        ", userName='" + userName + '\'' +
        ", realName='" + realName + '\'' +
        ", orgId='" + orgId + '\'' +
        '}';
  }
}
