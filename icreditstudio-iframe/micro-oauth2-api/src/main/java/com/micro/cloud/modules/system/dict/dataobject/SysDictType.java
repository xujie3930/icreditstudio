package com.micro.cloud.modules.system.dict.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.micro.cloud.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 字典类型表
 *
 * @author ruoyi
 */
@ApiModel(value = "SysDictType对象")
@TableName("sys_dict_type")
public class SysDictType extends BaseDO {

  private static final long serialVersionUID = -184772657643941710L;

  @ApiModelProperty("数据字典类型id")
  @TableId("sys_dict_type_id")
  private String sysDictTypeId;

  @ApiModelProperty("类型名称")
  @TableField("name")
  private String name;

  @ApiModelProperty("字典类型")
  @TableField("type")
  private String type;

  @ApiModelProperty("是否启用： 0-> 否 1-> 是")
  @TableField("status")
  private Boolean status;

  @ApiModelProperty("字典类型描述")
  @TableField("remark")
  private String remark;

  public String getSysDictTypeId() {
    return sysDictTypeId;
  }

  public void setSysDictTypeId(String sysDictTypeId) {
    this.sysDictTypeId = sysDictTypeId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
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
    return "SysDictType{"
        + "sysDictTypeId='"
        + sysDictTypeId
        + '\''
        + ", name='"
        + name
        + '\''
        + ", type='"
        + type
        + '\''
        + ", status="
        + status
        + ", remark='"
        + remark
        + '\''
        + '}';
  }
}
