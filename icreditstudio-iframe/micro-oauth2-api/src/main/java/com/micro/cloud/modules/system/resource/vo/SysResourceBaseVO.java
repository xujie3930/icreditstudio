package com.micro.cloud.modules.system.resource.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

/**
 * 菜单 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 *
 * @author roy
 */
public class SysResourceBaseVO implements Serializable {

  private static final long serialVersionUID = -7582248799142258809L;

  @ApiModelProperty(value = "菜单名称", required = true, example = "用户管理")
  @NotBlank(message = "菜单名称不能为空")
  @Length(max = 50, message = "菜单名称长度不能超过50个字符")
  private String name;

  @ApiModelProperty(
      value = "类型",
      required = true,
      example = "1",
      notes = "参见 SysResourceTypeEnum 枚举类")
  @NotNull(message = "菜单类型不能为空")
  private Integer type;

  @ApiModelProperty(value = "显示顺序不能为空", required = true, example = "1024")
  @NotNull(message = "显示顺序不能为空")
  private Integer sort;

  @ApiModelProperty(value = "父菜单 ID", required = false, example = "1024")
  private String parentId;

  @ApiModelProperty(value = "资源识别码", required = false, example = "T-001")
  @NotBlank(message = "资源识别码不能为空")
  @Length(max = 10, message = "资源识别码长度不得超过10个字符")
  private String code;

  @ApiModelProperty(value = "资源识别码", required = false, example = "T-001")
  @JsonProperty("isLeaf")
  private Boolean isLeaf;

  @ApiModelProperty(value = "后端接口地址", example = "/sys/user/page", notes = "后端API接口地址")
  @Length(max = 200, message = "后端接口地址不能超过200个字符")
  private String url;

  @ApiModelProperty(
      value = "状态",
      required = true,
      example = "1",
      notes = "见 SysCommonStatusEnum 枚举")
  @NotNull(message = "状态不能为空")
  @JsonProperty("status")
  private Boolean status;

  @ApiModelProperty(value = "前端布局", example = "1", notes = "1->top 2->bottom 3->left 4->right")
  private Integer layout;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Integer getSort() {
    return sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Boolean getLeaf() {
    return isLeaf;
  }

  public void setLeaf(Boolean leaf) {
    isLeaf = leaf;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String path) {
    this.url = path;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public Integer getLayout() {
    return layout;
  }

  public void setLayout(Integer layout) {
    this.layout = layout;
  }

  @Override
  public String toString() {
    return "SysResourceBaseVO{"
        + "name='"
        + name
        + '\''
        + ", type="
        + type
        + ", sort="
        + sort
        + ", parentId='"
        + parentId
        + '\''
        + ", code='"
        + code
        + '\''
        + ", isLeaf="
        + isLeaf
        + ", url='"
        + url
        + '\''
        + ", status="
        + status
        + ", layout="
        + layout
        + '}';
  }
}
