package com.micro.cloud.modules.system.user.vo.external;

import com.micro.cloud.modules.system.user.vo.SysUserBaseVO;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/** @author roy */
public class ExternalUserInfoVO extends SysUserBaseVO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "用户id", required = true, example = "201012")
  private String userId;

  @ApiModelProperty(value = "用户key", required = true, example = "201012")
  private String key;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  @Override
  public String toString() {
    return "ExternalUserInfoVO{" +
        "userId='" + userId + '\'' +
        ", key='" + key + '\'' +
        '}';
  }
}
