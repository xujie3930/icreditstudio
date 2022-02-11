package com.micro.cloud.modules.system.dict.vo.data;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

/**
 * 字典数据 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 *
 * @author roy
 */
public class SysDictDataBaseVO implements Serializable {

  private static final long serialVersionUID = -4206777282653464979L;

  @ApiModelProperty(value = "显示顺序不能为空", required = true, example = "1024")
  @NotNull(message = "显示顺序不能为空")
  private Integer sort;

  @ApiModelProperty(value = "字典标签", required = true, example = "行政区划")
  @NotBlank(message = "字典标签不能为空")
  @Length(max = 30, message = "字典标签长度不能超过30个字符")
  private String label;

  @ApiModelProperty(value = "字典值", required = true, example = "iocoder")
  @NotBlank(message = "字典键值不能为空")
  @Length(max = 50, message = "字典键值长度不能超过50个字符")
  private String value;

  @ApiModelProperty(value = "字典类型", required = true, example = "sys_common_sex")
  @NotBlank(message = "字典类型不能为空")
  @Length(max = 10, message = "字典类型长度不能超过10个字符")
  private String dictType;

  @ApiModelProperty(
      value = "状态",
      required = true,
      example = "1",
      notes = "见 SysCommonStatusEnum 枚举")
  @NotNull(message = "状态不能为空")
  private Boolean status;

  @ApiModelProperty(value = "备注", example = "我是一个角色")
  private String remark;

  public Integer getSort() {
    return sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getDictType() {
    return dictType;
  }

  public void setDictType(String dictType) {
    this.dictType = dictType;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  @Override
  public String toString() {
    return "SysDictDataBaseVO{"
        + "sort="
        + sort
        + ", label='"
        + label
        + '\''
        + ", value='"
        + value
        + '\''
        + ", dictType='"
        + dictType
        + '\''
        + ", status="
        + status
        + ", remark='"
        + remark
        + '\''
        + '}';
  }
}
