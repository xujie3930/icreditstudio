package com.micro.cloud.modules.system.dict.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.micro.cloud.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 字典数据表
 *
 * @author ruoyi
 */
@ApiModel(value = "SysDictData对象", description = "")
@TableName("sys_dict_data")
public class SysDictData extends BaseDO {

  private static final long serialVersionUID = 787484127841794165L;

  @ApiModelProperty("数据字典数据id")
  @TableId("sys_dict_data_id")
  private String sysDictDataId;

  @ApiModelProperty("字典键值")
  @TableField("value")
  private String value;

  @ApiModelProperty("字典标签")
  @TableField("label")
  private String label;

  @ApiModelProperty("字典类型")
  @TableField("dict_type")
  private Integer dictType;

  @ApiModelProperty("是否启用: 0->否 1->是")
  @TableField("status")
  private Boolean status;

  @ApiModelProperty("字典数据描述")
  @TableField("remark")
  private String remark;

  @ApiModelProperty("上级id")
  @TableField("parent_id")
  private String parentId;

  @ApiModelProperty("是否为叶子节点")
  @TableField("is_leaf")
  private Boolean isLeaf;

  @ApiModelProperty("排序字段")
  @TableField("sort")
  private Integer sort;

  public String getSysDictDataId() {
    return sysDictDataId;
  }

  public void setSysDictDataId(String sysDictDataId) {
    this.sysDictDataId = sysDictDataId;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Integer getDictType() {
    return dictType;
  }

  public void setDictType(Integer dictType) {
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

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public Boolean getLeaf() {
    return isLeaf;
  }

  public void setLeaf(Boolean leaf) {
    this.isLeaf = leaf;
  }

  public Integer getSort() {
    return sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  @Override
  public String toString() {
    return "SysDictData{" +
        "sysDictDataId='" + sysDictDataId + '\'' +
        ", value='" + value + '\'' +
        ", label='" + label + '\'' +
        ", dictType=" + dictType +
        ", status=" + status +
        ", remark='" + remark + '\'' +
        ", parentId='" + parentId + '\'' +
        ", isLeaf=" + isLeaf +
        ", sort=" + sort +
        '}';
  }
}
