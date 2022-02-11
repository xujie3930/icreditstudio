package com.micro.cloud.modules.system.resource.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.micro.cloud.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * @author EDZ
 * @since 2021-11-05
 */
@TableName("sys_resource")
@ApiModel(value = "SysResource对象", description = "")
public class SysResource extends BaseDO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("系统资源id")
  @TableId("sys_resource_id")
  private String sysResourceId;

  @ApiModelProperty("资源识别码")
  @TableField("code")
  private String code;

  @ApiModelProperty("资源名称")
  @TableField("name")
  private String name;

  @ApiModelProperty("排序标识")
  @TableField("order_by")
  private Integer orderBy;

  @ApiModelProperty("父级资源id")
  @TableField("parent_id")
  private String parentId;

  @ApiModelProperty("资源描述")
  @TableField("remark")
  private String remark;

  @ApiModelProperty("资源状态:0->停用 1->启用")
  @TableField("status")
  private Boolean status;

  @ApiModelProperty("资源类型")
  @TableField("type")
  private Integer type;

  @ApiModelProperty("资源URL")
  @TableField("url")
  private String url;

  @ApiModelProperty("前端布局")
  @TableField("layout")
  private Integer layout;

  @ApiModelProperty("是否为叶子节点")
  @TableField("is_leaf")
  private Boolean isLeaf;

  @ApiModelProperty("是否需要鉴权")
  @TableField("need_auth")
  private Boolean needAuth;

  @ApiModelProperty("创建人姓名")
  @TableField("creator_name")
  private String creatorName;

  @ApiModelProperty("创建人部门id")
  @TableField("creator_depart_id")
  private String creatorDepartId;

  @ApiModelProperty("创建人部门id")
  @TableField("creator_depart_name")
  private String creatorDepartName;

  @ApiModelProperty("更新人员姓名")
  @TableField("updater_name")
  private String updaterName;

  @ApiModelProperty("更新人员部门id")
  @TableField("updater_depart_id")
  private String updaterDepartId;

  @ApiModelProperty("更新人员部门名称")
  @TableField("updater_depart_name")
  private String updaterDepartName;

  public String getSysResourceId() {
    return this.sysResourceId;
  }

  public void setSysResourceId(String sysResourceId) {
    this.sysResourceId = sysResourceId;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getOrderBy() {
    return this.orderBy;
  }

  public void setOrderBy(Integer orderBy) {
    this.orderBy = orderBy;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Boolean getStatus() {
    return this.status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public Integer getType() {
    return this.type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getLayout() {
    return layout;
  }

  public void setLayout(Integer layout) {
    this.layout = layout;
  }

  public Boolean getLeaf() {
    return isLeaf;
  }

  public void setLeaf(Boolean leaf) {
    isLeaf = leaf;
  }

  public Boolean getNeedAuth() {
    return needAuth;
  }

  public void setNeedAuth(Boolean needAuth) {
    this.needAuth = needAuth;
  }

  public String getCreatorName() {
    return this.creatorName;
  }

  public void setCreatorName(String creatorName) {
    this.creatorName = creatorName;
  }

  public String getCreatorDepartId() {
    return this.creatorDepartId;
  }

  public void setCreatorDepartId(String creatorDepartId) {
    this.creatorDepartId = creatorDepartId;
  }

  public String getCreatorDepartName() {
    return this.creatorDepartName;
  }

  public void setCreatorDepartName(String creatorDepartName) {
    this.creatorDepartName = creatorDepartName;
  }

  public String getUpdaterName() {
    return this.updaterName;
  }

  public void setUpdaterName(String updaterName) {
    this.updaterName = updaterName;
  }

  public String getUpdaterDepartId() {
    return this.updaterDepartId;
  }

  public void setUpdaterDepartId(String updaterDepartId) {
    this.updaterDepartId = updaterDepartId;
  }

  public String getUpdaterDepartName() {
    return this.updaterDepartName;
  }

  public void setUpdaterDepartName(String updaterDepartName) {
    this.updaterDepartName = updaterDepartName;
  }

  @Override
  public String toString() {
    return "SysResource{" +
        "sysResourceId='" + sysResourceId + '\'' +
        ", code='" + code + '\'' +
        ", name='" + name + '\'' +
        ", orderBy=" + orderBy +
        ", parentId='" + parentId + '\'' +
        ", remark='" + remark + '\'' +
        ", status=" + status +
        ", type=" + type +
        ", url='" + url + '\'' +
        ", layout=" + layout +
        ", isLeaf=" + isLeaf +
        ", needAuth=" + needAuth +
        ", creatorName='" + creatorName + '\'' +
        ", creatorDepartId='" + creatorDepartId + '\'' +
        ", creatorDepartName='" + creatorDepartName + '\'' +
        ", updaterName='" + updaterName + '\'' +
        ", updaterDepartId='" + updaterDepartId + '\'' +
        ", updaterDepartName='" + updaterDepartName + '\'' +
        '}';
  }
}
