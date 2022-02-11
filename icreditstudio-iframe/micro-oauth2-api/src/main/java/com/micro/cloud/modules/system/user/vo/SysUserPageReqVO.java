package com.micro.cloud.modules.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.micro.cloud.api.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/** @author roy */
@ApiModel("用户分页 Request VO")
public class SysUserPageReqVO extends PageParam {

  @ApiModelProperty(value = "用户账号", example = "roy", notes = "模糊匹配")
  private String username;

  @ApiModelProperty(value = "手机号码", example = "13114175111", notes = "模糊匹配")
  private String mobile;

  @ApiModelProperty(value = "启用状态", example = "1", notes = "参见 SysCommonStatusEnum 枚举类")
  @JsonProperty(value = "status")
  private Boolean status;

  @ApiModelProperty(value = "开始时间", example = "2020-10-24")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date beginTime;

  @ApiModelProperty(value = "结束时间", example = "2020-10-24")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date endTime;

  @ApiModelProperty(value = "部门编号", example = "1024", notes = "同时筛选子部门")
  private String deptId;

  @ApiModelProperty(value = "用户类型", notes = "参见 UserTypeEnum 枚举类")
  private Integer type;

  @ApiModelProperty(value = "排序字段", example = "create_time", notes = "")
  private String orderBy;

  @ApiModelProperty(value = "排序方式 true:正序 false:倒叙", required = true, example = "true")
  private boolean sort;

  @ApiModelProperty(value = "角色id", required = false, example = "true")
  private String roleId;

  @ApiModelProperty(value = "真实姓名", required = false, example = "张三")
  private String realName;


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public Date getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public String getDeptId() {
    return deptId;
  }

  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getOrderBy() {
    return orderBy;
  }

  public boolean isSort() {
    return sort;
  }

  public void setSort(boolean sort) {
    this.sort = sort;
  }

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  @Override
  public String toString() {
    return "SysUserPageReqVO{" +
            "username='" + username + '\'' +
            ", mobile='" + mobile + '\'' +
            ", status=" + status +
            ", beginTime=" + beginTime +
            ", endTime=" + endTime +
            ", deptId='" + deptId + '\'' +
            ", type=" + type +
            ", orderBy='" + orderBy + '\'' +
            ", sort=" + sort +
            ", roleId='" + roleId + '\'' +
            ", realName='" + realName + '\'' +
            '}';
  }
}
