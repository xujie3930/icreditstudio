package com.micro.cloud.modules.system.user.vo.org;

import com.micro.cloud.modules.system.user.vo.SysUserBaseVO;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

/**
 * @ApiModel(value = "机构用户分页时的信息 Response VO", description = "相比个人用户基本信息来说，会多组织机构信息")
 *
 * @author roy
 */
public class OrgUserInfoVO extends SysUserBaseVO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "用户id", example = "123456789")
  private String userId;

  @ApiModelProperty(value = "用户key", example = "123456789")
  private String key;

  @ApiModelProperty(value = "统一社会信用代码", required = true, example = "2020219827656789")
  @Length(max = 30, message = "统一社会信用代码不能超过30个字符")
  private String creditCode;

  @ApiModelProperty(value = "组织机构名称", required = true, example = "南京金宁汇科技有限公司")
  @NotBlank(message = "组织机构名称不能为空")
  @Length(max = 30, message = "部门名称长度不能超过30个字符")
  private String title;

  @ApiModelProperty(value = "组织机构类型", required = true, example = "1", notes = "参见 OrgTypeEnum 枚举类")
  private Integer orgType;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getCreditCode() {
    return creditCode;
  }

  public void setCreditCode(String creditCode) {
    this.creditCode = creditCode;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getOrgType() {
    return orgType;
  }

  public void setOrgType(Integer orgType) {
    this.orgType = orgType;
  }

  @Override
  public String toString() {
    return "OrgUserInfoVO{"
        + "userId='"
        + userId
        + '\''
        + ", key='"
        + key
        + '\''
        + ", creditCode='"
        + creditCode
        + '\''
        + ", title='"
        + title
        + '\''
        + ", orgType="
        + orgType
        + '}';
  }
}
