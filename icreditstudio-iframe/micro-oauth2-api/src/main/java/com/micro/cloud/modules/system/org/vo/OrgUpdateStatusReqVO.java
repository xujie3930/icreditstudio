package com.micro.cloud.modules.system.org.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;

/** @author roy */
@ApiModel("部门更新状态 Request VO")
public class OrgUpdateStatusReqVO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "用户id", required = true, example = "1024")
  @NotNull(message = "部门id不能为空")
  private List<String> ids;

  @ApiModelProperty(
      value = "状态",
      required = true,
      example = "1",
      notes = "见 SysCommonStatusEnum 枚举")
  @NotNull(message = "状态不能为空")
  @JsonProperty("status")
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
