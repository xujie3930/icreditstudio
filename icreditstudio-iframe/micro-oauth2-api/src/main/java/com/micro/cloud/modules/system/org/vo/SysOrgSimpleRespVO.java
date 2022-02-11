package com.micro.cloud.modules.system.org.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 组织机构精简信息
 *
 * @author roy
 */
@ApiModel("组织机构精简信息 Response VO")
public class SysOrgSimpleRespVO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "组织机构id", required = true, example = "1024")
  private String id;

  @ApiModelProperty(value = "组织机构名称", required = true, example = "研发部")
  private String name;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "SysOrgSimpleRespVO{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
  }
}
