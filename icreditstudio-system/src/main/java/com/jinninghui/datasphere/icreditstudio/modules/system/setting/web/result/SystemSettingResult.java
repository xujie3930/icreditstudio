package com.jinninghui.datasphere.icreditstudio.modules.system.setting.web.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统设置返回结果
 *
 * @author EDZ
 */
@Data
public class SystemSettingResult {

  @ApiModelProperty(value = "")
  private String id;

  @ApiModelProperty(value = "系统logo")
  private String logo;

  @ApiModelProperty(value = "系统名称")
  private String appName;

  @ApiModelProperty(value = "版权信息")
  private String copyRight;

  @ApiModelProperty(value = "默认字号")
  private String defaultFontSize;

  @ApiModelProperty(value = "默认主题")
  private String defaultCssId;

  @ApiModelProperty(value = "默认布局")
  private String defaultLayout;

  @ApiModelProperty(value = "应用参数1")
  private String paramOne;

  @ApiModelProperty(value = "应用参数2")
  private String paramTwo;

  @ApiModelProperty(value = "应用参数3")
  private String paramThree;

  @ApiModelProperty(value = "应用参数4")
  private String paramFour;

  @ApiModelProperty(value = "应用参数5")
  private String paramFive;

  @ApiModelProperty(value = "应用参数6")
  private String paramSix;

  @ApiModelProperty(value = "创建时间")
  private Long createTime;

  @ApiModelProperty(value = "创建人id")
  private String createUserId;

  @ApiModelProperty(value = "最新更新时间")
  private Long lastUpdateTime;

  @ApiModelProperty(value = "最新更新用户id")
  private String lastUpdateUserId;
}
