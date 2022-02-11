package com.micro.cloud.modules.system.org.vo;

import com.micro.cloud.validation.CreditCode;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

/**
 * 组织机构 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 *
 * @author roy
 */
public class SysOrgBaseVO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "组织机构名称", required = true, example = "南京金宁汇科技有限公司")
  @NotBlank(message = "组织机构名称不能为空")
  @Length(max = 30, message = "部门名称长度不能超过30个字符")
  private String title;

  @ApiModelProperty(value = "统一社会信用代码", required = true, example = "2020219827656789")
  @Length(max = 18, message = "统一社会信用代码不能超过18个字符")
  @CreditCode
  private String creditCode;

  @ApiModelProperty(value = "联系电话", example = "15601691000")
  @Length(max = 11, message = "联系电话长度不能超过11个字符")
  private String phone;

  @ApiModelProperty(value = "组织机构状态", example = "1", notes = " 参见 SysCommonStatusEnum 枚举类")
  private Boolean status;

  @ApiModelProperty(value = "组织机构类型", required = true, example = "1", notes = "参见 OrgTypeEnum 枚举类")
  private Integer orgType;

  @ApiModelProperty(value = "是否为叶子节点", required = true, example = "1")
  private Boolean isLeaf;

  @ApiModelProperty(value = "排序下标", required = false, example = "2")
  private Integer orderBy;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCreditCode() {
    return creditCode;
  }

  public void setCreditCode(String creditCode) {
    this.creditCode = creditCode;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public Integer getOrgType() {
    return orgType;
  }

  public void setOrgType(Integer orgType) {
    this.orgType = orgType;
  }

  public Boolean getIsLeaf() {
    return isLeaf;
  }

  public void setIsLeaf(Boolean isLeaf) {
    this.isLeaf = isLeaf;
  }

  public Integer getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(Integer orderBy) {
    this.orderBy = orderBy;
  }

  @Override
  public String toString() {
    return "SysOrgBaseVO{"
        + "title='"
        + title
        + '\''
        + ", creditCode='"
        + creditCode
        + '\''
        + ", phone='"
        + phone
        + '\''
        + ", status="
        + status
        + ", orgType="
        + orgType
        + ", isLeaf="
        + isLeaf
        + ", orderBy="
        + orderBy
        + '}';
  }
}
