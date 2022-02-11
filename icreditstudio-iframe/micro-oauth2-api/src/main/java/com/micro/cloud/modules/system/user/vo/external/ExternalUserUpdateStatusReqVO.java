package com.micro.cloud.modules.system.user.vo.external;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;

/** @author roy */
@ApiModel("个人用户更新状态 Request VO")
public class ExternalUserUpdateStatusReqVO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "用户id", required = true, example = "1024")
  @NotNull(message = "用户不能为空")
  private List<String> ids;

  @ApiModelProperty(
      value = "状态",
      required = true,
      example = "1",
      notes = "见 SysCommonStatusEnum 枚举")
  @NotNull(message = "状态不能为空")
  private Boolean status;

  public List<String> getIds() {
    return ids;
  }

  public void setIds(List<String> ids) {
    this.ids = ids;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "ExternalUserUpdateStatusReqVO{" + "ids=" + ids + ", status=" + status + '}';
  }
}
