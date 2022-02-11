package com.micro.cloud.modules.system.role.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

/**
 * 角色 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 *
 * @author roy
 */
public class SysRoleBaseVO implements Serializable {

  private static final long serialVersionUID = -2274865347774306388L;

  @ApiModelProperty(value = "角色名称", required = true, example = "管理员")
  @NotBlank(message = "角色名称不能为空")
  @Length(max = 30, message = "角色名称长度不能超过30个字符")
  private String name;

  /* @NotBlank(message = "角色标志不能为空")
  @Length(max = 100, message = "角色标志长度不能超过100个字符")
  @ApiModelProperty(value = "角色编码", required = true, example = "ADMIN")
  private String code;*/

  @ApiModelProperty(
      value = "角色状态",
      required = true,
      example = "true",
      notes = "参见 SysCommonStatus 枚举类")
  @NotNull(message = "角色状态")
  private Boolean status;

  @ApiModelProperty(value = "角色类别", required = true, example = "1,2", notes = "由字典管理维护")
  private Integer category;

  @ApiModelProperty(value = "角色类型", required = true, example = "1", notes = "见 SysRoleTypeEnum 枚举")
  private Integer type;

  @ApiModelProperty(value = "备注", example = "我是一个角色")
  @Length(max = 200, message = "角色名称长度不能超过200个字符")
  private String remark;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /*  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }*/

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public Integer getCategory() {
    return category;
  }

  public void setCategory(Integer category) {
    this.category = category;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  @Override
  public String toString() {
    return "SysRoleBaseVO{"
        + "name='"
        + name
        + '\''
        /* + ", code='"
        + code
        + '\''*/
        + ", status="
        + status
        + ", category="
        + category
        + ", type="
        + type
        + ", remark='"
        + remark
        + '\''
        + '}';
  }
}
