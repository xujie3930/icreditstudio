package com.micro.cloud.modules.system.user.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotEmpty;

/**
 * 批量删除用户请求参数
 *
 * @author EDZ
 */
public class UserDelReqVO implements Serializable {

  private static final long serialVersionUID = 1L;
  /** 待删除用户id集合 */
  @ApiModelProperty(value = "id集合", required = true)
  @NotEmpty(message = "删除用户集合不能为空")
  private Set<String> ids;

  public Set<String> getIds() {
    return ids;
  }

  public void setIds(Set<String> ids) {
    this.ids = ids;
  }

  @Override
  public String toString() {
    return "UserDelReqVO{" + "ids=" + ids + '}';
  }
}
