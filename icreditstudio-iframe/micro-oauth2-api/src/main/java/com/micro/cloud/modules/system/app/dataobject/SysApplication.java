package com.micro.cloud.modules.system.app.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.micro.cloud.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author EDZ
 * @since 2021-11-05
 */
@TableName("sys_application")
@ApiModel(value = "SysApplication对象", description = "")
public class SysApplication extends BaseDO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("系统应用id")
  @TableId("sys_application_id")
  private String sysApplicationId;

  @ApiModelProperty("系统应用编码")
  @TableField("application_code")
  private String applicationCode;

  @ApiModelProperty("系统名称")
  @TableField("application_name")
  private String applicationName;

  @ApiModelProperty("是否启用: 0->否 1->是")
  @TableField("status")
  private Boolean status;

  public String getSysApplicationId() {
    return this.sysApplicationId;
  }

  public void setSysApplicationId(String sysApplicationId) {
    this.sysApplicationId = sysApplicationId;
  }

  public String getApplicationCode() {
    return this.applicationCode;
  }

  public void setApplicationCode(String applicationCode) {
    this.applicationCode = applicationCode;
  }

  public String getApplicationName() {
    return this.applicationName;
  }

  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  public Boolean getStatus() {
    return this.status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "SysApplication{"
        + "sysApplicationId='"
        + sysApplicationId
        + '\''
        + ", applicationCode='"
        + applicationCode
        + '\''
        + ", applicationName='"
        + applicationName
        + '\''
        + ", status="
        + status
        + '}';
  }
}
