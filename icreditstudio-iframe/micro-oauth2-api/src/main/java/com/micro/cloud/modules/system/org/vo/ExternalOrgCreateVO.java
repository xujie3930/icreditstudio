package com.micro.cloud.modules.system.org.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

/**
 * 〈外部机构创建参数〉
 *
 * @author roy
 * @create 2021/11/15
 * @since 1.0.0
 */
public class ExternalOrgCreateVO implements Serializable {

  private static final long serialVersionUID = -1579723129367453031L;

  @ApiModelProperty(value = "组织机构名称", required = true, example = "南京金宁汇科技有限公司")
  @NotBlank(message = "组织机构名称不能为空")
  @Length(max = 30, message = "部门名称长度不能超过30个字符")
  private String title;

  @ApiModelProperty(value = "统一社会信用代码", required = true, example = "2020219827656789")
  @NotBlank(message = "统一社会信用代码")
  @Length(max = 30, message = "统一社会信用代码不能超过30个字符")
  private String creditCode;

  @ApiModelProperty(value = "联系电话", example = "15601691000")
  @Length(max = 11, message = "联系电话长度不能超过11个字符")
  private String phone;

  @ApiModelProperty(value = "组织机构类型", example = "1", notes = "1 -> 法人机构 2->非法人机构")
  private Integer type;

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

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "ExternalOrgCreateVO{" +
        "title='" + title + '\'' +
        ", creditCode='" + creditCode + '\'' +
        ", phone='" + phone + '\'' +
        ", type=" + type +
        '}';
  }
}
