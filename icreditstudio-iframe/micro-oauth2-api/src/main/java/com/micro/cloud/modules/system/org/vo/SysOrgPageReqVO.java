package com.micro.cloud.modules.system.org.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.micro.cloud.api.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/** @author roy */
@ApiModel("组织机构列表 Request VO")
public class SysOrgPageReqVO extends PageParam implements Serializable {

  private static final long serialVersionUID = -2576440414208411809L;

  @ApiModelProperty(value = "上级部门id", example = "工程建设中心", notes = "没有则为空")
  private String parentId;

  @ApiModelProperty(value = "部门名称", example = "xxx", notes = "模糊匹配")
  private String name;

  @ApiModelProperty(value = "部门编码", example = "0101", notes = "模糊匹配")
  private String code;

  @ApiModelProperty(value = "展示状态", example = "1", notes = "参见 SysCommonStatusEnum 枚举类")
  @JsonProperty("status")
  private Boolean status;

  @ApiModelProperty(value = "排序方式 true:正序 false:倒叙", required = true, example = "true")
  private boolean sort;

  @ApiModelProperty(value = "排序字段", example = "create_time", notes = "")
  private String orderBy;

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public boolean isSort() {
    return sort;
  }

  public void setSort(boolean sort) {
    this.sort = sort;
  }

  public String getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  @Override
  public String toString() {
    return "SysOrgPageReqVO{" +
            "parentId='" + parentId + '\'' +
            ", name='" + name + '\'' +
            ", code='" + code + '\'' +
            ", status=" + status +
            ", sort=" + sort +
            ", orderBy='" + orderBy + '\'' +
            '}';
  }
}
