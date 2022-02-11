package com.micro.cloud.modules.system.org.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/**
 * 〈组织机构/部门用户查询〉
 *
 * @author roy
 * @create 2021/12/10
 * @since 1.0.0
 */
@ApiModel("机构用户查询参数")
public class OrgUserParam implements Serializable {

  private static final long serialVersionUID = 3451742603217685076L;

  @ApiModelProperty(value = "部门id", required = true, example = "23123")
  @NotBlank(message = "组织机构id不能为空")
  private String orgId;

  @ApiModelProperty(value = "用户名称", required = true, example = "23123")
  private String userName;

  @ApiModelProperty(value = "用户真实姓名", required = true, example = "张三")
  private String realName;

  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
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

  @Override
  public String toString() {
    return "OrgUserParam{" +
        "orgId='" + orgId + '\'' +
        ", userName='" + userName + '\'' +
        ", realName='" + realName + '\'' +
        '}';
  }
}
